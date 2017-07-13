package org.wizen.amber.compilation.functions;

import org.wizen.amber.BindingFunction;

import com.squareup.javapoet.MethodSpec;

import dagger.Module;
import dagger.Provides;

@Module
public class BindingFunctionCompilationBindingFunctionModule {
  @Provides
  @BindingFunctionCompilationResult
  MethodSpec provideBindingFunctionCompilationResult(
      @InputBindingFunction BindingFunction bindingFunction) {
    return MethodSpec.methodBuilder("foo").build();
  }
}
