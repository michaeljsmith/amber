package org.wizen.amber.compilation.classes;

import java.util.Optional;

import org.wizen.amber.extraction.BindingClass;

import com.squareup.javapoet.TypeSpec;

public interface BindingClassCompiler {
  Optional<TypeSpec> compiledClass(BindingClass bindingClass);
}
