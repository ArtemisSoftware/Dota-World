package com.artemissoftware.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.DataState
import com.artemissoftware.core.domain.Queue
import com.artemissoftware.core.domain.UIComponent
import com.artemissoftware.core.util.Logger
import com.artemissoftware.hero_domain.HeroAttribute
import com.artemissoftware.hero_domain.HeroFilter
import com.artemissoftware.hero_interactors.FilterHeros
import com.artemissoftware.hero_interactors.GetHeros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val getHeros: GetHeros,
    private val filterHeros: FilterHeros,
    private val logger: Logger
): ViewModel(){

    val state: MutableState<HeroListState> = mutableStateOf(HeroListState())

    init {
        onTriggerEvent(HeroListEvents.GetHeros)
    }

    fun onTriggerEvent(event: HeroListEvents){
        when(event){

            is HeroListEvents.GetHeros -> {
                getHeros()
            }

            is HeroListEvents.FilterHeros -> {
                filterHeros()
            }

            is HeroListEvents.UpdateHeroFilter -> {
                updateHeroFilter(event.heroFilter)
            }

            is HeroListEvents.UpdateHeroName -> {
                updateHeroName(event.heroName)
            }

            is HeroListEvents.UpdateFilterDialogState -> {
                state.value = state.value.copy(filterDialogState = event.uiComponentState)
            }

            is HeroListEvents.UpdateAttributeFilter -> {
                updateAttributeFilter(event.attribute)
            }
        }
    }


    private fun updateAttributeFilter(attribute: HeroAttribute) {
        state.value = state.value.copy(primaryAttrFilter = attribute)
        filterHeros()
    }

    private fun updateHeroFilter(heroFilter: HeroFilter){
        state.value = state.value.copy(heroFilter = heroFilter)
        filterHeros()
    }

    private fun updateHeroName(heroName: String) {
        state.value = state.value.copy(heroName = heroName)
    }


    private fun filterHeros(){

        val filteredList = filterHeros.execute(
            current = state.value.heros,
            heroName = state.value.heroName,
            heroFilter = state.value.heroFilter,
            attributeFilter = state.value.primaryAttrFilter,
        )
        state.value = state.value.copy(filteredHeros = filteredList)
    }

    private fun getHeros(){

        getHeros.execute().onEach { dataState ->
            when(dataState){

                is DataState.Response -> {
                    when(dataState.uiComponent){
                        is UIComponent.Dialog -> {
                            appendToMessageQueue(dataState.uiComponent)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }

                is DataState.Data -> {
                    state.value = state.value.copy(heros = dataState.data?: listOf())
                    filterHeros()
                }

                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent){
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
        state.value = state.value.copy(errorQueue = queue)
    }


}
