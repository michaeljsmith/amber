package org.wizen.amber.round;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Retention(RetentionPolicy.SOURCE)
@Qualifier
public @interface AnnotatedElementAccessor {
}