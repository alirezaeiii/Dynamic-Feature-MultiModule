package com.android.sample.feature.list.viewmodel

import com.android.sample.common.base.BaseRepository
import com.android.sample.common.base.BaseViewModel
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.response.Dashboard
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    repository: BaseRepository<Dashboard>,
    schedulerProvider: BaseSchedulerProvider,
) : BaseViewModel<Dashboard>(repository, schedulerProvider)