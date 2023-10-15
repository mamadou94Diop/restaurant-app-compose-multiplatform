package features.menu.data.source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val lg: String?,
    val sm: String?
)