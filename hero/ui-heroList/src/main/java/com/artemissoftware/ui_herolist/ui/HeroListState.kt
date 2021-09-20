package com.artemissoftware.ui_herolist.ui

import com.artemissoftware.core.ProgressBarState
import com.artemissoftware.hero_domain.Hero

data class HeroListState(

    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heros: List<Hero> = listOf(),
    val filteredHeros: List<Hero> = listOf(),
    val heroName: String = ""
)
