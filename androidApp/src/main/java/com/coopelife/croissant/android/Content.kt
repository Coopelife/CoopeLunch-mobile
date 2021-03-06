package com.coopelife.croissant.android

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coopelife.croissant.android.ui.screen.home.HomeScreen
import com.coopelife.croissant.android.ui.screen.home.HomeScreenViewModel
import com.coopelife.croissant.android.ui.screen.mypage.MypageScreen
import com.coopelife.croissant.android.ui.util.theme.CroissantTheme
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
internal fun Content() {
    val navController = rememberNavController()
    val screenItems = listOf(
        Screen.Home,
        Screen.Mypage,
    )
    CroissantTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "追加"
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            bottomBar = {
                BottomAppBar(
                    cutoutShape = MaterialTheme.shapes.small.copy(
                        CornerSize(percent = 50)
                    )
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    screenItems.forEach { screen ->
                        val route: String = stringResource(screen.routeStrResId)
                        val title: String = stringResource(screen.titleStrResId)
                        val isSelected: Boolean = currentRoute == route
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = title
                                )
                            },
                            label = { Text(title) },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(route) {
                                    navController.graph.startDestinationRoute?.let {
                                        popUpTo(it) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = stringResource(R.string.home_route),
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.padding_bottom_navigation_height))
            ) {
                // TODO: ハードコーディングの解消
                composable("home") {
                    HomeScreen(
                        nacController = navController,
                        viewModel = HomeScreenViewModel()
                    )
                }
                composable("mypage") {
                    MypageScreen(navController = navController)
                }
            }
        }
    }
}

internal sealed class Screen(
    @StringRes val routeStrResId: Int,
    @StringRes val titleStrResId: Int,
    val icon: ImageVector
) {
    internal object Home : Screen(
        routeStrResId = R.string.home_route,
        titleStrResId = R.string.home_title,
        icon = Icons.Filled.Home
    )

    internal object Mypage : Screen(
        routeStrResId = R.string.mypage_route,
        titleStrResId = R.string.mypage_title,
        icon = Icons.Filled.Face
    )
}
