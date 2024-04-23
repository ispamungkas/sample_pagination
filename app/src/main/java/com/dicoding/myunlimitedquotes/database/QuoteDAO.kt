package com.dicoding.myunlimitedquotes.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.myunlimitedquotes.network.QuoteResponseItem

@Dao
interface QuoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quoteResponseItem: List<QuoteResponseItem>)

    @Query("SELECT * From quote")
    fun getAllQuote(): PagingSource<Int, QuoteResponseItem>

    @Query("DELETE FROM quote")
    suspend fun deleteQuote()

}