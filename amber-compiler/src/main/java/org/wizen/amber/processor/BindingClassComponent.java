package org.wizen.amber.processor;

import java.util.Optional;

import javax.inject.Provider;

import org.wizen.amber.compilation.classes.BindingClassCompilationModule;
import org.wizen.amber.compilation.classes.BindingClassCompilationResult;
import org.wizen.amber.compilation.classes.BindingClassScope;
import org.wizen.amber.compilation.classes.CompiledClass;
import org.wizen.amber.compilation.classes.InputBindingClass;
import org.wizen.amber.compilation.functions.BindingFunctionCompiler;
import org.wizen.amber.extraction.BindingClass;

import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

@BindingClassScope
@Subcomponent(
  modules = {
    BindingClassComponent.SelfModule.class,
    BindingClassCompilationModule.class,
  }
)
public interface BindingClassComponent {
  @Module(subcomponents = BindingFunctionComponent.class)
  static class SelfModule {
    SelfModule(BindingClass bindingClass) {}

    @Provides
    static BindingFunctionCompiler provideBindingFunctionCompiler(
        Provider<BindingFunctionComponent.Builder> bindingFunctionComponentBuilderProvider) {
      return bindingFunction ->
          bindingFunctionComponentBuilderProvider
              .get()
              .setBindingFunction(bindingFunction)
              .build()
              .resultMethodSpec();
    }
  }

  @Subcomponent.Builder
  static interface Builder {
    @BindsInstance
    Builder setBindingClass(@InputBindingClass BindingClass bindingClass);

    BindingClassComponent build();
  }

  @BindingClassCompilationResult
  Optional<CompiledClass> resultTypeSpec();
}
