package com.artemissoftware.ui_herodetail

import androidx.lifecycle.ViewModel
import com.artemissoftware.hero_interactors.GetHeroFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel
@Inject
constructor(
    private val getHeroFromCache: GetHeroFromCache
): ViewModel(){


}