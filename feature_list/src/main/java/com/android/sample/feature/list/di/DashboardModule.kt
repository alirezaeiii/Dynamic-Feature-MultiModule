package com.android.sample.feature.list.di

import com.android.sample.common.base.BaseRepository
import com.android.sample.common.extension.viewModel
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.response.Dashboard
import com.android.sample.feature.list.ui.DashboardFragment
import com.android.sample.feature.list.viewmodel.DashboardViewModel
import dagger.Module
import dagger.Provides

@Module
class DashboardModule(private val fragment: DashboardFragment) {

    @Provides
    fun providesDashboardViewModel(
        repository: BaseRepository<Dashboard>,
        schedulerProvider: BaseSchedulerProvider
    ) = fragment.viewModel {
        DashboardViewModel(repository, schedulerProvider)
    }
}