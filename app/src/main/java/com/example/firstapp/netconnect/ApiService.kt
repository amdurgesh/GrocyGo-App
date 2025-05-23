package com.example.firstapp.netconnect

import com.example.firstapp.data.InternetItem
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://training-uploads.internshala.com"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        Json.asConverterFactory(
            "application/json".toMediaType())
    )
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("android/grocery_delivery_app/items.json")
    suspend fun getItems(): List<InternetItem>
}

object FirstApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}