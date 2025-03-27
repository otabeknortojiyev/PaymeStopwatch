package uz.payme.data.repository

interface UtilsRepository {
    fun getTheme(): Boolean
    fun setTheme(isDark: Boolean)
}