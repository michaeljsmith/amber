package org.wizen.amber.extraction;

import static javax.tools.Diagnostic.Kind.WARNING;

import java.util.Optional;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import com.google.common.collect.ImmutableSet;

@AutoFactory
class BindingFunctionElementConvertor {

  private final Messager messager;

  @SuppressWarnings("unused")
  private final TypeElement bindingClassElement;

  public BindingFunctionElementConvertor(
      @Provided Messager messager, TypeElement bindingClassElement) {
    this.messager = messager;
    this.bindingClassElement = bindingClassElement;
  }

  public Optional<BindingFunction> bindingFunctionForElement(Element bindingFunctionElement) {
    return new Instance(bindingFunctionElement).bindingFunction();
  }

  class Instance {
    private final Element bindingFunctionElement;

    public Instance(Element bindingFunctionElement) {
      this.bindingFunctionElement = bindingFunctionElement;
    }

    public Optional<BindingFunction> bindingFunction() {
      try {
        checkAnnotatedWithBindingFunction();
        ExecutableElement executableElement = checkIsMethod();
        return Optional.of(
            BindingFunction.create(
                bindingFunctionElement.getSimpleName().toString(), executableElement));
      } catch (BindingFunctionConversionException e) {
        return Optional.empty();
      }
    }

    private void checkAnnotatedWithBindingFunction() throws BindingFunctionConversionException {
      ImmutableSet<String> annotationNames =
          bindingFunctionElement
              .getAnnotationMirrors()
              .stream()
              .map(this::qualifiedNameFromAnnotationMirror)
              .filter(Optional::isPresent)
              .map(Optional::get)
              .collect(ImmutableSet.toImmutableSet());

      if (!annotationNames.contains(org.wizen.amber.BindingFunction.class.getName())) {
        throw new BindingFunctionConversionException();
      }
    }

    private ExecutableElement checkIsMethod() throws BindingFunctionConversionException {
      if (bindingFunctionElement.getKind() != ElementKind.METHOD) {
        messager.printMessage(
            WARNING,
            String.format(
                "@%s must only be applied to methods",
                org.wizen.amber.BindingFunction.class.getSimpleName()),
            bindingFunctionElement);

        throw new BindingFunctionConversionException();
      }

      return (ExecutableElement) bindingFunctionElement;
    }

    private Optional<String> qualifiedNameFromAnnotationMirror(AnnotationMirror annotationMirror) {
      Element annotationElement = annotationMirror.getAnnotationType().asElement();
      TypeElement annotationTypeElement;
      try {
        annotationTypeElement = (TypeElement) annotationElement;
      } catch (ClassCastException e) {
        messager.printMessage(
            WARNING, "Annotation element not instance of TypeElement", annotationElement);
        return Optional.empty();
      }

      return Optional.of(annotationTypeElement.getQualifiedName().toString());
    }
  }

  private static class BindingFunctionConversionException extends Exception {}
}
