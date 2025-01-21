package video.generator.ai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import video.generator.ai.data.local.model.Generation

@Database(entities = [Generation::class], version = 1, exportSchema = false)
abstract class GenerationDatabase : RoomDatabase() {
    abstract fun generationDao(): GenerationDao
}