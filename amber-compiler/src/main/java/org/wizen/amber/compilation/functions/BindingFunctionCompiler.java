package org.wizen.amber.compilation.functions;

import java.util.Optional;

import org.wizen.amber.extraction.BindingFunction;

import com.squareup.javapoet.MethodSpec;

public interface BindingFunctionCompiler {
  Optional<CompiledFunction> compiledFunction(BindingFunction bindingFunction);
}
