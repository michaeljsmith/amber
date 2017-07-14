package org.wizen.amber.round;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.processing.RoundEnvironment;
import javax.inject.Qualifier;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.sun.tools.classfile.Annotation;

import dagger.Module;
import dagger.Provides;

@Module
public class RoundModule {
}
