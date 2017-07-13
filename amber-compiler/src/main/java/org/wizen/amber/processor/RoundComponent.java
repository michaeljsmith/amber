package org.wizen.amber.processor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.inject.Provider;

import org.wizen.amber.compilation.CompiledClasses;
import org.wizen.amber.compilation.classes.BindingClassCompiler;
import org.wizen.amber.extraction.BindingClass;
import org.wizen.amber.extraction.BindingClassExtractionRoundModule;
import org.wizen.amber.extraction.BindingClasses;
import org.wizen.amber.processing.RoundScope;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.TypeSpec;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Module;
import dagger.Provides;

@RoundScope
@Component(
    modules = {
        RoundComponent.SelfModule.class,
        BindingClassExtractionRoundModule.class,
    })
public interface RoundComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder setProcessingEnvironment(ProcessingEnvironment processingEnvironment);

    RoundComponent build();
  }

  @CompiledClasses
  ImmutableSet<TypeSpec> compiledClasses();

  @Module(
      subcomponents = {
        CompilationComponent.class,
        ExtractionComponent.class,
      })
  static class SelfModule {
    @Provides
    Messager provideMessager(ProcessingEnvironment processingEnv) {
      return processingEnv.getMessager();
    }
  }
}
