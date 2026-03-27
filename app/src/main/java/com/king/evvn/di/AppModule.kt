package com.king.evvn.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.king.evvn.EVVNApplication
import com.king.evvn.data.repository.LoginRepositoryImpl
import com.king.evvn.data.source.local.SessionManager
import com.king.evvn.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideEVVNApplication(
        @ApplicationContext context: Context
    ): EVVNApplication {
        return context as EVVNApplication
    }

    private const val DATASTORE_NAME = "session_prefs"

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(DATASTORE_NAME)
            }
        )
    }

    @Provides
    @Singleton
    fun provideSessionManager(
        dataStore: DataStore<Preferences>
    ): SessionManager {
        return SessionManager(dataStore)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        sessionManager: SessionManager
    ): LoginRepository {
        return LoginRepositoryImpl(sessionManager)
    }
}