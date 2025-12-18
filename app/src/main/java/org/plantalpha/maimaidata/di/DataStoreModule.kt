package org.plantalpha.maimaidata.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.plantalpha.maimaidata.repository.DataRepository
import org.plantalpha.maimaidata.repository.DataStoreManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providerDataStoreModule(
        @ApplicationContext context: Context
    ) = DataStoreManager(context)

    @Provides
    @Singleton
    fun providerDataRepository(dataStoreManager: DataStoreManager): DataRepository {
        return DataRepository(dataStoreManager)
    }

}