package org.wizen.amber.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;

@SupportedAnnotationTypes("org.wizen.amber.BindingFunction")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class BindingFunctionProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    ExtractionComponent extractionComponent =
        DaggerExtractionComponent.builder()
            .setInputAnnotations(annotations)
            .setMessager(processingEnv.getMessager())
            .setRoundEnvironment(roundEnv)
            .build();
    CompilationComponent compilationComponent =
        DaggerCompilationComponent.builder()
            .setExtractionComponent(extractionComponent)
            .build();
    OutputComponent outputComponent =
        DaggerOutputComponent.builder()
            .setCompilationComponent(compilationComponent)
            .setFiler(processingEnv.getFiler())
            .setMessager(processingEnv.getMessager())
            .build();

    outputComponent.outputWriter().writeClasses();

    return true;
  }
}
