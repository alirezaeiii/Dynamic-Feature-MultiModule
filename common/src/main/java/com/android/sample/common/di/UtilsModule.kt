package com.android.sample.common.di

import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.common.util.schedulers.SchedulerProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UtilsModule {

    @Singleton
    @Binds
    internal abstract fun bindSchedulerProvider(schedulerProvider: SchedulerProvider): BaseSchedulerProvider
}