package com.artemissoftware.dotaworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import com.artemissoftware.core.DataState
import com.artemissoftware.core.Logger
import com.artemissoftware.core.ProgressBarState
import com.artemissoftware.core.UIComponent
import com.artemissoftware.dotaworld.ui.theme.DotaWorldTheme
import com.artemissoftware.hero_domain.Hero
import com.artemissoftware.hero_interactors.HeroInteractors
import com.artemissoftware.ui_herolist.HeroList
import com.artemissoftware.ui_herolist.HeroListState
import com.squareup.sqldelight.android.AndroidSqliteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.Dispatchers.IO

class MainActivity : ComponentActivity() {



    private val state: MutableState<HeroListState> = mutableStateOf(HeroListState())
    private val progressBarState: MutableState<ProgressBarState> = mutableStateOf(ProgressBarState.Idle)
    private lateinit var imageLoader: ImageLoader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        imageLoader = ImageLoader.Builder(applicationContext)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()


        val getHeros = HeroInteractors.build(
            sqlDriver = AndroidSqliteDriver(
                schema = HeroInteractors.schema,
                context = this,
                name = HeroInteractors.name
            )
        ).getHeros
        val logger = Logger("GetHerosTest")


        getHeros.execute().onEach { dataState ->

            when(dataState){

                is DataState.Response -> {

                    when(dataState.uiComponent){
                        is UIComponent.Dialog -> {
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }

                is DataState.Data -> {
                    state.value = state.value.copy(heros = dataState.data?: listOf())
                }

                is DataState.Loading -> {
                    progressBarState.value = dataState.progressBarState
                }

            }
        }.launchIn(CoroutineScope(IO))



        setContent {
            DotaWorldTheme {
                HeroList(state = state.value, imageLoader = imageLoader)
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