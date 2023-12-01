package features.favorite.data.source.local.entity

import features.menu.data.source.local.entity.IngredientEntity
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class FavoriteBurgerEntity : RealmObject {
    var id: Int = 0
    var description: String = ""
    var image: String = ""
    var ingredients: RealmList<IngredientEntity> = realmListOf()
    var name: String = ""
    var price: Double = 0.0
}