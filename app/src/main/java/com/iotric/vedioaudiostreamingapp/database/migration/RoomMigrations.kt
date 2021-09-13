package com.iotric.vedioaudiostreamingapp.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_1 = object : Migration(2, 1) {
    override fun migrate(database: SupportSQLiteDatabase) {

        /*
        With this sql just desired fields add to existing table without any loosing data
         */
        database.execSQL("ALTER TABLE encrypted ADD COLUMN migrationTest STRING")
    }
}

/*
 * If we want to crate tables from beginning for example
 *  for down-grading version of db from 2 to 1 without any crash.
 */
val MIGRATION_3_2 = object : Migration(3, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        dropSingleTable(database, "encrypt")
    }
}

private fun dropSingleTable(database: SupportSQLiteDatabase, tableName: String) {

    database.execSQL(" DROP TABLE IF EXISTS $tableName")
}
