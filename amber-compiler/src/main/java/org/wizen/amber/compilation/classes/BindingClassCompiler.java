package org.wizen.amber.compilation.classes;

import java.util.Optional;

import org.wizen.amber.extraction.BindingClass;

public interface BindingClassCompiler {
  Optional<CompiledClass> compiledClass(BindingClass bindingClass);
}
