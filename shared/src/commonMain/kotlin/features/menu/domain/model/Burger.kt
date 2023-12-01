package features.menu.domain.model

data class Burger(
    val description: String,
    val id: Int,
    val image: String,
    val ingredients: List<Ingredient>,
    val name: String,
    val price: Double,
)