package org.wizen.amber.extraction;

import java.util.Optional;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import com.google.common.collect.ImmutableList;

@AutoFactory(
    className = "BindingClassElementConvertor",
    extending = BindingClassElementConvertorInstance.Factory.class)
class BindingClassElementConvertorInstance {
  private final Messager messager;
  private final BindingFunctionElementConvertor bindingFunctionElementConvertor;
  private final TypeElement bindingClassElement;

  public abstract static class Factory {
    abstract BindingClassElementConvertorInstance create(TypeElement bindingClassElement);

    public Optional<BindingClass> bindingClassFromElement(TypeElement bindingClassElement) {
      return create(bindingClassElement).bindingClass();
    }
  }

  public BindingClassElementConvertorInstance(
      @Provided Messager messager,
      @Provided BindingFunctionElementConvertorFactory bindingFunctionElementConvertorFactory,
      TypeElement bindingClassElement) {
    this.messager = messager;
    this.bindingFunctionElementConvertor =
        bindingFunctionElementConvertorFactory.create(bindingClassElement);
    this.bindingClassElement = bindingClassElement;
  }

  public Optional<BindingClass> bindingClass() {
    try {
      return Optional.of(
          BindingClass.create(
              packageName(),
              bindingClassElement.getSimpleName().toString(),
              bindingClassElement,
              bindingFunctions()));
    } catch (BindingClassConversionException e) {
      return Optional.empty();
    }
  }

  private String packageName() throws BindingClassConversionException {
    String qualifiedName = bindingClassElement.getQualifiedName().toString();
    int lastDot = qualifiedName.lastIndexOf('.');
    if (lastDot > 0) {
        return qualifiedName.substring(0, lastDot);
    }

    messager.printMessage(Kind.ERROR, "Cannot extract package name from type", bindingClassElement);
    throw new BindingClassConversionException();
  }
  
  private ImmutableList<BindingFunction> bindingFunctions() {
    return bindingClassElement.getEnclosedElements().stream()
        .map(bindingFunctionElementConvertor::bindingFunctionForElement)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(ImmutableList.toImmutableList());
  }

  private static class BindingClassConversionException extends Exception {}
}
