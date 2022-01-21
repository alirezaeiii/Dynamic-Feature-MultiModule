package com.android.sample.feature.list.di

import com.android.sample.core.di.CoreComponent
import com.android.sample.core.di.FeatureScope
import com.android.sample.feature.list.ui.DashboardFragment
import dagger.Component

@FeatureScope
@Component(modules = [DashboardModule::class],
    dependencies = [CoreComponent::class]
)
interface DashboardComponent {

    fun inject(dashboardFragment: DashboardFragment)
}