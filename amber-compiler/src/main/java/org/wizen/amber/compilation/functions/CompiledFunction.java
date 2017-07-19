package org.wizen.amber.compilation.functions;

import com.google.auto.value.AutoValue;
import com.squareup.javapoet.MethodSpec;

@AutoValue
public abstract class CompiledFunction {

  public abstract MethodSpec apiMethod();

  public abstract MethodSpec implMethod();

  static CompiledFunction create(MethodSpec apiMethod, MethodSpec implMethod) {
    return new AutoValue_CompiledFunction(apiMethod, implMethod);
  }
}
