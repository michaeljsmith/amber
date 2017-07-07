package org.wizen.amber.examples.coffee;

import org.wizen.amber.BindingFunction;
import org.wizen.amber.Implicit;

public class Coffee {
  public static void main(@Implicit String[] argv) {
    System.out.println("Hello, World!");
  }

  @BindingFunction
  public static void foo() {
    
  }
}
