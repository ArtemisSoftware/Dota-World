package com.artemissoftware.ui_herolist

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import com.artemissoftware.core.domain.ProgressBarState
import com.artemissoftware.core.domain.UIComponentState
import com.artemissoftware.ui_herolist.components.HeroListFilter
import com.artemissoftware.ui_herolist.components.HeroListToolbar
import com.artemissoftware.ui_herolist.ui.HeroListEvents
import com.artemissoftware.ui_herolist.ui.HeroListItem
import com.artemissoftware.ui_herolist.ui.HeroListState

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun HeroList(
    state: HeroListState,
    events: (HeroListEvents) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit,
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Column {


            HeroListToolbar(
                heroName = state.heroName,
                onHeroNameChanged = { heroName ->
                    events(HeroListEvents.UpdateHeroName(heroName))
                },
                onExecuteSearch = {
                    events(HeroListEvents.FilterHeros)
                },
                onShowFilterDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Show))
                }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ){
                items(state.filteredHeros){ hero ->
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

        if(state.filterDialogState is UIComponentState.Show){
            HeroListFilter(
                heroFilter = state.heroFilter,
                onUpdateHeroFilter = { heroFilter ->
                    events(HeroListEvents.UpdateHeroFilter(heroFilter))
                },
                attributeFilter = state.primaryAttrFilter,
                onUpdateAttributeFilter = { attribute ->
                    events(HeroListEvents.UpdateAttributeFilter(attribute))
                },
                onCloseDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Hide))
                }
            )
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
