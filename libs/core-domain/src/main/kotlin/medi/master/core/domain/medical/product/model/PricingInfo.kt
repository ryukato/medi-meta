package medi.master.core.domain.medical.product.model

// this is draft code
data class PricingInfo(
    val id: Long,
    val productItemSequence: String,
    val unitPrice: Int,
    val paymentType: String
)
