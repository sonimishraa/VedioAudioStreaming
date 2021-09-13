package com.alok.basicapps.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iotric.vedioaudiostreamingapp.database.dao.EncryptedDao
import com.iotric.vedioaudiostreamingapp.database.table.Encrypted

@Database(
    entities = [
        Encrypted::class
    ],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun encryptedDao(): EncryptedDao
}
