package org.wizen.amber.processor;

import javax.inject.Provider;

import org.wizen.amber.BindingFunction;
import org.wizen.amber.compilation.classes.BindingClassCompiler;
import org.wizen.amber.compilation.functions.BindingFunctionCompilationBindingFunctionModule;
import org.wizen.amber.compilation.functions.BindingFunctionCompilationResult;
import org.wizen.amber.compilation.functions.BindingFunctionCompiler;
import org.wizen.amber.compilation.functions.BindingFunctionScope;
import org.wizen.amber.compilation.functions.InputBindingFunction;
import org.wizen.amber.extraction.BindingClass;
import org.wizen.amber.extraction.BindingClassExtractionRoundModule;
import org.wizen.amber.processing.RoundScope;

import com.squareup.javapoet.MethodSpec;

import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

@Subcomponent(modules = {CompilationComponent.SelfModule.class})
public interface CompilationComponent {
  BindingClassCompiler bindingClassCompiler();

  @Subcomponent.Builder
  static interface Builder {
    CompilationComponent build();
  }

  @Module(subcomponents = BindingClassComponent.class)
  static class SelfModule {
    SelfModule(BindingClass bindingClass) {}

    @Provides
    @RoundScope
    static BindingClassCompiler provideBindingClassCompiler(
        Provider<BindingClassComponent.Builder> bindingClassComponentBuilderProvider) {
      return bindingClass ->
        bindingClassComponentBuilderProvider.get().setBindingClass(bindingClass).build().resultTypeSpec();
    }
  }
}
