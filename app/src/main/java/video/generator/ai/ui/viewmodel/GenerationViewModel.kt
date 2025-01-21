package video.generator.ai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import video.generator.ai.data.local.GenerationDao
import video.generator.ai.data.local.model.Generation
import javax.inject.Inject

@HiltViewModel
class GenerationViewModel @Inject constructor(
    private val generationDao: GenerationDao
) : ViewModel(){

    private val _generations = MutableStateFlow<List<Generation>>(emptyList())
    val generations = _generations.asStateFlow()

    init {
        loadGenerations()
    }

    fun loadGenerations(){
        viewModelScope.launch(Dispatchers.IO){
            _generations.value = generationDao.getAll()
        }
    }

    fun addGeneration(generation: Generation){
        viewModelScope.launch(Dispatchers.IO){
            generationDao.insert(generation)
            loadGenerations()
        }
    }
}