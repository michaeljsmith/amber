package org.wizen.amber.processing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Scope;

@Retention(RetentionPolicy.SOURCE)
@Scope
public @interface RoundScope {
}
