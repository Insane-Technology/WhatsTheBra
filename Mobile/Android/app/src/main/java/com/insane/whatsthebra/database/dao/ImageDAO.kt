package com.insane.whatsthebra.database.dao

import androidx.room.*
import com.insane.whatsthebra.database.dto.IMAGE_TABLE_NAME
import com.insane.whatsthebra.database.dto.ImageDTO

@Dao
interface ImageDAO {

    @Query("SELECT * FROM $IMAGE_TABLE_NAME ORDER BY id ASC")
    fun getAll(): List<ImageDTO>

    @Query("SELECT * FROM $IMAGE_TABLE_NAME WHERE id = :imageId")
    fun getById(imageId: Int): ImageDTO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageDTO)

    @Query("DELETE FROM $IMAGE_TABLE_NAME WHERE id = :imageId")
    fun removeImageById(imageId: Int)

}