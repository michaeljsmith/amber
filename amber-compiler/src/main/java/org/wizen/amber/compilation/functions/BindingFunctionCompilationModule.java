package org.wizen.amber.compilation.functions;

import java.util.Optional;

import javax.lang.model.element.Modifier;

import org.wizen.amber.extraction.BindingFunction;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.MethodSpec;

import dagger.Module;
import dagger.Provides;

@Module
public class BindingFunctionCompilationModule {
  private static ImmutableSet<Modifier> MODIFERS_TO_ADD =
      ImmutableSet.of(Modifier.PUBLIC, Modifier.FINAL);

  @Provides
  @BindingFunctionCompilationResult
  Optional<MethodSpec> provideBindingFunctionCompilationResult(
      @InputBindingFunction BindingFunction bindingFunction) {
    return Optional.of(
        MethodSpec.methodBuilder(bindingFunction.name())
            .addModifiers(MODIFERS_TO_ADD)
            .build());
  }
}
