package org.wizen.amber.extraction;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BindingFunction {
  abstract String name();

  public static BindingFunction create(String name) {
    return new AutoValue_BindingFunction(name);
  }
}
