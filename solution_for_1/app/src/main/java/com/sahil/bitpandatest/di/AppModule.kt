package com.sahil.bitpandatest.di

import com.sahil.bitpandatest.arch.repository.Repository
import com.sahil.bitpandatest.remote.DummyWebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    fun provideData() = Repository(DummyWebService())
}

