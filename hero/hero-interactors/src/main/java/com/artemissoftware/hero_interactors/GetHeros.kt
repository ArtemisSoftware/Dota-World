package com.artemissoftware.hero_interactors

import com.artemissoftware.core.DataState
import com.artemissoftware.core.ProgressBarState
import com.artemissoftware.core.UIComponent
import com.artemissoftware.hero_datasource.network.HeroService
import com.artemissoftware.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeros(
    // TODO(Add caching)
    private val service: HeroService,
) {

    fun execute(): Flow<DataState<List<Hero>>> = flow {

        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val heros: List<Hero> = try { // catch network exceptions

                service.getHeroStats()

            }
            catch (e: Exception){
                e.printStackTrace() // log to crashlytics?

                emit(DataState.Response<List<Hero>>(uiComponent = UIComponent.Dialog(title = "Network Data Error", description = e.message?: "Unknown error")))
                listOf()
            }

            emit(DataState.Data(heros))


        }catch (e: Exception){
            e.printStackTrace()

            emit(DataState.Response<List<Hero>>(
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