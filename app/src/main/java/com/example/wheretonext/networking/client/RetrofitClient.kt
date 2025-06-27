//package com.example.wheretonext.networking.client
//
//import com.google.firebase.appdistribution.gradle.ApiService
//import retrofit2.converter.gson.GsonConverterFactory
//
//import retrofit2.Retrofit
//
//object RetrofitClient {
//    private const val BASE_URL = "https://reqres.in/"
//
//    val apiService: ApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiService::class.java)
//    }
//}