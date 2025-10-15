package org.plantalpha.maimaidata.feature.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.plantalpha.maimaidata.R
import org.plantalpha.maimaidata.feature.song.SongListPage
import org.plantalpha.maimaidata.feature.songdetails.SongDetailsPage

@Serializable
sealed class Route(@StringRes val desc: Int) {
    @Serializable
    object SongList : Route(R.string.home)

    @Serializable
    class SongDetail(val index: Int) : Route(R.string.song_detail)

    @Serializable
    object Rating : Route(R.string.rating)

}

@Composable
fun MainGraph() {
    val navController = rememberNavController()
    val currentDestination by navController.currentBackStackEntryAsState()
    val bottomItem = remember {
        listOf(
            Triple(Route.SongList.desc, Route.SongList, Icons.Default.Menu),
            Triple(Route.Rating.desc, Route.Rating, Icons.Default.Star)
        )
    }
    Column {
        NavHost(
            navController = navController,
            startDestination = Route.SongList,
            modifier = Modifier.weight(9f)
        ) {
            composable<Route.SongList> {
                SongListPage {
                    navController.navigate(Route.SongDetail(it))
                }
            }
            composable<Route.SongDetail> { navBackStackEntry ->
                //TODO song
                val index = navBackStackEntry.toRoute<Route.SongDetail>().index
                SongDetailsPage(onBack = navController::popBackStack)
            }

            composable<Route.Rating> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Rating")
                }
            }

        }
        NavigationBar {
            bottomItem.fastForEach { (desc, route, icon) ->
                val selected = currentDestination?.destination?.hasRoute(route::class) == true
                NavigationBarItem(
                    selected = selected,
                    label = { Text(stringResource(desc)) },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(desc)
                        )
                    },
                    onClick = {
                        if (selected) return@NavigationBarItem
                        navController.navigate(route) {
                            popUpTo(Route.SongList::class) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}