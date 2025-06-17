package medi.master.core.domain.medical.company.model

data class Manufacturer(
    val id: Long,
    val name: String,
    val businessRegistrationNumber: String? = null,
    val type: String? = null  // 예: "수입업", "의약품 제조업", "의약외품 제조업"
) {
    companion object {
        fun createEmpty(): Manufacturer {
            return Manufacturer(
                id = 0L,
                name = "",
                businessRegistrationNumber = null,
                type = null
            )
        }
    }
}
