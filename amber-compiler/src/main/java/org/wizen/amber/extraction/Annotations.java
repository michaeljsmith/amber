package org.wizen.amber.extraction;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

class Annotations {
  private Annotations() {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  @interface ClassesWithAnnotatedFunctions {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  @interface AnnotatedElementsLoader {}

  @Retention(RetentionPolicy.SOURCE)
  @Qualifier
  @interface InputAnnotationsByName {}
}
