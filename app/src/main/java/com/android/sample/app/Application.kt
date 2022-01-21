package com.android.sample.app

import android.content.Context
import com.android.sample.app.di.DaggerAppComponent
import com.android.sample.core.di.ContextModule
import com.android.sample.core.di.CoreComponent
import com.android.sample.core.di.DaggerCoreComponent
import timber.log.Timber

class Application : android.app.Application() {

    lateinit var coreComponent: CoreComponent

    companion object {

        /**
         * Obtain core dagger component.
         *
         * @param context The application context
         */
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as? Application)?.coreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initCoreDependencyInjection()
        initAppDependencyInjection()
    }

    // ============================================================================================
    //  Private init methods
    // ============================================================================================

    /**
     * Initialize app dependency injection component.
     */
    private fun initAppDependencyInjection() {
        DaggerAppComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    /**
     * Initialize core dependency injection component.
     */
    private fun initCoreDependencyInjection() {
        coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    /**
     * Initialize log library Timber only on debug build.
     */
    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}