package medi.master.core.domain.medical.product.model

data class DrugIngredient(
    val id: Long,
    val code: String,
    val name: String,
    val dosageUnit: String? = null
)
