package uz.payme.otabek.data.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveTime(time: Long)
    fun getTime(): Flow<Long>
    suspend fun saveWasRunning(wasRunning: Boolean)
    fun getWasRunning(): Flow<Boolean>
    suspend fun saveIsFirst(isFirst: Boolean)
    fun getIsFirst(): Flow<Boolean>
}