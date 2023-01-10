package furkan.compose_viewpager

import androidx.annotation.DrawableRes

data class Countries(
    val code : String,
    @DrawableRes val flag: Int,
    val capital: String,
    val official_language: String
)