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
  static ImmutableSet<BindingClass> provideBindingClasses() {
    return ImmutableSet.of(BindingClass.create("Foo", ImmutableSet.of(BindingFunction.create("foo"))));
  }

  @Provides
  @ClassesWithAnnotatedFunctions
  static ImmutableSet<TypeElement> provideClassesWithAnnotatedFunctions(
      @AnnotatedElementsLoader Function<String, Collection<? extends Element>>
        annotatedElementsLoader) {
    String annotationName = org.wizen.amber.BindingFunction.class.getCanonicalName();
    Collection<? extends Element> annotatedElements = annotatedElementsLoader.apply(annotationName);
    for (Element annotatedElement : annotatedElements) {
      asdf();
    }
  }

  @Provides
  @AnnotatedElementsLoader
  static Function<String, Collection<? extends Element>> provideAnnotatedElementsLoader(
      RoundEnvironment roundEnvironment,
      @InputAnnotationsByName ImmutableMultimap<String, ? extends TypeElement>
          inputAnnotationsByName) {
    return name -> {
      Collection<? extends TypeElement> typeElements =
          inputAnnotationsByName.asMap().getOrDefault(name, ImmutableList.of());
      return typeElements.stream()
          .flatMap(typeElement -> roundEnvironment.getElementsAnnotatedWith(typeElement).stream())
          .collect(ImmutableList.toImmutableList());
    };
  }

  @Provides
  @InputAnnotationsByName
  static ImmutableMultimap<String, ? extends TypeElement> provideInputAnnotationsByName(
      @InputAnnotations Set<? extends TypeElement> inputAnnotations) {
    return Multimaps.index(
        inputAnnotations, annotation -> annotation.getQualifiedName().toString());
  }

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface ClassesWithAnnotatedFunctions {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface AnnotatedElementsLoader {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  private @interface InputAnnotationsByName {}
}
