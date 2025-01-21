package video.generator.ai.data.local.repository

import video.generator.ai.data.local.GenerationDao
import video.generator.ai.data.local.model.Generation


class GenerationRepository(
    private val generationDao: GenerationDao
) {

    suspend fun addGeneration(generation: Generation) = generationDao.insert(generation)
    suspend fun getAll() = generationDao.getAll()
}