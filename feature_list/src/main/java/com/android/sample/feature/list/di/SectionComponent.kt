package com.android.sample.feature.list.di

import com.android.sample.core.di.CoreComponent
import com.android.sample.core.di.FeatureScope
import com.android.sample.feature.list.ui.SectionFragment
import dagger.Component

@FeatureScope
@Component(modules = [SectionModule::class],
    dependencies = [CoreComponent::class]
)
interface SectionComponent {

    fun inject(sectionFragment: SectionFragment)
}