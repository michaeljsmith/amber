package org.wizen.amber.compilation.functions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;

@Retention(RetentionPolicy.SOURCE)
@Qualifier
public @interface InputBindingFunction {}
