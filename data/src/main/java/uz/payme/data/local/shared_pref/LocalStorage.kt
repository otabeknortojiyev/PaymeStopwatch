package uz.payme.data.local.shared_pref

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class LocalStorage @Inject constructor(@ApplicationContext context: Context) : SharedPreference(context) {
    var isDark by booleans(false)
}