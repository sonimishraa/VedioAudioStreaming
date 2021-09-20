package com.tamasha.vedioaudiostreamingapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tamasha.vedioaudiostreamingapp.database.table.Encrypted

@Dao
interface EncryptedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Encrypted)

    @Query("DELETE FROM encrypted WHERE `index` = :keyIndex")
    suspend fun delete(keyIndex: String)

    @Query("DELETE FROM encrypted where `category` = :category")
    suspend fun clearAllCategory(category: String)

    @Query("DELETE FROM encrypted ")
    suspend fun clearAll()

    @Query("SELECT * FROM encrypted WHERE `index` = :keyIndex")
    suspend fun getData(keyIndex: String): Encrypted?
}
