package com.android.sample.feature.list.di

import androidx.navigation.fragment.navArgs
import com.android.sample.common.base.BaseRepository
import com.android.sample.common.extension.viewModel
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.response.Link
import com.android.sample.core.response.Section
import com.android.sample.feature.list.ui.SectionFragment
import com.android.sample.feature.list.ui.SectionFragmentArgs
import com.android.sample.feature.list.viewmodel.SectionViewModel
import dagger.Module
import dagger.Provides

@Module
class SectionModule(private val fragment: SectionFragment) {

    @Provides
    fun provideSectionViewModel(
        repository: BaseRepository<Section>,
        schedulerProvider: BaseSchedulerProvider,
        link: Link
    ) = fragment.viewModel {
        SectionViewModel(repository, schedulerProvider, link)
    }

    @Provides
    internal fun provideLink(): Link {
        val args: SectionFragmentArgs by fragment.navArgs()
        return args.link
    }
}