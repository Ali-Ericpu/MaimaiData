package org.plantalpha.maimaidata.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.plantalpha.maimaidata.repository.SongDatabase
import org.plantalpha.maimaidata.util.Constants.SONG_DATABASE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SongDatabase {
        return Room.databaseBuilder(context, SongDatabase::class.java, SONG_DATABASE).build()

    }

}