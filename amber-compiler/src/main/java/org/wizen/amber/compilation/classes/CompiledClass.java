package org.wizen.amber.compilation.classes;

import javax.lang.model.element.TypeElement;

import com.google.auto.value.AutoValue;
import com.squareup.javapoet.TypeSpec;

@AutoValue
public abstract class CompiledClass {

  public abstract String packageName();

  public abstract TypeSpec typeSpec();

  public abstract TypeElement inputElement();

  public static CompiledClass create(
      String packageName, TypeSpec typeSpec, TypeElement inputElement) {
    return new AutoValue_CompiledClass(packageName, typeSpec, inputElement);
  }
}
