package org.wizen.amber.compilation.classes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Optional;
import java.util.Set;

import javax.inject.Qualifier;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import org.wizen.amber.compilation.functions.BindingFunctionCompiler;
import org.wizen.amber.compilation.functions.CompiledFunction;
import org.wizen.amber.extraction.BindingClass;
import org.wizen.amber.extraction.BindingFunction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import dagger.Module;
import dagger.Provides;

@Module
public class BindingClassCompilationModule {
  private static final ImmutableSet<Modifier> MODIFIERS_TO_FORCE = ImmutableSet.of(Modifier.FINAL);
  private static final ImmutableSet<Modifier> MODIFIERS_TO_COPY =
      ImmutableSet.of(Modifier.PUBLIC, Modifier.PRIVATE, Modifier.PROTECTED);

  @Provides
  @BindingClassCompilationResult
  static Optional<CompiledClass> provideBindingClassCompilationResult(
      @InputBindingClass BindingClass bindingClass,
      @CompiledPackageName String compiledPackageName,
      @CompiledApiClass TypeSpec compiledApiClass,
      @CompiledImplClass TypeSpec compiledImplClass) {
    return Optional.of(
        CompiledClass.create(
            compiledPackageName, compiledApiClass, compiledImplClass, bindingClass.inputElement()));
  }

  @Provides
  @CompiledApiClass
  static TypeSpec provideCompiledApiClass(
      @CompiledApiClassName String compiledApiClassName,
      @CompiledApiClassModifiers ImmutableSet<Modifier> compiledClassModifiers,
      @CompiledApiMethods ImmutableList<MethodSpec> compiledApiMethods) {
    return TypeSpec.classBuilder(compiledApiClassName)
        .addModifiers(compiledClassModifiers.toArray(new Modifier[0]))
        .addMethods(compiledApiMethods)
        .build();
  }

  @Provides
  @CompiledImplClass
  static TypeSpec provideCompiledImplClass(
      @CompiledImplClassName String compiledImplClassName,
      @CompiledImplClassModifiers ImmutableSet<Modifier> compiledImplClassModifiers,
      @CompiledImplMethods ImmutableList<MethodSpec> compiledImplMethods) {
    return TypeSpec.classBuilder(compiledImplClassName)
        .addModifiers(compiledImplClassModifiers.toArray(new Modifier[0]))
        .addMethods(compiledImplMethods)
        .build();
  }

  @Provides
  @CompiledApiClassName
  static String provideCompiledApiClassName(@InputClassName String inputClassName) {
    return "Amber" + inputClassName;
  }

  @Provides
  @CompiledImplClassName
  static String provideCompiledImplClassName(@InputClassName String inputClassName) {
    return "Amber" + inputClassName + "_impl";
  }

  @Provides
  @CompiledMethods
  static ImmutableList<CompiledFunction> provideCompiledMethods(
      @InputBindingClass BindingClass bindingClass,
      BindingFunctionCompiler bindingFunctionCompiler) {
    return bindingClass
        .bindingFunctions()
        .stream()
        .map(bindingFunctionCompiler::compiledFunction)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(ImmutableList.toImmutableList());
  }

  @Provides
  @CompiledApiMethods
  static ImmutableList<MethodSpec> provideCompiledApiMethods(
      @CompiledMethods ImmutableList<CompiledFunction> compiledMethods) {
    return compiledMethods
        .stream()
        .map(CompiledFunction::apiMethod)
        .collect(ImmutableList.toImmutableList());
  }

  @Provides
  @CompiledImplMethods
  static ImmutableList<MethodSpec> provideCompiledImplMethods(
      @CompiledMethods ImmutableList<CompiledFunction> compiledMethods) {
    return compiledMethods
        .stream()
        .map(CompiledFunction::implMethod)
        .collect(ImmutableList.toImmutableList());
  }

  @Provides
  @CompiledApiClassModifiers
  static ImmutableSet<Modifier> provideCompiledApiClassModifiers(
      @CompiledClassCommonModifiers ImmutableSet<Modifier> compiledClassCommonModifiers) {
    return compiledClassCommonModifiers;
  }

  @Provides
  @CompiledImplClassModifiers
  static ImmutableSet<Modifier> provideCompiledImplClassModifiers(
      @CompiledClassCommonModifiers ImmutableSet<Modifier> compiledClassCommonModifiers) {
    return compiledClassCommonModifiers;
  }

  @Provides
  @CompiledClassCommonModifiers
  static ImmutableSet<Modifier> provideCompiledClassCommonModifiers(
      @InputClassElement TypeElement inputClassElement) {
    Set<Modifier> modifiers = inputClassElement.getModifiers();
    ImmutableSet.Builder<Modifier> results = ImmutableSet.builder();
    results.addAll(MODIFIERS_TO_FORCE);
    for (Modifier modifierToCopy : MODIFIERS_TO_COPY) {
      if (modifiers.contains(modifierToCopy)) {
        results.add(modifierToCopy);
      }
    }
    return results.build();
  }

  @Provides
  @CompiledPackageName
  static String provideCompiledPackageName(@InputPackageName String inputPackageName) {
    return inputPackageName;
  }

  @Provides
  @InputPackageName
  static String provideInputPackageName(@InputBindingClass BindingClass bindingClass) {
    return bindingClass.packageName();
  }

  @Provides
  @InputClassName
  static String provideInputClassName(@InputBindingClass BindingClass bindingClass) {
    return bindingClass.name();
  }

  @Provides
  @InputClassElement
  static TypeElement provideInputClassElement(@InputBindingClass BindingClass bindingClass) {
    return bindingClass.inputElement();
  }

  @Provides
  @InputBindingFunctions
  static ImmutableList<BindingFunction> provideInputBindingFunction(
      @InputBindingClass BindingClass bindingClass) {
    return bindingClass.bindingFunctions();
  }

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledApiClass {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledImplClass {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledPackageName {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledApiClassName {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledImplClassName {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledMethods {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledApiMethods {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledImplMethods {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledApiClassModifiers {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledImplClassModifiers {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledClassCommonModifiers {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface InputPackageName {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface InputClassName {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface InputClassElement {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface InputBindingFunctions {}
}
