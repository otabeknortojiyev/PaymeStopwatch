package uz.payme.otabek

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun formatTime(milliseconds: Long): String {
    val hours = (milliseconds / 3600000) % 24
    val minutes = (milliseconds / 60000) % 60
    val seconds = (milliseconds / 1000) % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}