package org.wizen.amber.compilation.classes;

import org.wizen.amber.extraction.BindingClass;

import com.squareup.javapoet.TypeSpec;

public interface BindingClassCompiler {
  TypeSpec compiledClass(BindingClass bindingClass);
}
