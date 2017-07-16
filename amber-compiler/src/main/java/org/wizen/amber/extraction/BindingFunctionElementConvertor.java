package org.wizen.amber.extraction;

import java.util.Optional;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

@AutoFactory
class BindingFunctionElementConvertor {

  private final Messager messager;
  private final TypeElement bindingClassElement;

  public BindingFunctionElementConvertor(
      @Provided Messager messager,
      TypeElement bindingClassElement) {
        this.messager = messager;
        this.bindingClassElement = bindingClassElement;
  }

  public Optional<BindingFunction> bindingFunctionForElement(Element bindingFunctionElement) {
    return new Instance(bindingFunctionElement).bindingFunction(); 
  }

  static class Instance {
    private final Element bindingFunctionElement;
  
    public Instance(Element bindingFunctionElement) {
      this.bindingFunctionElement = bindingFunctionElement;
    }

    public Optional<BindingFunction> bindingFunction() {
      return Optional.of(BindingFunction.create(bindingFunctionElement.getSimpleName().toString()));
    }
  }
}
