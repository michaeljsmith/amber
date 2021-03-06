package org.wizen.amber.processor;

import java.util.Optional;

import org.wizen.amber.compilation.functions.BindingFunctionCompilationModule;
import org.wizen.amber.compilation.functions.BindingFunctionCompilationResult;
import org.wizen.amber.compilation.functions.BindingFunctionScope;
import org.wizen.amber.compilation.functions.CompiledFunction;
import org.wizen.amber.compilation.functions.InputBindingFunction;
import org.wizen.amber.extraction.BindingFunction;

import dagger.BindsInstance;
import dagger.Subcomponent;

@BindingFunctionScope
@Subcomponent(modules = {BindingFunctionCompilationModule.class})
public interface BindingFunctionComponent {
  @BindingFunctionCompilationResult
  Optional<CompiledFunction> resultMethodSpec();

  @Subcomponent.Builder
  static interface Builder {
    @BindsInstance
    Builder setBindingFunction(@InputBindingFunction BindingFunction bindingFunction);

    BindingFunctionComponent build();
  }
}
