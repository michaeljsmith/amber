package org.wizen.amber.extraction;

import java.util.Collection;

import javax.annotation.processing.RoundEnvironment;
import javax.inject.Inject;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.wizen.amber.extraction.Annotations.InputAnnotationsByName;

import com.google.auto.factory.AutoFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;

class ElementsByAnnotationNameLoader {
  private final RoundEnvironment roundEnvironment;
  private final ImmutableMultimap<String, ? extends TypeElement> inputAnnotationsByName;

  @Inject
  public ElementsByAnnotationNameLoader(
      RoundEnvironment roundEnvironment,
      @InputAnnotationsByName ImmutableMultimap<String, ? extends TypeElement>
          inputAnnotationsByName) {
            this.roundEnvironment = roundEnvironment;
            this.inputAnnotationsByName = inputAnnotationsByName;
  }

  public Collection<? extends Element> getElementsWithAnnotationName(String name) {
    Collection<? extends TypeElement> typeElements =
        inputAnnotationsByName.asMap().getOrDefault(name, ImmutableList.of());
    return typeElements.stream()
        .flatMap(typeElement -> roundEnvironment.getElementsAnnotatedWith(typeElement).stream())
        .collect(ImmutableList.toImmutableList());
  }
}
