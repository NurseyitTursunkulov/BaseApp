package com.example.hellocompose.ui.settingsScreen

interface SettingsScreenPresenter {
    val loading: Boolean
    val updateSettings: () -> Unit
}