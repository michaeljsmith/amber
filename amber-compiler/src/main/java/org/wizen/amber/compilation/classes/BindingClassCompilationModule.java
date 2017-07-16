package org.wizen.amber.compilation.classes;

import java.util.Optional;

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
  static Optional<TypeSpec> provideBindingClassCompilationResult(
      @InputBindingClass BindingClass bindingClass,
      BindingFunctionCompiler bindingFunctionCompiler) {
    ImmutableList<MethodSpec> methods =
        bindingClass.bindingFunctions().stream()
            .map(bindingFunctionCompiler::compiledFunction)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(ImmutableList.toImmutableList());
    return Optional.of(
        TypeSpec.classBuilder(bindingClass.name())
            .addMethods(methods)
            .build());
  }
}
