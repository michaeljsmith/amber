package org.wizen.amber.processor;

import java.util.Set;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import org.wizen.amber.extraction.BindingClass;
import org.wizen.amber.extraction.BindingClasses;
import org.wizen.amber.extraction.ExtractionModule;
import org.wizen.amber.extraction.InputAnnotations;

import com.google.common.collect.ImmutableList;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = ExtractionModule.class)
public interface ExtractionComponent {
  @BindingClasses
  ImmutableList<BindingClass> bindingClasses();

  @Component.Builder
  public static interface Builder {
    @BindsInstance
    Builder setRoundEnvironment(RoundEnvironment roundEnvironment);

    @BindsInstance
    Builder setMessager(Messager messager);

    @BindsInstance
    Builder setInputAnnotations(@InputAnnotations Set<? extends TypeElement> inputAnnotations);

    ExtractionComponent build();
  }
}
