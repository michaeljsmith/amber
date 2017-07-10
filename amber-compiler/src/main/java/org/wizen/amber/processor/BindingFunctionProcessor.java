package org.wizen.amber.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import org.wizen.amber.processor.ProcessingEnvironmentRoundModule.Foo;

import com.google.auto.service.AutoService;

@SupportedAnnotationTypes("org.wizen.amber.BindingFunction")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class BindingFunctionProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    Foo foo = DaggerRoundComponent.builder()
        .processingEnvironmentRoundModule(new ProcessingEnvironmentRoundModule(processingEnv))
        .build()
        .foo();
    //foo.foo();
    for (TypeElement annotation : annotations) {
      Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
      for (Element element : annotatedElements) {
        //processingEnv.getMessager().printMessage(Kind.ERROR, "FOUND ANNOTATED ELEMENT", element);
        JavaFileObject builderFile;
        try {
          builderFile = processingEnv.getFiler().createSourceFile("Foo");
          try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            out.println("package foo;");
            out.println("public class Foo {}");
          }
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          return true;
        }
      }
    }
    return true;
  }
}
