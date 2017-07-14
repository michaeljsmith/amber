package org.wizen.amber.extraction;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.processing.RoundEnvironment;
import javax.inject.Qualifier;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.wizen.amber.round.InputAnnotations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;

import dagger.Module;
import dagger.Provides;

@Module
public class ExtractionModule {
  @Provides
  @BindingClasses
  ImmutableSet<BindingClass> provideBindingClasses() {
    return ImmutableSet.of(BindingClass.create("Foo", ImmutableSet.of(BindingFunction.create("foo"))));
  }

  @Provides
  static Function<String, Collection<? extends Element>> provideAnnotatedElements(
      RoundEnvironment roundEnvironment,
      @ElementsByName ImmutableMultimap<String, ? extends TypeElement> elementsByName) {
    return name -> {
      Collection<? extends TypeElement> typeElements =
          elementsByName.asMap().getOrDefault(name, ImmutableList.of());
      return typeElements.stream()
          .flatMap(typeElement -> roundEnvironment.getElementsAnnotatedWith(typeElement).stream())
          .collect(ImmutableList.toImmutableList());
    };
  }

  @Provides
  @ElementsByName
  static ImmutableMultimap<String, ? extends TypeElement> provideElementsByName(
      @InputAnnotations Set<? extends TypeElement> inputAnnotations) {
    return Multimaps.index(
        inputAnnotations, annotation -> annotation.getQualifiedName().toString());
  }

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface ElementsByName {}
}
