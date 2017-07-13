package org.wizen.amber.compilation.functions;

import org.wizen.amber.BindingFunction;

import com.squareup.javapoet.MethodSpec;

public interface BindingFunctionCompiler {
  MethodSpec compiledFunction(BindingFunction bindingFunction);
}
