package com.artemissoftware.ui_herodetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.artemissoftware.components.DefaultScreenUI
import com.artemissoftware.hero_domain.Hero
import com.artemissoftware.hero_domain.maxAttackDmg
import com.artemissoftware.hero_domain.minAttackDmg
import com.artemissoftware.ui_herodetail.ui.HeroDetailState
import kotlin.math.round


@Composable
fun HeroDetail(
    state: HeroDetailState,
    imageLoader: ImageLoader,
) {
    DefaultScreenUI(
        progressBarState = state.progressBarState,
    ) {
        state.hero?.let{ hero ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                item {
                    Column {
                        val painter = rememberImagePainter(
                            hero.img,
                            imageLoader = imageLoader,
                            builder = {
                                placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
                            }
                        )
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 200.dp),
                            painter = painter,
                            contentDescription = hero.localizedName,
                            contentScale = ContentScale.Crop,
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 8.dp),
                                    text = hero.localizedName,
                                    style = MaterialTheme.typography.h1,
                                )
                                val iconPainter = rememberImagePainter(
                                    hero.icon,
                                    imageLoader = imageLoader,
                                    builder = {
                                        placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
                                    }
                                )
                                Image(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(30.dp)
                                        .align(Alignment.CenterVertically),
                                    painter = iconPainter,
                                    contentDescription = hero.localizedName,
                                    contentScale = ContentScale.Crop,
                                )
                            }
                            Text(
                                modifier = Modifier
                                    .padding(bottom = 4.dp),
                                text = hero.primaryAttribute.uiValue,
                                style = MaterialTheme.typography.subtitle1,
                            )
                            Text(
                                modifier = Modifier
                                    .padding(bottom = 12.dp),
                                text = hero.attackType.uiValue,
                                style = MaterialTheme.typography.caption,
                            )
                            HeroBaseStats(
                                hero = hero,
                                padding = 10.dp,
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            WinPercentages(hero = hero,)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeroDescription(
    hero: Hero,
    imageLoader: ImageLoader
    ){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 8.dp),
            text = hero.localizedName,
            style = MaterialTheme.typography.h1,
        )
        val iconPainter = rememberImagePainter(
            hero.icon,
            imageLoader = imageLoader,
            builder = {
                placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
            }
        )
        Image(
            modifier = Modifier
                .height(30.dp)
                .width(30.dp)
                .align(Alignment.CenterVertically),
            painter = iconPainter,
            contentDescription = hero.localizedName,
            contentScale = ContentScale.Crop,
        )
    }
    Text(
        modifier = Modifier
            .padding(bottom = 4.dp),
        text = hero.primaryAttribute.uiValue,
        style = MaterialTheme.typography.subtitle1,
    )
    Text(
        modifier = Modifier
            .padding(bottom = 12.dp),
        text = hero.attackType.uiValue,
        style = MaterialTheme.typography.caption,
    )

}




/**
 * Displays Pro wins % and Turbo wins %
 */
@Composable
fun WinPercentages(
    hero: Hero,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){
        // Pro Win %
        Column(
            modifier = Modifier.fillMaxWidth(.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
                ,
                text = "Pro Wins",
                style = MaterialTheme.typography.h2,
            )
            val proWinPercentage = remember { round(hero.proWins.toDouble() / hero.proPick.toDouble() * 100).toInt()}
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
                ,
                text = "${proWinPercentage} %",
                style = MaterialTheme.typography.h2,
                color = if(proWinPercentage > 50) Color(0xFF009a34) else MaterialTheme.colors.error
            )
        }
        // Turbo win %
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
                ,
                text = "Turbo Wins",
                style = MaterialTheme.typography.h2,
            )
            val turboWinPercentage = remember {round(hero.turboWins.toDouble() / hero.turboPicks.toDouble() * 100).toInt()}
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
                ,
                text = "${turboWinPercentage} %",
                style = MaterialTheme.typography.h2,
                color = if(turboWinPercentage > 50) Color(0xFF009a34) else MaterialTheme.colors.error
            )
        }
    }
}


@Composable
fun HeroBaseStatsItem(
    title: Int,
    base: String,
    gain: String? = null
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "${stringResource(title)}:",
            style = MaterialTheme.typography.body2,
        )
        Row {
            Text(
                text = "${base}",
                style = MaterialTheme.typography.body2,
            )

            gain?.let {
                Text(
                    text = " + ${gain}",
                    style = MaterialTheme.typography.caption,
                )
            }


        }
    }

}


@Composable
fun HeroBaseStats(
    hero: Hero,
    padding: Dp,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
        ) {



            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                text = "Base Stats",
                style = MaterialTheme.typography.h4,
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(end = 20.dp)
                ) {

                    HeroBaseStatsItem(R.string.strength, "${hero.baseStr}", "${hero.strGain}")
                    HeroBaseStatsItem(R.string.agility, "${hero.baseAgi}", "${hero.agiGain}")
                    HeroBaseStatsItem(R.string.intelligence, "${hero.baseInt}", "${hero.intGain}")

                    val health = remember{round(hero.baseHealth + hero.baseStr * 20).toInt()}
                    HeroBaseStatsItem(R.string.health, "${health}")

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                        HeroBaseStatsItem(R.string.attack_range, "${hero.attackRange}")
                        HeroBaseStatsItem(R.string.projectile_speed, "${hero.projectileSpeed}")
                        HeroBaseStatsItem(R.string.move_speed, "${hero.moveSpeed}")

                        val atkMin = remember{hero.minAttackDmg()}
                        val atkMax = remember{hero.maxAttackDmg()}

                        HeroBaseStatsItem(R.string.attack_dmg, "${atkMin} - ${atkMax}")


                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    //HeroBaseStatsItem(R.string.strength, "24", "2.7")
    //HeroBaseStats_(1.dp)

}
