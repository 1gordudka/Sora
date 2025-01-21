package video.generator.ai.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("generations")
data class Generation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val prompt: String,
    val videoLink: String,
    val posterLink: String,
    val date: String,
)
