package uz.payme.data.repository.impl

import uz.payme.data.local.shared_pref.LocalStorage
import uz.payme.data.repository.UtilsRepository
import javax.inject.Inject

class UtilsRepositoryImpl @Inject constructor(private val storage: LocalStorage) : UtilsRepository {
    override fun getTheme(): Boolean = storage.isDark
    override fun setTheme(isDark: Boolean) {
        storage.isDark = isDark
    }
}