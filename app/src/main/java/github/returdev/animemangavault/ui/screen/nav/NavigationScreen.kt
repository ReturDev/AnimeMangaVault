package github.returdev.animemangavault.ui.screen.nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.core.navigation.Destination
import github.returdev.animemangavault.ui.core.navigation.NavigationGraph
import github.returdev.animemangavault.ui.core.snackbar.rememberSnackBarController
import github.returdev.animemangavault.ui.theme.AnimeMangaVaultTheme
import kotlinx.coroutines.delay


@Composable
fun NavigationScreen(
    navController : NavHostController,
    viewModel: NavigationViewModel = hiltViewModel()
){

    val bottomRoutes = listOf(
        Destination.NoArgumentsDestination.HomeScreenDestination(),
        Destination.NoArgumentsDestination.LibraryScreenDestination()
    )
    val navBackEntryState by navController.currentBackStackEntryAsState()
    var showBottomBar by remember { mutableStateOf(true) }
    var bottomBarItemSelected by remember { mutableStateOf(0)  }
    var isVisibleConnectionView by remember { mutableStateOf(false) }

    val snackbarController = rememberSnackBarController(
        snackbarHostState = remember { SnackbarHostState() }
    )


    val uiState by viewModel.uiState.collectAsState()

    Column(Modifier.fillMaxSize()) {

        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = slideInVertically(
                        animationSpec = tween(200),
                        initialOffsetY = {it}
                    ),
                    exit = slideOutVertically(
                        animationSpec = tween(200),
                        targetOffsetY = {it}
                    )
                ) {
                    BottomNavigationBar(bottomBarItemSelected) { selection ->
                        navController.navigate(bottomRoutes[selection]){
                            launchSingleTop = true
                            this.popUpTo(bottomRoutes[selection])
                        }
                    }
                }
            },
            snackbarHost = {
                SnackbarHost(snackbarController.snackbarHostState) {
                    snackbarController.Snackbar(it)
                }
            }
        ){ paddingValues ->

            NavigationGraph(
                navController = navController,
                snackBarController = snackbarController,
                modifier = Modifier.padding(paddingValues)
            )

        }

        LaunchedEffect(key1 = uiState){
            isVisibleConnectionView = if (uiState.connectionLost){
                delay(500)
                true
            }else{
                delay(1000)
                false
            }
        }


        LaunchedEffect(key1 = navBackEntryState){
            showBottomBar = navBackEntryState?.destination?.route in bottomRoutes
            bottomBarItemSelected = bottomRoutes.indexOf(navBackEntryState?.destination?.route)
        }

        AnimatedVisibility(
            visible = isVisibleConnectionView,
            enter = slideInVertically(tween(200)){it},
            exit = slideOutVertically(tween(200)){it},
        ) {
            ConnectionAlert(state = uiState)
        }

    }

}

@Composable
fun BottomNavigationBar(routeSelected: Int, onSelectItem: (Int) -> Unit){
    NavigationBar {

        NavigationBarItem(
            selected = routeSelected == 0,
            onClick = { onSelectItem(0) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null
                    )
            },
            label = { Text(text = stringResource(R.string.menu_bar_home)) }
        )
        NavigationBarItem(
            selected = routeSelected == 1,
            onClick = { onSelectItem(1) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_library),
                    contentDescription = null
                )
            },
            label = { Text(text = stringResource(R.string.menu_bar_library)) }
        )
    }
}

@Composable
private fun ConnectionAlert(state : NavigationUiState){

    val iconRes : Int
    val stringRes : Int
    val backgroundColor : Color
    val contentColor : Color

    if (state.connectionLost){
        iconRes = R.drawable.ic_connection_lost
        stringRes = R.string.network_connection_lost
        backgroundColor = MaterialTheme.colorScheme.errorContainer
        contentColor = MaterialTheme.colorScheme.onErrorContainer

    }else{
        iconRes = R.drawable.ic_connection_back
        stringRes = R.string.network_connection_back
        backgroundColor = MaterialTheme.colorScheme.primary
        contentColor = MaterialTheme.colorScheme.onPrimary
    }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = contentColor
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = stringRes),
                color = contentColor
            )
        }

}


@Preview
@Composable
fun PreviewNavigationScreen(){
    AnimeMangaVaultTheme {
        NavigationScreen(NavHostController(LocalContext.current))
    }
}