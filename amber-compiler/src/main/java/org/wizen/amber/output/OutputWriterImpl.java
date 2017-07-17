package org.wizen.amber.output;

import static javax.tools.Diagnostic.Kind.ERROR;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.inject.Inject;
import javax.tools.JavaFileObject;

import org.wizen.amber.compilation.CompiledClasses;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public class OutputWriterImpl implements OutputWriter {
  private final ImmutableSet<TypeSpec> compiledClasses;
  private final Filer filer;
  private final Messager messager;

  @Inject
  public OutputWriterImpl(
        @CompiledClasses ImmutableSet<TypeSpec> compiledClasses, Filer filer, Messager messager) {
    this.compiledClasses = compiledClasses;
    this.filer = filer;
    this.messager = messager;
  }

  @Override
  public void writeClasses() {
    for (TypeSpec compiledClass : compiledClasses) {
      JavaFileObject builderFile;
      try {
        JavaFile generatedFile = JavaFile.builder("out", compiledClass).build();
        builderFile = filer.createSourceFile(compiledClass.name);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
          generatedFile.writeTo(out);
        }
      } catch (IOException e) {
        messager.printMessage(ERROR, "Unable to open file for writing: " + e);
      }
    }
  }
}
