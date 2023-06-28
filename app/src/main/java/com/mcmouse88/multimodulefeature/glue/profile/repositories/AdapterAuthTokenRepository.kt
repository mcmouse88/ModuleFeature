package com.mcmouse88.multimodulefeature.glue.profile.repositories

import com.mcmouse88.multimodulefeature.data.settings.SettingsDataSource
import com.mcmouse88.multimodulefeature.profile.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class AdapterAuthTokenRepository @Inject constructor(
    private val settingsDataSource: SettingsDataSource
) : AuthTokenRepository {

    override suspend fun clearToken() {
        settingsDataSource.setToken(null)
    }
}