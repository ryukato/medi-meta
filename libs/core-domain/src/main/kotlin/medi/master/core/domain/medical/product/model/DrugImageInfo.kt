package medi.master.core.domain.medical.product.model

data class DrugImageInfo(
    val itemSequence: String,
    val shape: String,
    val color1: String?,
    val color2: String?,
    val frontMark: String?,
    val backMark: String?,
    val imageUrl: String?
)
