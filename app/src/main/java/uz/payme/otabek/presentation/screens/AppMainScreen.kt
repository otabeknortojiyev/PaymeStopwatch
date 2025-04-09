package uz.payme.otabek.presentation.screens

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import uz.payme.otabek.R
import uz.payme.otabek.presentation.actions_handler.AppMainScreenAction
import uz.payme.otabek.presentation.activity.darkTheme
import uz.payme.otabek.presentation.screens.news.NewsMain
import uz.payme.otabek.presentation.screens.stopwatch.StopWatchScreen
import uz.payme.otabek.presentation.screens.weather.WeatherScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppMainScreen(
    startStopPlayer: () -> Unit,
    resetPlayer: () -> Unit,
    setTheme: (Boolean) -> Unit,
    actions: (AppMainScreenAction) -> Unit
) {
    val items = listOf(
        NavigationItem(
            title = stringResource(R.string.stopwatch),
            selectedIcon = R.drawable.timer_blue,
            unselectedIcon = R.drawable.timer_gray,
            screenTitle = stringResource(R.string.stopwatch)
        ), NavigationItem(
            title = stringResource(R.string.weather),
            selectedIcon = R.drawable.weather_yellow,
            unselectedIcon = R.drawable.weather_gray,
            screenTitle = stringResource(R.string.Tashkent)
        ), NavigationItem(
            title = stringResource(R.string.news),
            selectedIcon = R.drawable.news_white,
            unselectedIcon = R.drawable.news_gray,
            screenTitle = stringResource(R.string.news)
        )
    )

    Surface(modifier = Modifier.fillMaxSize()) {

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var selectedItemIndex by remember { mutableIntStateOf(2) }
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.fillMaxHeight(),
                    drawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
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
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                unselectedContainerColor = Color.Transparent,
                                selectedTextColor = Color.White,
                                unselectedTextColor = Color.LightGray,
                                selectedIconColor = Color.White,
                                unselectedIconColor = Color.LightGray
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                    val checked = remember {
                        mutableStateOf(darkTheme.value)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = checked.value,
                        onCheckedChange = {
                            checked.value = it
                            darkTheme.value = !darkTheme.value
                            setTheme.invoke(darkTheme.value)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                }
            }, drawerState = drawerState
        ) {
            val systemUiController = rememberSystemUiController()
            when (selectedItemIndex) {
                0 -> {
                    StopWatchScreen(
                        startPlayer = { startStopPlayer.invoke() },
                        resetPlayer = { resetPlayer.invoke() },
                        navIconClick = { scope.launch { drawerState.open() } })
                    systemUiController.apply {
                        setStatusBarColor(MaterialTheme.colorScheme.primaryContainer)
                        setNavigationBarColor(MaterialTheme.colorScheme.primaryContainer)
                    }
                }

                1 -> {
                    WeatherScreen(navIconClick = { scope.launch { drawerState.open() } })
                    systemUiController.apply {
                        setStatusBarColor(MaterialTheme.colorScheme.primaryContainer)
                        setNavigationBarColor(MaterialTheme.colorScheme.primaryContainer)
                    }
                }

                2 -> {
                    NewsMain(
                        navIconClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                        actions = { action ->
                            actions.invoke(action)
                        }
                    )
                    systemUiController.apply {
                        setStatusBarColor(MaterialTheme.colorScheme.primary)
                        setNavigationBarColor(MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}


data class NavigationItem(
    val title: String, @DrawableRes val selectedIcon: Int, @DrawableRes val unselectedIcon: Int, val screenTitle: String
)