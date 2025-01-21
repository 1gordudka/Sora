package video.generator.ai.data.network.api.model.request

data class GenerateRequest(
    val options: Options?,
    val promptText: String?
)