package org.wizen.amber.compilation.functions;

import java.util.Optional;

import org.wizen.amber.extraction.BindingFunction;

import com.squareup.javapoet.MethodSpec;

import dagger.Module;
import dagger.Provides;

@Module
public class BindingFunctionCompilationBindingFunctionModule {
  @Provides
  @BindingFunctionCompilationResult
  Optional<MethodSpec> provideBindingFunctionCompilationResult(
      @InputBindingFunction BindingFunction bindingFunction) {
    return Optional.of(MethodSpec.methodBuilder(bindingFunction.name()).build());
  }
}
