package com.example.mycontacts.di

import android.content.Context
import com.example.mycontacts.repos.ContactsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContactsRepository(@ApplicationContext appContext: Context): ContactsRepository {
        return ContactsRepository(appContext)
    }

}