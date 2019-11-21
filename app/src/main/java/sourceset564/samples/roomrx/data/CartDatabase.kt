package sourceset564.samples.roomrx.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Anwar on 11/20/2019.
 */

@Database(entities = [CartEntity::class],version = 1,exportSchema = false)
abstract class CartDatabase : RoomDatabase() {

    abstract fun foodCartDao() : FoodCartDao

    companion object{
        private var INSTANCE : CartDatabase? = null
        const val DB_NAME = "carts"

        @JvmStatic
        fun getInstance(context : Context) : CartDatabase {
            if(INSTANCE == null){
                synchronized(CartDatabase::class){
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context,CartDatabase::class.java, DB_NAME)
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}