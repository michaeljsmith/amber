package org.wizen.amber.compilation.output;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

import org.wizen.amber.compilation.CompiledClasses;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import dagger.Module;
import dagger.Provides;

@Module
public class OutputModule {
  @Provides
  static RoundWriter provideRoundWriter(
      @CompiledClasses ImmutableSet<TypeSpec> compiledClasses, Filer filer) {
    return () -> {
      for (TypeSpec compiledClass : compiledClasses) {
        JavaFileObject builderFile;
        try {
          JavaFile generatedFile = JavaFile.builder("out", compiledClass).build();
          builderFile = filer.createSourceFile("Foo");
          try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            generatedFile.writeTo(out);
          }
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    };
  }
}
