package medi.master.core.domain.medical.product.model

import java.time.LocalDate

data class DrugProduct(
    val itemSequence: String,
    val productName: String,
    val productEngName: String? = null,
    val permitDate: LocalDate,
    val manufacturerId: Long
) {
    companion object {
        fun createEmpty(): DrugProduct {
            return DrugProduct(
                itemSequence = "",
                productName = "",
                productEngName = null,
                permitDate = LocalDate.now(),
                manufacturerId = 0L
            )
        }
    }
}
