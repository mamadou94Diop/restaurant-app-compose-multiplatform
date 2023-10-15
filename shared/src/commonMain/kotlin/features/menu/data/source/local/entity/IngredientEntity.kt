package features.menu.data.source.local.entity

import io.realm.kotlin.types.RealmObject

class IngredientEntity : RealmObject {
    var id: Int = 0
    var image: String = ""
    var name: String = ""
}