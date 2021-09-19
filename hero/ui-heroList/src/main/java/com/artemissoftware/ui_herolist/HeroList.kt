package com.artemissoftware.ui_herolist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.core.ProgressBarState
import com.artemissoftware.ui_herolist.components.ui.HeroListItem

@Composable
fun HeroList(
    state: HeroListState,
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ){
            items(state.heros){ hero ->
                HeroListItem(hero = hero, onSelectHero = {})
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
