package features.menu.data.source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BurgerDto(
    val desc: String,
    val id: Int,
    val images: List<ImageDto>,
    val ingredients: List<IngredientDto>,
    val name: String,
    val price: Double)