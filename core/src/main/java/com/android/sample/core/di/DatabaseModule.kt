package com.android.sample.core.di

import android.content.Context
import androidx.room.Room
import com.android.sample.core.BuildConfig
import com.android.sample.core.database.MyDatabase
import com.android.sample.core.database.dashboard.DbLinkConverter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMyDatabase(context: Context, moshi: Moshi): MyDatabase {
        DbLinkConverter.initialize(moshi)
        return Room.databaseBuilder(context,
            MyDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideDashboardDao(db: MyDatabase) = db.dashboardDao()

    @Singleton
    @Provides
    fun provideSectionDao(db: MyDatabase) = db.sectionDao()
}