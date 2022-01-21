package com.android.sample.feature.list.viewmodel

import com.android.sample.common.base.BaseRepository
import com.android.sample.common.base.BaseViewModel
import com.android.sample.common.extension.cleanHref
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.response.Link
import com.android.sample.core.response.Section
import javax.inject.Inject

class SectionViewModel @Inject constructor(
    repository: BaseRepository<Section>,
    schedulerProvider: BaseSchedulerProvider,
    link: Link
) : BaseViewModel<Section>(repository, schedulerProvider, link.href.cleanHref(), link.id)