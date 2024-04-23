package com.dicoding.myunlimitedquotes.tuils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.myunlimitedquotes.network.ApiService
import com.dicoding.myunlimitedquotes.network.QuoteResponseItem

class QuotePagingSource(val apiService : ApiService): PagingSource<Int, QuoteResponseItem>() {

    companion object {
        const val initial_page_index = 1
    }

    override fun getRefreshKey(state: PagingState<Int, QuoteResponseItem>): Int? {
        return state.anchorPosition?.let {
            val achorPage = state.closestPageToPosition(it)
            achorPage?.prevKey?.plus(1) ?: achorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteResponseItem> {
        return try {
            val position = params.key ?: initial_page_index
            val responseData = apiService.getQuote(position, params.loadSize)

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == initial_page_index) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}