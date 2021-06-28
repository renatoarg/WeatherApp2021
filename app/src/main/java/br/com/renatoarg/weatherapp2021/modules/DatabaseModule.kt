package br.com.renatoarg.weatherapp2021.modules

import android.app.Application
import android.content.Context
import br.com.renatoarg.data.database.ItemsDao
import br.com.renatoarg.data.database.ItemsDatabase
import br.com.renatoarg.data.database.ItemsRepository
import br.com.renatoarg.weatherapp2021.AppApplication
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@ExperimentalUnsignedTypes
val databaseModule = module(override = true) {

    fun provideDatabase(context: Context) : ItemsDatabase {
        return ItemsDatabase.getDatabase(context)
    }

    fun provideDatabaseDao(database: ItemsDatabase) : ItemsDao {
        return database.itemsDao()
    }

    fun provideItemsRepository(itemsDao: ItemsDao) : ItemsRepository {
        return ItemsRepository(itemsDao)
    }

    factory { provideDatabase(androidContext()) }
    factory { provideDatabaseDao(get()) }
    single { provideItemsRepository(get()) }
}