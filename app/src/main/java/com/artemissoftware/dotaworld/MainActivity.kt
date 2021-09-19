package com.artemissoftware.dotaworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.artemissoftware.core.DataState
import com.artemissoftware.core.Logger
import com.artemissoftware.core.ProgressBarState
import com.artemissoftware.core.UIComponent
import com.artemissoftware.dotaworld.ui.navigation.Screen
import com.artemissoftware.dotaworld.ui.theme.DotaWorldTheme
import com.artemissoftware.hero_interactors.HeroInteractors
import com.artemissoftware.ui_herodetail.HeroDetail
import com.artemissoftware.ui_herodetail.ui.HeroDetailViewModel
import com.artemissoftware.ui_herolist.HeroList
import com.artemissoftware.ui_herolist.ui.HeroListState
import com.artemissoftware.ui_herolist.ui.HeroListViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {




    @Inject
    lateinit var imageLoader: ImageLoader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DotaWorldTheme {


                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.HeroList.route,
                    builder = {
                        addHeroList(
                            navController = navController,
                            imageLoader = imageLoader
                        )
                        addHeroDetail()
                    }
                )

            }
        }
    }
}





fun NavGraphBuilder.addHeroList(
    navController: NavController,
    imageLoader: ImageLoader,
) {
    composable(
        route = Screen.HeroList.route,
    ){
        val viewModel: HeroListViewModel = hiltViewModel()

        HeroList(
            state = viewModel.state.value,
            imageLoader = imageLoader,
            navigateToDetailScreen = { heroId ->
                navController.navigate("${Screen.HeroDetail.route}/$heroId")
            }
        )
    }
}

fun NavGraphBuilder.addHeroDetail() {
    composable(
        route = Screen.HeroDetail.route + "/{heroId}",
        arguments = Screen.HeroDetail.arguments,
    ){

        val viewModel: HeroDetailViewModel = hiltViewModel()

        HeroDetail(state = viewModel.state.value)
    }
}





//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    DotaWorldTheme {
//        Greeting("Android")
//    }
//}