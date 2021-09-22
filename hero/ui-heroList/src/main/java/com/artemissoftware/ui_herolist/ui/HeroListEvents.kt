package com.artemissoftware.ui_herolist.ui

import com.artemissoftware.core.domain.UIComponentState
import com.artemissoftware.hero_domain.HeroFilter

sealed class HeroListEvents {

    object GetHeros : HeroListEvents()

    object FilterHeros: HeroListEvents()

    data class UpdateHeroName(
        val heroName: String,
    ): HeroListEvents()

    data class UpdateHeroFilter(
        val heroFilter: HeroFilter
    ): HeroListEvents()


    data class UpdateFilterDialogState(
        val uiComponentState: UIComponentState
    ): HeroListEvents()
}
