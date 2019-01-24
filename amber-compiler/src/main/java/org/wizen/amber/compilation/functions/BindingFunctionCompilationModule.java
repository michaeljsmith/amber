package org.wizen.amber.compilation.functions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Optional;

import javax.inject.Qualifier;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

import org.wizen.amber.extraction.BindingFunction;

import com.google.auto.value.AutoValue;
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
    return Optional.of(CompiledFunction.create(compiledApiFunction, compiledImplFunction));
  }

  @Provides
  @CompiledApiFunction
  static MethodSpec provideCompiledApiFunction(
      @CompiledCommonFunction MethodSpec compiledCommonFunction) {
    return compiledCommonFunction;
  }

  @Provides
  @CompiledImplFunction
  static MethodSpec provideCompiledImplFunction(
      @CompiledCommonFunction MethodSpec compiledCommonFunction) {
    return compiledCommonFunction;
  }

  @Provides
  @CompiledCommonFunction
  static MethodSpec provideCompiledCommonFunction(@InputFunctionName String inputFunctionName) {
    return MethodSpec.methodBuilder(inputFunctionName).addModifiers(MODIFERS_TO_ADD).build();
  }

  @AutoValue
  abstract static class CompiledParameterInfo {
    void asdf();
  }

  @Provides
  static CompiledParameterInfo provideCompiledParameterInfo(
      @InputParameters List<? extends VariableElement> inputParameters) {
    
  }

  @Provides
  @InputParameters
  static List<? extends VariableElement> provideInputParameters(
      @InputFunctionElement ExecutableElement element) {
    return element.getParameters();
  }

  @Provides
  @InputFunctionName
  static String provideInputFunctionName(
      @InputBindingFunction BindingFunction inputBindingFunction) {
    return inputBindingFunction.name();
  }

  @Provides
  @InputFunctionElement
  static ExecutableElement provideInputFunctionElement(
      @InputBindingFunction BindingFunction inputBindingFunction) {
    return inputBindingFunction.element();
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

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface InputFunctionElement {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface InputParameters {}
}
