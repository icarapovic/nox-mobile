package dev.nox.data.remote.client

import dev.nox.data.remote.dto.AlgoliaItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface AlgoliaApi {

    @GET("items/{itemId}")
    suspend fun fetchItem(@Path("itemId") itemId: Long): AlgoliaItemDto
}
