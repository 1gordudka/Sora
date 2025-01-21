package video.generator.ai.data.network.server

val statusYES = "yes"
val statusNOYEAR = "noYear"
val statusNOWEEK = "noWeek"

data class Status (
    val code: Int,
    val result: String,
)