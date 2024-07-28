package com.example.nalassetmanagement.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nalassetmanagement.base.getAssetFakeData
import com.example.nalassetmanagement.base.getListInventorySessionFakeData
import com.example.nalassetmanagement.extension.mapEntityToData
import com.example.nalassetmanagement.room.dao.AssetDao
import com.example.nalassetmanagement.room.dao.AssetInventorySessionDao
import com.example.nalassetmanagement.room.dao.InventorySessionDao
import com.example.nalassetmanagement.room.entity.AssetEntity
import com.example.nalassetmanagement.room.entity.AssetInventorySessionEntity
import com.example.nalassetmanagement.room.entity.InventorySessionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [AssetEntity::class, InventorySessionEntity::class, AssetInventorySessionEntity::class],
    version = 3,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val databaseName = "APP"
        fun getInstance(applicationContext: Context): AppDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(applicationContext, AppDatabase::class.java, databaseName)
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance!!
        }
    }
    abstract fun assetDao(): AssetDao
    abstract fun inventorySessionDao(): InventorySessionDao
    abstract fun assetInventorySessionDao(): AssetInventorySessionDao

}
fun importDataFakeToDatabase(context: Context) {
    val database = AppDatabase.getInstance(context)
    CoroutineScope(Dispatchers.IO).launch {
        val assetCount = database.assetDao().getAll().size

        if (assetCount == 0 ) {
            database.assetDao().insert(
                *getAssetFakeData()
                    .mapEntityToData {
                        it.toAssetEntity()
                    }
                    .toTypedArray()
            )
            database.inventorySessionDao().insert(
                *getListInventorySessionFakeData()
                    .mapEntityToData { it.toInventorySessionEntity() }
                    .toTypedArray()
            )
        }
    }
}

fun importDataToDatabase(
    context: Context,
    listAsset: List<AssetEntity>,
) {
    val database = AppDatabase.getInstance(context)
    CoroutineScope(Dispatchers.IO).launch {
        val assetCount = database.assetDao().getAll().size

        if (assetCount == 0) {
            database.assetDao().insert(
                *listAsset.toTypedArray()
            )
            database.inventorySessionDao().insert(
                *getListInventorySessionFakeData()
                    .mapEntityToData { it.toInventorySessionEntity() }
                    .toTypedArray()
            )
        }
    }
}