package org.wizen.amber.processor;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;

public class BindingFunctionProcessorTest {

  @Test
  public void test() {
    Compilation compilation =
        javac()
            .withProcessors(new BindingFunctionProcessor())
            .compile(
                JavaFileObjects.forResource(
                    "processing/empty-binding-function/input/EmptyBindingFunction.java"));
    assertThat(compilation).succeeded();
    assertThat(compilation)
        .generatedSourceFile("AmberEmptyBindingFunction")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "processing/empty-binding-function/output/AmberEmptyBindingFunction.java"));
    assertThat(compilation)
        .generatedSourceFile("AmberEmptyBindingFunction_impl")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "processing/empty-binding-function/output/AmberEmptyBindingFunction_impl.java"));
  }
}
