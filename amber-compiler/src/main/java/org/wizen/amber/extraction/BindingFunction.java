package org.wizen.amber.extraction;

import javax.lang.model.element.ExecutableElement;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BindingFunction {
  public abstract String name();

  public abstract ExecutableElement element();

  public static BindingFunction create(String name, ExecutableElement element) {
    return new AutoValue_BindingFunction(name, element);
  }
}
