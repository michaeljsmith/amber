package org.wizen.amber.output;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class OutputModule {
  @Binds
  abstract OutputWriter provideOutputWriter(OutputWriterImpl impl);
}
