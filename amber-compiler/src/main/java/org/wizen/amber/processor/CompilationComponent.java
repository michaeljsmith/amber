package org.wizen.amber.processor;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.inject.Provider;

import org.wizen.amber.compilation.CompilationModule;
import org.wizen.amber.compilation.CompiledClasses;
import org.wizen.amber.compilation.classes.BindingClassCompiler;
import org.wizen.amber.extraction.BindingClass;
import org.wizen.amber.extraction.BindingClasses;
import org.wizen.amber.extraction.ExtractionModule;
import org.wizen.amber.output.OutputModule;
import org.wizen.amber.output.RoundWriter;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.TypeSpec;

import dagger.BindsInstance;
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
    Builder setExtractionComponent(ExtractionComponent extractionComponent);

    CompilationComponent build();
  }

  @Module(subcomponents = BindingClassComponent.class)
  static class SelfModule {
    @Provides
    static BindingClassCompiler provideBindingClassCompiler(
        Provider<BindingClassComponent.Builder> bindingClassComponentBuilderProvider) {
      return bindingClass ->
        bindingClassComponentBuilderProvider.get().setBindingClass(bindingClass).build().resultTypeSpec();
    }
  }
}
