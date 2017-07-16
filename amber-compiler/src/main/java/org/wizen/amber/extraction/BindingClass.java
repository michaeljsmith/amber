package org.wizen.amber.extraction;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class BindingClass {
  public abstract String name();
  public abstract ImmutableList<BindingFunction> bindingFunctions();

  public static BindingClass create(String name, ImmutableList<BindingFunction> bindingFunctions) {
    return new AutoValue_BindingClass(name, bindingFunctions);
  }
}
