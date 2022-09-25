package com.example.simbirsoftpracticeapp.data.database.categories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    fun getAll(): Single<List<CategoryEntity>>

    @Update
    fun updateAll(category: List<CategoryEntity>) : Completable

    @Insert
    fun insertAll(categories: List<CategoryEntity>) : Completable
}