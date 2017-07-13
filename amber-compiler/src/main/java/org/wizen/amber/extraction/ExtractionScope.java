package org.wizen.amber.extraction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Scope;

@Retention(RetentionPolicy.SOURCE)
@Scope
public @interface ExtractionScope {
}
