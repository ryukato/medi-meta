package medi.master.core.domain.medical.company.model

import java.time.LocalDateTime
import medi.master.date.extension.toEpochMilli

data class Company(
    val identifier: String,
    val name: String,
    val nameInEng: String? = null,
    val businessType: String,
    val businessRegistrationNo: String,
    val businessLicenseNo: String,
    val createdAt: Long = LocalDateTime.now().toEpochMilli(),
    val lastModifiedAt: Long? = null
)
