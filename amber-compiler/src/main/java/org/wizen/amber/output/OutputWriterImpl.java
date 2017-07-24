package org.wizen.amber.output;

import static javax.tools.Diagnostic.Kind.ERROR;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.inject.Inject;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import org.wizen.amber.compilation.CompiledClasses;
import org.wizen.amber.compilation.classes.CompiledClass;

import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public class OutputWriterImpl implements OutputWriter {
  private final ImmutableList<CompiledClass> compiledClasses;
  private final Filer filer;
  private final Messager messager;

  @Inject
  public OutputWriterImpl(
      @CompiledClasses ImmutableList<CompiledClass> compiledClasses,
      Filer filer,
      Messager messager) {
    this.compiledClasses = compiledClasses;
    this.filer = filer;
    this.messager = messager;
  }

  @Override
  public void writeClasses() {
    for (CompiledClass compiledClass : compiledClasses) {
      writeClassFile(
          compiledClass.packageName(), compiledClass.apiTypeSpec(), compiledClass.inputElement());
      writeClassFile(
          compiledClass.packageName(), compiledClass.implTypeSpec(), compiledClass.inputElement());
    }
  }

  private void writeClassFile(String packageName, TypeSpec typeSpec, TypeElement inputElement) {
    try {
      JavaFile generatedFile = JavaFile.builder(packageName, typeSpec).build();
      JavaFileObject builderFile = filer.createSourceFile(typeSpec.name);
      try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
        generatedFile.writeTo(out);
      }
    } catch (IOException e) {
      messager.printMessage(ERROR, "Unable to open file for writing: " + e, inputElement);
    }
  }
}
