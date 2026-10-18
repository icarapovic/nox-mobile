package dev.nox.data.remote.client

import dev.nox.data.remote.dto.AlgoliaItemDto
import dev.nox.news.domain.exceptions.DataFetchingException
import dev.nox.news.domain.exceptions.NoInternetException
import okhttp3.OkHttpClient
import okio.IOException
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.create

class AlgoliaClient (
    okHttpHttp: OkHttpClient,
    converterFactory: Converter.Factory,
) {

    private val baseUrl = "https://hn.algolia.com/api/v1/"

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpHttp)
        .addConverterFactory(converterFactory)
        .build()
        .create<AlgoliaApi>()

    suspend fun fetchItem(itemId: Long): Result<AlgoliaItemDto> {
        try {
            val item = retrofitClient.fetchItem(itemId)
            return Result.success(item)
        } catch (e: HttpException) {
            return Result.failure(DataFetchingException("HTTP ${e.code()}: ${e.message()}"))
        } catch (e: IOException) {
            return Result.failure(NoInternetException(e.message))
        }
    }
}
