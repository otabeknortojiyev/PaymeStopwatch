package uz.payme.otabek.presentation.activity

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import uz.payme.otabek.ui.theme.PaymeStopwatchTheme
import uz.payme.otabek.R
import uz.payme.otabek.presentation.actions_handler.AppMainScreenAction
import uz.payme.otabek.presentation.screens.AppMainScreen
import uz.payme.otabek.presentation.screens.news.items.WebViewScreen
import javax.inject.Inject
import kotlin.getValue

val darkTheme = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    object Route {
        const val mainApp = "MainApp"
        const val webView = "WebView"
    }

    private var startStopPlayer: MediaPlayer? = null
    private var resetPlayer: MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startStopPlayer = MediaPlayer.create(this, R.raw.boop)
        resetPlayer = MediaPlayer.create(this, R.raw.breeze)

        setContent {
            val viewModel: MainActivityViewModel = hiltViewModel()
            val navController = rememberNavController()
            darkTheme.value = viewModel.getTheme()

            PaymeStopwatchTheme(darkTheme = darkTheme.value) {
                NavHost(
                    navController = navController, startDestination = Route.mainApp,
                ) {
                    composable(Route.mainApp) {
                        AppMainScreen(
                            startStopPlayer = { startStopPlayer?.start() },
                            resetPlayer = { resetPlayer?.start() },
                            setTheme = { value -> viewModel.setTheme(value) },
                            actions = { action ->
                                handleAppMainScreenAction(navController, action)
                            })
                    }

                    composable(
                        route = "${Route.webView}/{url}",
                        arguments = listOf(navArgument("url") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val url = backStackEntry.arguments?.getString("url") ?: ""
                        WebViewScreen(url = url)
                    }
                }
            }
        }
    }

    private fun handleAppMainScreenAction(navController: NavController, action: AppMainScreenAction) {
        when (action) {
            is AppMainScreenAction.OpenWebView -> {
                val encodedUrl = Uri.encode(action.url)
                navController.navigate("${Route.webView}/$encodedUrl") {
                    launchSingleTop = false
                    restoreState = true
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        startStopPlayer?.release()
        resetPlayer?.release()
        startStopPlayer = null
        resetPlayer = null
    }
}