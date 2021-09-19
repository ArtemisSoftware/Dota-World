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
import coil.ImageLoader
import com.artemissoftware.core.DataState
import com.artemissoftware.core.Logger
import com.artemissoftware.core.ProgressBarState
import com.artemissoftware.core.UIComponent
import com.artemissoftware.dotaworld.ui.theme.DotaWorldTheme
import com.artemissoftware.hero_interactors.HeroInteractors
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

                val viewModel: HeroListViewModel = hiltViewModel()

                HeroList(state = viewModel.state.value, imageLoader = imageLoader)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DotaWorldTheme {
        Greeting("Android")
    }
}