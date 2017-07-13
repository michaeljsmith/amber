package org.wizen.amber.processor;

import org.wizen.amber.BindingFunction;
import org.wizen.amber.compilation.functions.BindingFunctionCompilationBindingFunctionModule;
import org.wizen.amber.compilation.functions.BindingFunctionCompilationResult;
import org.wizen.amber.compilation.functions.BindingFunctionScope;
import org.wizen.amber.compilation.functions.InputBindingFunction;

import com.squareup.javapoet.MethodSpec;

import dagger.BindsInstance;
import dagger.Subcomponent;

@BindingFunctionScope
@Subcomponent(modules = {BindingFunctionCompilationBindingFunctionModule.class})
public interface BindingFunctionComponent {
  @BindingFunctionCompilationResult
  MethodSpec resultMethodSpec();

  @Subcomponent.Builder
  static interface Builder {
    @BindsInstance
    Builder setBindingFunction(@InputBindingFunction BindingFunction bindingFunction);

    BindingFunctionComponent build();
  }
}
