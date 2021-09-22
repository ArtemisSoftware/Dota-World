package com.artemissoftware.ui_herodetail.ui

import com.artemissoftware.core.domain.ProgressBarState
import com.artemissoftware.hero_domain.Hero

data class HeroDetailState (

    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero? = null,
)