/*
 * Copyright 2024 shinhyo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.shinhyo.brba.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import io.github.shinhyo.brba.core.datastore.model.DeviceData
import io.github.shinhyo.brba.core.model.BrbaThemeMode
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeviceDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    object PreferencesKey {
        val MODE_THEME = intPreferencesKey("MODE_THEME")
    }

    val deviceDataFlow = dataStore.data.map { preferences ->
        DeviceData(
            themeMode = preferences[PreferencesKey.MODE_THEME]?.let {
                BrbaThemeMode.entries[it]
            } ?: BrbaThemeMode.Dark,
        )
    }

    suspend fun setThemeMode(themeMode: BrbaThemeMode) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.MODE_THEME] = themeMode.ordinal
        }
    }
}