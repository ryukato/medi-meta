package medi.master.core.domain.common.excepion

data class TemporaryDataFetchException(
    val apiPath: String,
    val operationName: String = "",
    val queryParameters: Map<String, Any> = emptyMap(),
) : RuntimeException(
    "Temporary data fetch failed for API path=$apiPath with query=$queryParameters, operationName=$operationName"
)

data class PermanentDataFetchException(
    val apiPath: String,
    val operationName: String = "",
    val queryParameters: Map<String, Any> = emptyMap(),
) : RuntimeException(
    "Permanent data fetch failed for API path=$apiPath with query=$queryParameters, operationName=$operationName"
)

data class InvalidRequestDataFetchException(
    val apiPath: String,
    val operationName: String = "",
    val queryParameters: Map<String, Any> = emptyMap(),
) : RuntimeException(
    " Invalid request to fetch for API path=$apiPath with query=$queryParameters, operationName=$operationName"
)
