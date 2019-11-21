package sourceset564.samples.roomrx.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Anwar on 11/20/2019.
 */
@Entity(tableName = "food_cart")
data class CartEntity(
    @ColumnInfo(name = "food_name")
    var name : String,
    @ColumnInfo(name = "qty")
    var qty : Int,
    @ColumnInfo(name = "price")
    var price : Int)

{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0

    fun getTotalPrice() = price * qty
}