package com.alok.basicapps.data

import com.iotric.vedioaudiostreamingapp.database.dao.EncryptedDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GardenPlantingRepository @Inject constructor(
    private val gardenPlantingDao: EncryptedDao
) {

}
