package org.wizen.amber.extraction;

import java.util.Optional;

import javax.inject.Inject;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

class EnclosingClassFinder {
  @Inject
  public EnclosingClassFinder() {}

  public Optional<TypeElement> enclosingClass(Element methodElement) {
    Element enclosingElement = methodElement.getEnclosingElement();
    if (enclosingElement.getKind() != ElementKind.CLASS) {
      throw new RuntimeException("Foo");
    }
    return Optional.of((TypeElement) enclosingElement);
  }
}
