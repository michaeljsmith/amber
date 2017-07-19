package org.wizen.amber.compilation.classes;

import javax.lang.model.element.TypeElement;

import com.google.auto.value.AutoValue;
import com.squareup.javapoet.TypeSpec;

@AutoValue
public abstract class CompiledClass {

  public abstract String packageName();

  public abstract TypeSpec apiTypeSpec();

  public abstract TypeSpec implTypeSpec();

  public abstract TypeElement inputElement();

  public static CompiledClass create(
      String packageName, TypeSpec apiTypeSpec, TypeSpec implTypeSpec, TypeElement inputElement) {
    return new AutoValue_CompiledClass(packageName, apiTypeSpec, implTypeSpec, inputElement);
  }
}
