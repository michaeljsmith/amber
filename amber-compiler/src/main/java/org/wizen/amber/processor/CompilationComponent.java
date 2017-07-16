package org.wizen.amber.processor;

import java.util.Optional;

import javax.inject.Provider;

import org.wizen.amber.compilation.CompilationModule;
import org.wizen.amber.compilation.CompiledClasses;
import org.wizen.amber.compilation.classes.BindingClassCompiler;
import org.wizen.amber.extraction.BindingClasses;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.TypeSpec;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Component(
    modules = {
        CompilationComponent.SelfModule.class,
        CompilationModule.class,
    },
    dependencies = ExtractionComponent.class)
public interface CompilationComponent {
  @CompiledClasses
  ImmutableSet<TypeSpec> compiledClasses();

  @Component.Builder
  interface Builder {
    Builder setExtractionComponent(@BindingClasses ExtractionComponent extractionComponent);

    CompilationComponent build();
  }

  @Module(subcomponents = BindingClassComponent.class)
  static class SelfModule {
    @Provides
    static BindingClassCompiler provideBindingClassCompiler(
        Provider<BindingClassComponent.Builder> bindingClassComponentBuilderProvider) {
      return bindingClass ->
          bindingClassComponentBuilderProvider.get()
              .setBindingClass(bindingClass).build().resultTypeSpec();
    }
  }
}
