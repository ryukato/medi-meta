package medi.master.core.domain.medical.product.model

import java.time.LocalDate

data class RecallInfo(
    val id: Long,
    val productItemSequence: String,
    val reason: String,
    val recallDate: LocalDate?
)
