package sourceset564.samples.roomrx.data

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by Anwar on 11/20/2019.
 */
@Dao
interface FoodCartDao {

    @Insert()
    fun insert(cart : CartEntity) : Completable

    /**
     * Gunakan method ini apabila ingin insert multiple item
     */
    @Insert()
    fun insert(carts : List<CartEntity>) : Maybe<List<Long>>

    @Query("select * from food_cart")
    fun getAllCart() : Flowable<List<CartEntity>>

    @Update
    fun update(cart : CartEntity) : Maybe<Int>

    @Delete
    fun delete(cart : CartEntity) : Single<Int>

    @Query("delete from food_cart")
    fun deleteAll() : Completable
}