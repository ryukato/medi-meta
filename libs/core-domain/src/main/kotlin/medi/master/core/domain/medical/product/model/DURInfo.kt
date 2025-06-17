package medi.master.core.domain.medical.product.model

data class DURInfo(
    val id: Long,
    val type: String,  // 예: "병용금기", "임부금기"
    val ingredientId: Long,
    val productItemSequence: String,
    val content: String
)
