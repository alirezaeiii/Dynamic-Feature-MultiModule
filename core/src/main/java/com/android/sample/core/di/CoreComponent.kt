package com.android.sample.core.di

import android.content.Context
import com.android.sample.common.base.BaseRepository
import com.android.sample.common.di.UtilsModule
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.database.dashboard.DashboardDao
import com.android.sample.core.database.section.SectionDao
import com.android.sample.core.network.ApiService
import com.android.sample.core.response.Dashboard
import com.android.sample.core.response.Section
import dagger.Component
import javax.inject.Singleton

/**
 * Core component that all module's components depend on.
 *
 * @see Component
 */
@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        UtilsModule::class,
        RepositoryModule::class]
)
interface CoreComponent {

    /**
     * Provide dependency graph Context
     *
     * @return Context
     */
    fun context(): Context

    /**
     * Provide dependency graph apiService
     *
     * @return MarvelService
     */
    fun apiService(): ApiService

    /**
     * Provide dependency graph DashboardDao
     *
     * @return DashboardDao
     */
    fun dashboardDao(): DashboardDao

    /**
     * Provide dependency graph SectionDao
     *
     * @return SectionDao
     */
    fun sectionDao(): SectionDao

    /**
     * Provide dependency graph SchedulerProvider
     *
     * @return BaseSchedulerProvider
     */
    fun schedulerProvider(): BaseSchedulerProvider

    /**
     * Provide dependency graph DashboardRepository
     *
     * @return BaseRepository
     */
    fun dashboardRepository(): BaseRepository<Dashboard>

    /**
     * Provide dependency graph SectionRepository
     *
     * @return BaseRepository
     */
    fun sectionRepository(): BaseRepository<Section>
}