package com.artemissoftware.ui_herodetail.ui

sealed class HeroDetailEvents{

    data class GetHeroFromCache(
        val id: Int,
    ) : HeroDetailEvents()

}
