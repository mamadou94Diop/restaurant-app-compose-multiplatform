package features.menu.data.source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    val id: Int,
    val img: String,
    val name: String
)