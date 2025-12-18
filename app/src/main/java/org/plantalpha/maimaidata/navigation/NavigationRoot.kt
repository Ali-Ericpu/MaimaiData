package org.plantalpha.maimaidata.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.plantalpha.maimaidata.feature.song.SongListPage
import org.plantalpha.maimaidata.feature.songdetails.SongDetailsPage

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val bottomItem = remember {
        listOf(
            Triple(Route.Home.desc, Route.Home, Icons.Default.Menu),
            Triple(Route.Rating.desc, Route.Rating, Icons.Default.Star)
        )
    }
    val rootBackStack = rememberNavBackStack(Route.Home)
    var currentTab: Route by remember { mutableStateOf(Route.Home) }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        NavigationRoute(rootBackStack)
        val key = rootBackStack.lastOrNull()
        AnimatedVisibility(key is Route.Home || key is Route.Rating) {
            NavigationBar {
                bottomItem.fastForEach { (desc, route, icon) ->
                    NavigationBarItem(
                        selected = currentTab == route,
                        label = { Text(stringResource(desc)) },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = stringResource(desc)
                            )
                        },
                        onClick = {
                            if (currentTab == route) return@NavigationBarItem
                            currentTab = route
                            when (route) {
                                Route.Rating -> rootBackStack.add(Route.Rating)
                                Route.Home -> rootBackStack.removeLastOrNull()
                                else -> error("unreachable")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationRoute(navBackStack: NavBackStack<in NavKey>, modifier: Modifier = Modifier) {
    NavDisplay(
        modifier = modifier,
        backStack = navBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Route.Home> {
                SongListPage {
                    navBackStack.add(Route.SongDetail(it))
                }
            }
            entry<Route.SongDetail> {
                SongDetailsPage(it.song) {
                    navBackStack.removeLastOrNull()
                }
            }
            entry<Route.Rating> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Rating")
                }
            }
        }
    )
}