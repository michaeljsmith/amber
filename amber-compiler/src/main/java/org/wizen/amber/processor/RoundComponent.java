package org.wizen.amber.processor;

import dagger.Component;

@Component(modules={ProcessingEnvironmentRoundModule.class})
interface RoundComponent {
  ProcessingEnvironmentRoundModule.Foo foo();
}
