package video.generator.ai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import video.generator.ai.data.preferences.UserPreferencesRepository
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val appPreferencesRepository: UserPreferencesRepository
) : ViewModel(){


    val pay = appPreferencesRepository.pay.map { it }.stateIn(viewModelScope, SharingStarted.Eagerly, null)
    val launch = appPreferencesRepository.launch.map { it }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun launchApp(){
        viewModelScope.launch{
            appPreferencesRepository.changeLaunch()
        }
    }
    fun changePay(){
        viewModelScope.launch{
            appPreferencesRepository.changePay()
        }
    }
}