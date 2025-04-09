package uz.payme.otabek.presentation.actions_handler

sealed interface AppMainScreenAction {
    data class OpenWebView(val url: String) : AppMainScreenAction
}