package com.qnecesitas.elreteninventario.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qnecesitas.elreteninventario.data.ModelDrawerS
import com.qnecesitas.elreteninventario.data.ModelDrawerLS
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.data.ModelEditProductLS
import com.qnecesitas.elreteninventario.data.ModelPassword
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.data.ModelSessionLS
import com.qnecesitas.elreteninventario.data.ModelSessionS
import com.qnecesitas.elreteninventario.data.ModelShelfS
import com.qnecesitas.elreteninventario.data.ModelShelfLS

@Database(entities = [
    ModelShelfS::class,
    ModelShelfLS::class,
    ModelDrawerS::class,
    ModelDrawerLS::class,
    ModelSessionLS::class,
    ModelSessionS::class,
    ModelPassword::class,
    ModelEditProductS::class,
    ModelEditProductLS::class,
    ModelSale::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun drawerLSDao(): DrawerLSDao
    abstract fun shelfLSDao(): ShelfLSDao
    abstract fun drawerSDao(): DrawerSDao
    abstract fun shelfSDao(): ShelfSDao
    abstract fun ordersDao(): OrdersDao
    abstract fun sessionSDao(): SessionSDao
    abstract fun sessionLSDao(): SessionLSDao
    abstract fun productSDao(): ProductSDao
    abstract fun productLSDao(): ProductLSDao
    abstract fun accountDao(): AccountDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val callback = RoomDatabaseImport()

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                        .addCallback(callback)
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }

}