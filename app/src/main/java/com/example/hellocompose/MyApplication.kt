package com.example.hellocompose

import com.example.hellocompose.data.login.localDataSource.LocalDataSourceImpl
import android.app.Application
import com.example.hellocompose.data.login.LoginMock
import com.example.hellocompose.data.login.LoginRepo
import com.example.hellocompose.data.login.LoginRepoImpl
import com.example.hellocompose.data.login.localDataSource.LocalDataSource
import com.example.hellocompose.data.login.remoteDS.RemoteDS
import com.example.hellocompose.data.login.remoteDS.RemoteDSImpl
import com.example.hellocompose.di.apiModules
import com.example.hellocompose.domain.EntryPointUseCase
import com.example.hellocompose.domain.LoginUseCase
import com.example.hellocompose.ui.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule,apiModules)
        }
    }

    val appModule = module {
        viewModel {
            MainViewModel(
                loginUseCase = LoginUseCase(loginRepo = get()),
                entryPointUseCase = EntryPointUseCase(loginRepo = get()),
                local = LocalDataSourceImpl(androidContext())
            )
        }
        single<LoginRepo> {
            LoginRepoImpl(
                remoteDS = get(),
                localDataSource = get()
            )
        }
        single<RemoteDS>{
            RemoteDSImpl(
                apiService = get()
            )
        }
        single<LocalDataSource> {
            LocalDataSourceImpl(
                androidContext()
            )
        }
    }
}