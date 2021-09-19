package com.artemissoftware.hero_interactors

import com.artemissoftware.hero_datasource.cache.HeroCache
import com.artemissoftware.hero_datasource.network.HeroService
import com.squareup.sqldelight.db.SqlDriver

data class HeroInteractors(
    val getHeros: GetHeros,
    val getHeroFromCache: GetHeroFromCache
) {
    companion object Factory {
        fun build(sqlDriver: SqlDriver): HeroInteractors{

            val service = HeroService.build()
            val cache = HeroCache.build(sqlDriver)

            return HeroInteractors(
                getHeros = GetHeros(
                    cache = cache,
                    service = service,
                ),

                getHeroFromCache = GetHeroFromCache(
                    cache = cache
                )
            )
        }

        val schema: SqlDriver.Schema = HeroCache.schema

        val dbName: String = HeroCache.dbName
    }
}