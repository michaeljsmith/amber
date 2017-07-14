package org.wizen.amber.processor;

import org.wizen.amber.extraction.BindingClass;
import org.wizen.amber.extraction.BindingClasses;
import org.wizen.amber.extraction.ExtractionModule;

import com.google.common.collect.ImmutableSet;

import dagger.Component;

@Component(modules = ExtractionModule.class)
public interface ExtractionComponent {
  @BindingClasses ImmutableSet<BindingClass> bindingClasses();
}
