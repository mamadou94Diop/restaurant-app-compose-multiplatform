package features.menu.data.source

import features.menu.data.source.local.entity.BurgerEntity
import features.menu.data.source.local.entity.IngredientEntity
import features.menu.data.source.remote.dto.BurgerDto
import features.menu.data.source.remote.dto.IngredientDto
import features.menu.domain.model.Burger
import features.menu.domain.model.Ingredient
import io.realm.kotlin.ext.toRealmList

fun BurgerDto.toDomain() = Burger(
    id = id,
    description = desc,
    image = images.filterNot { it.lg.isNullOrEmpty() && it.sm.isNullOrEmpty() }.firstOrNull()?.lg ?: images.firstOrNull()?.sm.orEmpty(),
    name = name,
    price = price,
    isFavorite = false,
    ingredients = ingredients.map { it.toDomain() },
)

fun BurgerEntity.toDomain() = Burger(
    id = id,
    description = description,
    image = image,
    name = name,
    price = price,
    isFavorite = isFavorite, ingredients = listOf()
)

fun Burger.toEntity(): BurgerEntity {
    val burger = this
    return BurgerEntity().apply {
        id = burger.id
        description = burger.description
        image = burger.image
        name = burger.name
        isFavorite = burger.isFavorite
        price = burger.price
        ingredients = burger.ingredients.map { it.toEntity() }.toRealmList()
    }
}

fun IngredientDto.toDomain() = Ingredient(id = id, image = img, name = name)

fun Ingredient.toEntity(): IngredientEntity {
    val ingredient = this
    return IngredientEntity().apply {
        id = ingredient.id
        image = ingredient.image
        name = ingredient.name
    }

}


fun Int?.orZero() = this ?: 0
