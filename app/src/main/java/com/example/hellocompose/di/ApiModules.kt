package com.example.hellocompose.di

import com.example.hellocompose.data.login.remoteDS.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.timelysoft.shelter.android.util.network.NetworkResponseAdapterFactory
import okhttp3.*
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModules = module {

    factory<Gson> {
        GsonBuilder().setLenient().create()
    }
    single<OkHttpClient> {
        OkHttpClient().newBuilder()
            .addInterceptor(Interceptor { chain ->
                val newUrl = chain.request().url
                    .newBuilder()
                    .build()

                val newRequest = chain.request()
                    .newBuilder()
//                    .addHeader("Authorization", "bearer " + AppPreferences.token)
                    .url(newUrl)
                    .build()

                chain.proceed(newRequest)
            })
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .client(
                get()
            )
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("http://54.36.192.142:8073/api/")
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }


}

