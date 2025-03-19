package uz.payme.otabek.presentation.activity

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.payme.otabek.ui.theme.PaymeStopwatchTheme
import uz.payme.otabek.R
import uz.payme.otabek.presentation.screens.stopwatch.StopWatchScreen
import uz.payme.otabek.presentation.screens.weather.WeatherScreen
import uz.payme.otabek.presentation.screens.weather.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var startStopPlayer: MediaPlayer? = null
    private var resetPlayer: MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.O)
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
                        // TODO hard coded string literals stringResource(R.string.blabla)
                        title = "Секундомер",
                        selectedIcon = R.drawable.timer_blue,
                        unselectedIcon = R.drawable.timer_gray,
                        screenTitle = "Секундомер"
                    ), NavigationItem(
                        title = "Погода",
                        selectedIcon = R.drawable.weather_yellow,
                        unselectedIcon = R.drawable.weather_gray,
                        screenTitle = "Ташкент"
                    )
                )
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    var selectedItemIndex by remember { mutableIntStateOf(1) }
                    val scope = rememberCoroutineScope()

                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet(
                                modifier = Modifier.fillMaxHeight(),
                                drawerContainerColor = Color(0xFF2196F3),
                                drawerContentColor = Color.White
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                items.forEachIndexed { index, item ->
                                    NavigationDrawerItem(
                                        label = {
                                        Text(
                                            text = item.title,
                                            color = if (index == selectedItemIndex) Color.White else Color.LightGray,
                                            fontWeight = if (index == selectedItemIndex) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 16.sp,
                                            modifier = Modifier.padding(vertical = 8.dp)
                                        )
                                    },
                                        selected = index == selectedItemIndex,
                                        onClick = {
                                            selectedItemIndex = index
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        },
                                        icon = {
                                            Image(
                                                painter = painterResource(
                                                    id = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon
                                                ),
                                                contentDescription = item.title,
                                                modifier = Modifier.size(24.dp),
                                                colorFilter = if (index == selectedItemIndex) {
                                                    ColorFilter.tint(Color.White)
                                                } else {
                                                    ColorFilter.tint(Color.LightGray)
                                                }
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 12.dp, vertical = 8.dp)
                                            .animateContentSize(),
                                        colors = NavigationDrawerItemDefaults.colors(
                                            selectedContainerColor = Color(0xFF1976D2),
                                            unselectedContainerColor = Color.Transparent,
                                            selectedTextColor = Color.White,
                                            unselectedTextColor = Color.LightGray,
                                            selectedIconColor = Color.White,
                                            unselectedIconColor = Color.LightGray
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                }
                            }
                        }, drawerState = drawerState
                    ) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    colors = TopAppBarColors(
                                    containerColor = Color(0xFF5999e0),
                                    scrolledContainerColor = Color.Transparent,
                                    navigationIconContentColor = Color.White,
                                    titleContentColor = Color.White,
                                    actionIconContentColor = Color.White
                                ), title = {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = items[selectedItemIndex].screenTitle)
                                        when (selectedItemIndex) {
                                            1 -> Image(
                                                painter = painterResource(R.drawable.location),
                                                contentDescription = "Location",
                                                modifier = Modifier
                                                    .width(16.dp)
                                                    .height(16.dp)
                                            )
                                        }
                                    }
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
                                    modifier = Modifier.padding(paddingValues),
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