package uz.context.examenapplication.activities.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CardEntity::class], version = 1)
internal abstract class MyDatabase : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        private var INSTANCE: MyDatabase? = null
        fun getDatabase(context: Context?): MyDatabase? {
            if (INSTANCE == null) {
                synchronized(MyDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context!!,
                        MyDatabase::class.java, "card.db"
                    ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}