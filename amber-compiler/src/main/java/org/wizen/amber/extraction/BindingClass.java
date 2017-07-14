package org.wizen.amber.extraction;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

@AutoValue
public abstract class BindingClass {
  public abstract String name();
  public abstract ImmutableSet<BindingFunction> bindingFunctions();

  public static BindingClass create(String name, ImmutableSet<BindingFunction> bindingFunctions) {
    return new AutoValue_BindingClass(name, bindingFunctions);
  }
}
