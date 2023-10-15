package features.menu.data.source.local.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class BurgerEntity : RealmObject {
    var id: Int = 0
    var description: String = ""
    var image: String = ""
    var ingredients: RealmList<IngredientEntity> = realmListOf()
    var name: String = ""
    var price: Double = 0.0
    var isFavorite: Boolean = false
}