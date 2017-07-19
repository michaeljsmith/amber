package org.wizen.amber.compilation.functions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Optional;

import javax.inject.Qualifier;
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
  static Optional<CompiledFunction> provideBindingFunctionCompilationResult(
      @CompiledApiFunction MethodSpec compiledApiFunction,
      @CompiledImplFunction MethodSpec compiledImplFunction) {
    return Optional.of(
        CompiledFunction.create(compiledApiFunction, compiledImplFunction));
  }

  @Provides
  @CompiledApiFunction
  static MethodSpec provideCompiledApiFunction(@CompiledCommonFunction MethodSpec compiledCommonFunction) {
    return compiledCommonFunction;
  }

  @Provides
  @CompiledImplFunction
  static MethodSpec provideCompiledImplFunction(@CompiledCommonFunction MethodSpec compiledCommonFunction) {
    return compiledCommonFunction;
  }

  @Provides
  @CompiledCommonFunction
  static MethodSpec provideCompiledCommonFunction(@InputFunctionName String inputFunctionName) {
    return MethodSpec.methodBuilder(inputFunctionName)
        .addModifiers(MODIFERS_TO_ADD)
        .build();
  }

  @Provides
  @InputFunctionName
  static String provideInputFunctionName(
      @InputBindingFunction BindingFunction inputBindingFunction) {
    return inputBindingFunction.name();
  }

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledApiFunction {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledImplFunction {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface CompiledCommonFunction {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface InputFunctionName {}
}
