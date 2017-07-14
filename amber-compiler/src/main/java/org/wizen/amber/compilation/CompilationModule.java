package org.wizen.amber.compilation;

import org.wizen.amber.BindingFunction;
import org.wizen.amber.compilation.classes.BindingClassCompiler;
import org.wizen.amber.compilation.functions.BindingFunctionCompilationResult;
import org.wizen.amber.compilation.functions.InputBindingFunction;
import org.wizen.amber.extraction.BindingClass;
import org.wizen.amber.extraction.BindingClasses;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import dagger.Module;
import dagger.Provides;

@Module
public class CompilationModule {
  @Provides
  @CompiledClasses
  ImmutableSet<TypeSpec> provideCompiledClasses(
      @BindingClasses ImmutableSet<BindingClass> bindingClasses,
      BindingClassCompiler bindingClassCompiler) {
    return bindingClasses
        .stream()
        .map(bindingClassCompiler::compiledClass)
        .collect(ImmutableSet.toImmutableSet());
  }
}
