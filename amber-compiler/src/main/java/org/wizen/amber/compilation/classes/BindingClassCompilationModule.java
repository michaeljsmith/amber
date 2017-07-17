package org.wizen.amber.compilation.classes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Optional;

import javax.inject.Qualifier;

import org.wizen.amber.compilation.functions.BindingFunctionCompiler;
import org.wizen.amber.extraction.BindingClass;

import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import dagger.Module;
import dagger.Provides;

@Module
public class BindingClassCompilationModule {

  @Provides
  @BindingClassCompilationResult
  static Optional<CompiledClass> provideBindingClassCompilationResult(
      @InputBindingClass BindingClass bindingClass,
      @CompiledClassName String compiledClassName,
      BindingFunctionCompiler bindingFunctionCompiler) {
    ImmutableList<MethodSpec> methods =
        bindingClass.bindingFunctions().stream()
            .map(bindingFunctionCompiler::compiledFunction)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(ImmutableList.toImmutableList());
    return Optional.of(
        CompiledClass.create(
            TypeSpec.classBuilder(compiledClassName)
                .addMethods(methods)
                .build(),
            bindingClass.inputElement()));
  }

  @Provides
  @CompiledClassName
  static String provideCompiledClassName(@InputBindingClass BindingClass bindingClass) {
    return "Amber" + bindingClass.name();
  }

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledClassName {}
}
