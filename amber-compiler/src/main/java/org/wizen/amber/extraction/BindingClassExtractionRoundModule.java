package org.wizen.amber.extraction;

import com.google.common.collect.ImmutableSet;

import dagger.Module;
import dagger.Provides;

@Module
public class BindingClassExtractionRoundModule {
  @Provides
  @BindingClasses
  ImmutableSet<BindingClass> provideCompiledFunctionsByClass() {
    return ImmutableSet.of(BindingClass.create(ImmutableSet.of(BindingFunction.create())));
  }
}
