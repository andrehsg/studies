package com.daitan.pocmiddleware.modules;

import com.daitan.pocmiddleware.api.ProductCache;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class ProductModule extends AbstractModule {

  @Provides
  @Singleton
  ProductCache provideProductCache () {
    return new ProductCache();
  }
}
