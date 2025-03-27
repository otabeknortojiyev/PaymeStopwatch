package uz.payme.data.repository.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.payme.data.repository.DataStoreRepository
import javax.inject.Inject

const val timeKey: String = "Time"
const val wasRunningKey: String = "WasRunning"
const val isFirstKey: String = "IsFirst"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

class DataStoreRepositoryImpl @Inject constructor(@ApplicationContext val context: Context) : DataStoreRepository {

    override suspend fun saveTime(time: Long) {
        context.dataStore.edit { pref ->
            pref[longPreferencesKey(timeKey)] = time
        }
    }

    override fun getTime() = context.dataStore.data.map { pref ->
        return@map pref[longPreferencesKey(timeKey)] ?: 0L
    }

    override suspend fun saveWasRunning(wasRunning: Boolean) {
        context.dataStore.edit { pref ->
            pref[booleanPreferencesKey(wasRunningKey)] = wasRunning
        }
    }

    override fun getWasRunning(): Flow<Boolean> = context.dataStore.data.map { pref ->
        return@map pref[booleanPreferencesKey(wasRunningKey)] == true
    }

    override suspend fun saveIsFirst(isFirst: Boolean) {
        context.dataStore.edit { pref ->
            pref[booleanPreferencesKey(isFirstKey)] = isFirst
        }
    }

    override fun getIsFirst(): Flow<Boolean> = context.dataStore.data.map { pref ->
        return@map pref[booleanPreferencesKey(isFirstKey)] != false
    }
}