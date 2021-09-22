package com.artemissoftware.ui_herolist.ui

import com.artemissoftware.core.domain.ProgressBarState
import com.artemissoftware.core.domain.UIComponentState
import com.artemissoftware.hero_domain.Hero
import com.artemissoftware.hero_domain.HeroAttribute
import com.artemissoftware.hero_domain.HeroFilter

data class HeroListState(

    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heros: List<Hero> = listOf(),
    val filteredHeros: List<Hero> = listOf(),
    val heroName: String = "",

    val heroFilter: HeroFilter = HeroFilter.Hero(),
    val primaryAttrFilter: HeroAttribute = HeroAttribute.Unknown,

    val filterDialogState: UIComponentState = UIComponentState.Hide, // show/hide the filter dialog
)
