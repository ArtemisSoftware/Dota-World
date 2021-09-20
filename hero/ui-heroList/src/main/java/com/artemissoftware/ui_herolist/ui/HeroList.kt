package com.artemissoftware.ui_herolist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import com.artemissoftware.core.ProgressBarState
import com.artemissoftware.ui_herolist.components.HeroListToolbar
import com.artemissoftware.ui_herolist.ui.HeroListItem
import com.artemissoftware.ui_herolist.ui.HeroListState

@ExperimentalComposeUiApi
@Composable
fun HeroList(
    state: HeroListState,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit,
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Column {


            val name = remember { mutableStateOf("") }

            HeroListToolbar(
                heroName = name.value,
                onHeroNameChanged = { heroName ->
                    name.value = heroName
                },
                onExecuteSearch = {

                },
                onShowFilterDialog = {

                }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ){
                items(state.heros){ hero ->
                    HeroListItem(
                        hero = hero,
                        onSelectHero = { heroId ->
                            navigateToDetailScreen(heroId)
                        },
                        imageLoader = imageLoader
                    )
                }
            }

        }



        if(state.progressBarState is ProgressBarState.Loading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

        //HeroList(HeroListState())

}
