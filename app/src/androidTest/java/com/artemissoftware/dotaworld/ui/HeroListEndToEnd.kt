package com.artemissoftware.dotaworld.ui

import com.artemissoftware.dotaworld.di.HeroInteractorsModule
import com.artemissoftware.hero_datasource.cache.HeroCache
import com.artemissoftware.hero_datasource.network.HeroService
import com.artemissoftware.hero_datasource_test.cache.HeroCacheFake
import com.artemissoftware.hero_datasource_test.cache.HeroDatabaseFake
import com.artemissoftware.hero_datasource_test.network.HeroServiceFake
import com.artemissoftware.hero_datasource_test.network.HeroServiceResponseType
import com.artemissoftware.hero_interactors.FilterHeros
import com.artemissoftware.hero_interactors.GetHeroFromCache
import com.artemissoftware.hero_interactors.GetHeros
import com.artemissoftware.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@UninstallModules(HeroInteractorsModule::class)
@HiltAndroidTest
class HeroListEndToEnd {

    @Module
    @InstallIn(SingletonComponent::class)
    object TestHeroInteractorsModule {

        @Provides
        @Singleton
        fun provideHeroCache(): HeroCache {
            return HeroCacheFake(HeroDatabaseFake())
        }

        @Provides
        @Singleton
        fun provideHeroService(): HeroService {
            return HeroServiceFake.build(
                type = HeroServiceResponseType.GoodData
            )
        }

        @Provides
        @Singleton
        fun provideHeroInteractors(
            cache: HeroCache,
            service: HeroService
        ): HeroInteractors {
            return HeroInteractors(
                getHeros = GetHeros(
                    cache = cache,
                    service = service,
                ),
                filterHeros = FilterHeros(),
                getHeroFromCache = GetHeroFromCache(
                    cache = cache,
                ),
            )
        }
    }
}