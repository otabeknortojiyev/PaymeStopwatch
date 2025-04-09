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

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

class DataStoreRepositoryImpl @Inject constructor(@ApplicationContext val context: Context) : DataStoreRepository {

    override suspend fun saveTime(time: Long) {
        context.dataStore.edit { pref ->
            pref[longPreferencesKey(TIME_KEY)] = time
        }
    }

    override fun getTime() = context.dataStore.data.map { pref ->
        return@map pref[longPreferencesKey(TIME_KEY)] ?: 0L
    }

    override suspend fun saveWasRunning(wasRunning: Boolean) {
        context.dataStore.edit { pref ->
            pref[booleanPreferencesKey(WAS_RUNNING_KEY)] = wasRunning
        }
    }

    override fun getWasRunning(): Flow<Boolean> = context.dataStore.data.map { pref ->
        return@map pref[booleanPreferencesKey(WAS_RUNNING_KEY)] == true
    }

    override suspend fun saveIsFirst(isFirst: Boolean) {
        context.dataStore.edit { pref ->
            pref[booleanPreferencesKey(IS_FIRST_KEY)] = isFirst
        }
    }

    override fun getIsFirst(): Flow<Boolean> = context.dataStore.data.map { pref ->
        return@map pref[booleanPreferencesKey(IS_FIRST_KEY)] != false
    }

    companion object {
        private const val TIME_KEY: String = "Time"
        private const val WAS_RUNNING_KEY: String = "WasRunning"
        private const val IS_FIRST_KEY: String = "IsFirst"
    }
}