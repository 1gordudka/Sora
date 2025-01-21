package video.generator.ai.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map


class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    private companion object{
        val LAUNCH = booleanPreferencesKey("1")
        val PAY = booleanPreferencesKey("2")
    }


    suspend fun changePay(){
        dataStore.edit {
            it[PAY] = true
        }
    }

    suspend fun changeLaunch(){
        dataStore.edit {
            it[LAUNCH] = true
        }
    }


    val pay = dataStore.data.map {
        it[PAY] ?: false
    }
    val launch = dataStore.data.map {
        it[LAUNCH] ?: false
    }


}