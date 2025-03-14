package uz.payme.otabek.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.payme.otabek.data.AppDataStore


class AppViewModelFactory(private val dataStore: AppDataStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            return AppViewModel(dataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}