package org.wizen.amber.extraction;

import javax.lang.model.element.TypeElement;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class BindingClass {
  public abstract String packageName();

  public abstract String name();

  public abstract TypeElement inputElement();

  public abstract ImmutableList<BindingFunction> bindingFunctions();

  public static BindingClass create(
      String packageName,
      String name,
      TypeElement inputElement,
      ImmutableList<BindingFunction> bindingFunctions) {
    return new AutoValue_BindingClass(packageName, name, inputElement, bindingFunctions);
  }
}
