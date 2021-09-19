package com.artemissoftware.hero_interactors
import com.artemissoftware.core.DataState
import com.artemissoftware.core.ProgressBarState
import com.artemissoftware.core.UIComponent
import com.artemissoftware.hero_datasource.cache.HeroCache
import com.artemissoftware.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
/**
 * Retrieve a hero from the cache using the hero's unique id
 */
class GetHeroFromCache(
    private val cache: HeroCache
) {

    fun execute(id: Int): Flow<DataState<Hero>> = flow {

        try {

            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            // emit data from network
            val cachedHero = cache.getHero(id)

            if(cachedHero == null){
                throw Exception("That hero does not exist in the cache.")
            }

            emit(DataState.Data(cachedHero))

        }catch (e: Exception){

            e.printStackTrace()

            emit(DataState.Response<Hero>(
                uiComponent = UIComponent.Dialog(
                    title = "Error",
                    description = e.message?: "Unknown error"
                )
            ))
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }

    }
}