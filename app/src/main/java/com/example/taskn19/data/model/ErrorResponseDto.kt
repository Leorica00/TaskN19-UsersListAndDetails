package com.example.taskn19.data.model

import com.squareup.moshi.Json

data class ErrorResponseDto(
    @Json(name = "error") val error: String
)