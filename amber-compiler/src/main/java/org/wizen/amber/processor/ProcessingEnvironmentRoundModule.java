package org.wizen.amber.processor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic.Kind;

import dagger.Module;
import dagger.Provides;

@Module
class ProcessingEnvironmentRoundModule {
  private final ProcessingEnvironment processingEnv;

  interface Foo {
    void foo();
  }
  public ProcessingEnvironmentRoundModule(ProcessingEnvironment processingEnv) {
    this.processingEnv = processingEnv;
  }

  @Provides
  Messager provideMessager() {
    return processingEnv.getMessager();
  }

  @Provides
  Foo provideFoo(Messager messager) {
    return new Foo() {

      @Override
      public void foo() {
        messager.printMessage(Kind.ERROR, "foo");
      }
    };
  }
}
