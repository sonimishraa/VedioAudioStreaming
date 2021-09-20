package com.tamasha.vedioaudiostreamingapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tamasha.vedioaudiostreamingapp.database.table.Encrypted
import com.tamasha.vedioaudiostreamingapp.database.dao.EncryptedDao

@Database(
    entities = [
        Encrypted::class
    ],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun encryptedDao(): EncryptedDao
}
