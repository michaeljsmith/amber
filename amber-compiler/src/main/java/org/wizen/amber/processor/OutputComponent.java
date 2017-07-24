package org.wizen.amber.processor;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;

import org.wizen.amber.output.OutputModule;
import org.wizen.amber.output.OutputWriter;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = OutputModule.class, dependencies = CompilationComponent.class)
public interface OutputComponent {
  OutputWriter outputWriter();

  @Component.Builder
  public interface Builder {
    Builder setCompilationComponent(CompilationComponent compilationComponent);

    @BindsInstance
    Builder setFiler(Filer filer);

    @BindsInstance
    Builder setMessager(Messager messager);

    OutputComponent build();
  }
}
