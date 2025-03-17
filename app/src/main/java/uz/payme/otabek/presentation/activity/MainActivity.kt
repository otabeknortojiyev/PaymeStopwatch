package uz.payme.otabek.presentation.activity

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.payme.otabek.ui.theme.PaymeStopwatchTheme
import uz.payme.otabek.R
import uz.payme.otabek.presentation.screens.stopwatch.StopWatchScreen
import uz.payme.otabek.presentation.screens.weather.WeatherScreen

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var startStopPlayer: MediaPlayer? = null
    private var resetPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*Intent(this, AppService::class.java).also { intent ->
            bindService(intent, serviceConnection, BIND_AUTO_CREATE)
        }*/

        startStopPlayer = MediaPlayer.create(this, R.raw.boop)
        resetPlayer = MediaPlayer.create(this, R.raw.breeze)

        setContent {
            PaymeStopwatchTheme {
                val items = listOf(
                    NavigationItem(
                        title = "Секундомер",
                        selectedIcon = R.drawable.timer_blue,
                        unselectedIcon = R.drawable.timer_gray,
                        screenTitle = "Секундомер"
                    ), NavigationItem(
                        title = "Погода",
                        selectedIcon = R.drawable.weather_yellow,
                        unselectedIcon = R.drawable.weather_gray,
                        screenTitle = "Погода"
                    )
                )
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    var selectedItemIndex by rememberSaveable {
                        mutableIntStateOf(0)
                    }
                    val scope = rememberCoroutineScope()
                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(modifier = Modifier.height(16.dp))
                                items.forEachIndexed { index, item ->
                                    NavigationDrawerItem(
                                        label = {
                                            Text(text = item.title)
                                        }, selected = index == selectedItemIndex, onClick = {
                                            scope.launch {
                                                selectedItemIndex = index
                                                drawerState.close()
                                            }
                                        }, icon = {
                                            Image(
                                                painter = if (index == selectedItemIndex) {
                                                    painterResource(item.selectedIcon)
                                                } else {
                                                    painterResource(item.unselectedIcon)
                                                }, contentDescription = item.title
                                            )
                                        }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                    )
                                }
                            }
                        }, drawerState = drawerState
                    ) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    colors = TopAppBarColors(
                                        containerColor = Color.White,
                                        scrolledContainerColor = Color.Transparent,
                                        navigationIconContentColor = Color.Black,
                                        titleContentColor = Color.Black,
                                        actionIconContentColor = Color.Transparent
                                    ), title = {
                                        Text(text = items[selectedItemIndex].screenTitle)
                                    }, navigationIcon = {
                                        IconButton(onClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }) {
                                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                                        }
                                    })
                            }) { paddingValues ->
                            when (selectedItemIndex) {
                                0 -> StopWatchScreen(
                                    modifier = Modifier.padding(paddingValues),
                                    startPlayer = { startStopPlayer?.start() },
                                    resetPlayer = { resetPlayer?.start() })

                                1 -> WeatherScreen(
                                    modifier = Modifier.padding(paddingValues)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        startStopPlayer?.release()
        resetPlayer?.release()
        startStopPlayer = null
        resetPlayer = null/*if (bound) {
            unbindService(serviceConnection)
            bound = false
        }*/
    }
}

data class NavigationItem(
    val title: String, val selectedIcon: Int, val unselectedIcon: Int, val screenTitle: String
)