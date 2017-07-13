package org.wizen.amber.extraction;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

@AutoValue
public abstract class BindingClass {
  public abstract ImmutableSet<BindingFunction> bindingFunctions();

  public static BindingClass create(ImmutableSet<BindingFunction> bindingFunctions) {
    return new AutoValue_BindingClass(bindingFunctions);
  }
}
