package com.example.numbertesttask2.numbers.data.cache
import androidx.room.*

@Dao
interface NumbersDao {

    @Query("SELECT * FROM numbers_table ORDER BY date ASC")
    fun allNumbers(): List<NumberCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(number: NumberCache)

    @Update
    fun update(number: NumberCache)

    @Query("SELECT * FROM numbers_table WHERE number = :number")
    fun number(number: String): NumberCache?


}