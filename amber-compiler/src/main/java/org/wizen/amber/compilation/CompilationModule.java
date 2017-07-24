package org.wizen.amber.compilation;

import java.util.Optional;

import org.wizen.amber.compilation.classes.BindingClassCompiler;
import org.wizen.amber.compilation.classes.CompiledClass;
import org.wizen.amber.extraction.BindingClass;
import org.wizen.amber.extraction.BindingClasses;

import com.google.common.collect.ImmutableList;

import dagger.Module;
import dagger.Provides;

@Module
public class CompilationModule {
  @Provides
  @CompiledClasses
  ImmutableList<CompiledClass> provideCompiledClasses(
      @BindingClasses ImmutableList<BindingClass> bindingClasses,
      BindingClassCompiler bindingClassCompiler) {
    return bindingClasses
        .stream()
        .map(bindingClassCompiler::compiledClass)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(ImmutableList.toImmutableList());
  }
}
