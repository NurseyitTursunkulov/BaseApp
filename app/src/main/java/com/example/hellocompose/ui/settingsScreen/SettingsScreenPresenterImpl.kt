package com.example.hellocompose.ui.settingsScreen

import com.example.hellocompose.ui.MainViewModel

class SettingsScreenPresenterImpl(
    override val loading: Boolean,
    val viewModel: MainViewModel
) : SettingsScreenPresenter {
    override val updateSettings: () -> Unit
        get() = { viewModel.makeSuspendCall() }

}