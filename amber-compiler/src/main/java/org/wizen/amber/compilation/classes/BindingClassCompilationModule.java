package org.wizen.amber.compilation.classes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Optional;
import java.util.Set;

import javax.inject.Qualifier;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import org.wizen.amber.compilation.functions.BindingFunctionCompiler;
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
  private static final ImmutableSet<Modifier> MODIFIERS_TO_FORCE =
      ImmutableSet.of(Modifier.FINAL);
  private static final ImmutableSet<Modifier> MODIFIERS_TO_COPY =
      ImmutableSet.of(Modifier.PUBLIC, Modifier.PRIVATE, Modifier.PROTECTED);

  @Provides
  @BindingClassCompilationResult
  static Optional<CompiledClass> provideBindingClassCompilationResult(
      @InputBindingClass BindingClass bindingClass,
      @CompiledClassName String compiledClassName,
      BindingFunctionCompiler bindingFunctionCompiler,
      @CompiledClassModifiers ImmutableSet<Modifier> compiledClassModifiers) {
    ImmutableList<MethodSpec> methods =
        bindingClass.bindingFunctions().stream()
            .map(bindingFunctionCompiler::compiledFunction)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(ImmutableList.toImmutableList());
    return Optional.of(
        CompiledClass.create(
            TypeSpec.classBuilder(compiledClassName)
                .addModifiers(compiledClassModifiers.toArray(new Modifier[0]))
                .addMethods(methods)
                .build(),
            bindingClass.inputElement()));
  }

  @Provides
  @CompiledClassName
  static String provideCompiledClassName(@InputBindingClass BindingClass bindingClass) {
    return "Amber" + bindingClass.name();
  }

  @Provides
  @CompiledClassModifiers
  static ImmutableSet<Modifier> provideCompiledClassModifiers(
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
  private @interface CompiledClassName {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  public @interface CompiledClassModifiers {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  public @interface InputClassName {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  public @interface InputClassElement {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  public @interface InputBindingFunctions {}
}
