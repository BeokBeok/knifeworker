package com.beok.knifeworker.di

import android.content.Context
import com.beok.knifeworker.inapp.InAppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesAppUpdateManager(@ApplicationContext context: Context) =
        AppUpdateManagerFactory.create(context)

    @Provides
    @Singleton
    fun providesInAppUpdateManager(appUpdateManager: AppUpdateManager) =
        InAppUpdateManager(inAppUpdateManager = appUpdateManager)
}
