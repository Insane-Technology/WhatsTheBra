package com.insane.whatsthebra.database.dao

import androidx.room.*
import com.insane.whatsthebra.database.dto.BRA_TYPE_TABLE_NAME
import com.insane.whatsthebra.database.dto.BraTypeDTO

@Dao
interface BraTypeDAO {

    @Query("SELECT * FROM $BRA_TYPE_TABLE_NAME ORDER BY id ASC")
    fun getAll(): List<BraTypeDTO>

    @Query("SELECT * FROM $BRA_TYPE_TABLE_NAME WHERE id = :braTypeId")
    fun getById(braTypeId: Int): BraTypeDTO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(braType: BraTypeDTO)

    @Query("DELETE FROM $BRA_TYPE_TABLE_NAME WHERE id = :braTypeId")
    fun removeBraTypeById(braTypeId: Int)

}