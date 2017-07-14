package org.wizen.amber.compilation.classes;

import javax.lang.model.element.Modifier;

import org.wizen.amber.compilation.functions.BindingFunctionCompiler;
import org.wizen.amber.extraction.BindingClass;

import com.squareup.javapoet.TypeSpec;

import dagger.Module;
import dagger.Provides;

@Module
public class BindingClassCompilationBindingClassModule {
  @Provides
  @BindingClassCompilationResult
  static TypeSpec provideBindingClassCompilationResult(
      @InputBindingClass BindingClass bindingClass,
      BindingFunctionCompiler bindingFunctionCompiler) {
    return TypeSpec.classBuilder("Foo").build();
  }
}
