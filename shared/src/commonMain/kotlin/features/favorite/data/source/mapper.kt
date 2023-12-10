import features.favorite.data.source.local.entity.FavoriteBurgerEntity
import features.menu.data.source.toDomain
import features.menu.domain.model.Burger

fun FavoriteBurgerEntity.toBurger() = Burger(
    description = description,
    id = id,
    image = image,
    ingredients = ingredients.map { it.toDomain() },
    name = name,
    price = price
)