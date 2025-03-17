package uz.payme.otabek.presentation.screens.stopwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.payme.otabek.data.AppDataStore


class StopWatchModelFactory(private val dataStore: AppDataStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StopWatchViewModel::class.java)) {
            return StopWatchViewModel(dataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}