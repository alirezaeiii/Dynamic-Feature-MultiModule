package com.android.sample.app.di

import com.android.sample.core.di.AppScope
import com.android.sample.core.di.CoreComponent
import com.android.sample.app.Application
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class]
)
interface AppComponent {

    fun inject(application: Application)
}