package uz.payme.otabek.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val timeKey: String = "Time"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

class AppDataStoreImpl(val context: Context) : AppDataStore {
    override suspend fun saveTime(time: Long) {
        context.dataStore.edit { pref ->
            pref[longPreferencesKey(timeKey)] = time
        }
    }

    override fun getTime() = context.dataStore.data.map { pref ->
        return@map pref[longPreferencesKey(timeKey)] ?: 0L
    }
}