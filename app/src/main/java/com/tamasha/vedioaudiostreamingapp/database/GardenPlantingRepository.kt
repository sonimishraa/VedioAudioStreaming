package com.tamasha.vedioaudiostreamingapp.database

import com.tamasha.vedioaudiostreamingapp.database.dao.EncryptedDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GardenPlantingRepository @Inject constructor(
    private val gardenPlantingDao: EncryptedDao
) {

}
