package org.wizen.amber.processor;

import javax.annotation.processing.Filer;

import org.wizen.amber.compilation.output.OutputModule;
import org.wizen.amber.compilation.output.RoundWriter;

import dagger.BindsInstance;
import dagger.Component;

@Component(
    modules = OutputModule.class,
    dependencies = CompilationComponent.class)
public interface OutputComponent {
  RoundWriter roundWriter();

  @Component.Builder
  public interface Builder {
    Builder setCompilationComponent(CompilationComponent compilationComponent);

    @BindsInstance
    Builder setFiler(Filer filer);

    OutputComponent build();
  }
}
