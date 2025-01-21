package video.generator.ai.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import video.generator.ai.data.local.model.Generation

@Dao
interface GenerationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(generation: Generation)

    @Query("SELECT * FROM generations")
    suspend fun getAll() : List<Generation>


}