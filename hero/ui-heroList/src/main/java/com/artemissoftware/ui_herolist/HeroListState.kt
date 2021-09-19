package com.artemissoftware.ui_herolist

import com.artemissoftware.core.ProgressBarState
import com.artemissoftware.hero_domain.Hero

data class HeroListState(

    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heros: List<Hero> = listOf(),

)
