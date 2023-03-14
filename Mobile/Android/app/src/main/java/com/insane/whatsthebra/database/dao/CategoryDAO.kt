package com.insane.whatsthebra.database.dao

import androidx.room.*
import com.insane.whatsthebra.database.dto.CATEGORY_TABLE_NAME
import com.insane.whatsthebra.database.dto.CategoryDTO

@Dao
interface CategoryDAO {

    @Query("SELECT * FROM $CATEGORY_TABLE_NAME ORDER BY id ASC")
    fun getAll(): List<CategoryDTO>

    @Query("SELECT * FROM $CATEGORY_TABLE_NAME WHERE id = :categoryId")
    fun getById(categoryId: Int): CategoryDTO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: CategoryDTO)

    @Query("DELETE FROM $CATEGORY_TABLE_NAME WHERE id = :categoryId")
    fun removeCategoryById(categoryId: Int)

}