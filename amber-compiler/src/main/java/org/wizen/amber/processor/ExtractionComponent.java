package org.wizen.amber.processor;

import org.wizen.amber.BindingFunction;
import org.wizen.amber.compilation.functions.BindingFunctionCompilationBindingFunctionModule;
import org.wizen.amber.compilation.functions.BindingFunctionCompilationResult;
import org.wizen.amber.compilation.functions.BindingFunctionScope;
import org.wizen.amber.compilation.functions.InputBindingFunction;
import org.wizen.amber.extraction.BindingClass;
import org.wizen.amber.extraction.BindingClassExtractionRoundModule;
import org.wizen.amber.extraction.BindingClasses;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.MethodSpec;

import dagger.BindsInstance;
import dagger.Subcomponent;

@BindingFunctionScope
@Subcomponent(modules = {BindingClassExtractionRoundModule.class})
public interface ExtractionComponent {
  @BindingClasses
  ImmutableSet<BindingClass> bindingClasses();

  @Subcomponent.Builder
  static interface Builder {
    ExtractionComponent build();
  }
}
