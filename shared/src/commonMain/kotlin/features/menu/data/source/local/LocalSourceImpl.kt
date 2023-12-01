package features.menu.data.source.local

import features.menu.data.source.local.entity.BurgerEntity
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalSourceImpl(private val realm: Realm) : LocalSource {
    override fun getBurgers(): Flow<List<BurgerEntity>> {
        return realm.query(BurgerEntity::class).asFlow().map {
            it.list.toList()
        }
    }

    override fun insertBurgers(pizzas: List<BurgerEntity>) {
        pizzas.forEach {
            realm.writeBlocking {
                copyToRealm(it)
            }
        }
    }

    override fun deleteAllBurgers() {
        realm.writeBlocking {
            val query = this.query<BurgerEntity>(BurgerEntity::class)
            delete(query)
        }
    }

}