package uz.payme.otabek.data

import kotlinx.coroutines.flow.Flow

interface AppDataStore {
    suspend fun saveTime(time: Long)
    fun getTime(): Flow<Long>
}