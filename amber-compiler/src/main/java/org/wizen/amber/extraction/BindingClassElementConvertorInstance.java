package org.wizen.amber.extraction;

import java.util.Optional;

import javax.lang.model.element.TypeElement;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import com.google.common.collect.ImmutableList;

@AutoFactory(
    className = "BindingClassElementConvertor",
    extending = BindingClassElementConvertorInstance.Factory.class)
class BindingClassElementConvertorInstance {
  private final BindingFunctionElementConvertor bindingFunctionElementConvertor;
  private final TypeElement bindingClassElement;

  public abstract static class Factory {
    abstract BindingClassElementConvertorInstance create(TypeElement bindingClassElement);

    public Optional<BindingClass> bindingClassFromElement(TypeElement bindingClassElement) {
      return create(bindingClassElement).bindingClass();
    }
  }

  public BindingClassElementConvertorInstance(
      @Provided BindingFunctionElementConvertorFactory bindingFunctionElementConvertorFactory,
      TypeElement bindingClassElement) {
    this.bindingFunctionElementConvertor =
        bindingFunctionElementConvertorFactory.create(bindingClassElement);
    this.bindingClassElement = bindingClassElement;
  }

  public Optional<BindingClass> bindingClass() {
    return Optional.of(
        BindingClass.create(
            bindingClassElement.getSimpleName().toString(),
            bindingClassElement,
            bindingFunctions()));
  }

  private ImmutableList<BindingFunction> bindingFunctions() {
    return bindingClassElement.getEnclosedElements().stream()
        .map(bindingFunctionElementConvertor::bindingFunctionForElement)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(ImmutableList.toImmutableList());
  }
}
