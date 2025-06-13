package medi.master.core.domain.api.parser

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import medi.master.core.domain.medical.product.MedicalDetailItem

class DefaultProductApprovalSourceParser : RawDataParser<String, Set<MedicalDetailItem>> {

    private val jacksonObjectMapper = jacksonObjectMapper().apply { registerModules(JavaTimeModule()) }
    private val rawDataTypeRef = object : TypeReference<MedicalGenericResponse<MedicalItemResponse>>() {}
    private val medicalDetailItemContentsParser = MedicalDetailItemContentsParser()

    /**
     * Parses the raw data of type [String] into a result of type [Map<String, Any>].
     *
     * @param input The raw data to be parsed.
     * @return The parsed result as a [Map<String, Any>].
     */
    override fun parse(input: String): Set<MedicalDetailItem> {
        val medicalItemResponse = jacksonObjectMapper.readValue(input, rawDataTypeRef)
        return medicalItemResponse.body.items.map {
            convertToMedicalItem(it)
        }.toSet()

    }

    private fun convertToMedicalItem(
        medicalItemResponse: MedicalItemResponse,
    ): MedicalDetailItem {
        val medicalItem = medicalItemResponse.toMedicalItemExcludeDocumentsData()
        return medicalItem.copy(
            efficacyDocuments = if (medicalItemResponse.efficacyDocumentData.isNotBlank()) {
                medicalDetailItemContentsParser.parse(medicalItemResponse.efficacyDocumentData)
            } else {
                emptySet()
            },
            dosageDocuments = if (medicalItemResponse.dosageDocumentData.isNotBlank()) {
                medicalDetailItemContentsParser.parse(medicalItemResponse.dosageDocumentData)
            } else {
                emptySet()
            },
            precautionsDocuments = if (medicalItemResponse.precautionsDocumentData.isNotBlank()) {
                medicalDetailItemContentsParser.parse(medicalItemResponse.precautionsDocumentData)
            } else {
                emptySet()
            },
            pediatricDocuments = if (medicalItemResponse.pediatricDocumentData.isNullOrBlank().not()) {
                medicalDetailItemContentsParser.parse(medicalItemResponse.pediatricDocumentData)
            } else {
                emptySet()
            }
        )
    }
    

}

data class MedicalGenericResponse<T>(
    @JsonProperty("header") val header: MedicalGenericResponseHeader,
    @JsonProperty("body") val body: MedicalGenericResponseBody<T>,
)

data class MedicalGenericResponseHeader(
    @JsonProperty("resultCode") val resultCode: String,
    @JsonProperty("resultMsg") val resultMsg: String,
)

data class MedicalGenericResponseBody<T>(
    @JsonProperty("items") val items: List<T>,
    @JsonProperty("numOfRows") val numOfRows: Int,
    @JsonProperty("pageNo") val pageNo: Int,
    @JsonProperty("totalCount") val totalCount: Int,
)


data class MedicalItemResponse(
    @JsonProperty("ITEM_SEQ") val itemSequence: String,
    @JsonProperty("ITEM_NAME") val itemName: String,
    @JsonProperty("ENTP_NAME") val enterpriseName: String,
    @JsonProperty("ITEM_PERMIT_DATE") val itemPermitDate: String,
    @JsonProperty("CNSGN_MANUF") val consignedManufacturer: String? = null,
    @JsonProperty("ETC_OTC_CODE") val otcClassification: String,
    @JsonProperty("CHART") val appearanceDescription: String,
    @JsonProperty("BAR_CODE") val barCodes: String? = null,
    @JsonProperty("MATERIAL_NAME") val materialName: String,
    @JsonProperty("EE_DOC_ID") val efficacyDocumentUrl: String,
    @JsonProperty("UD_DOC_ID") val dosageDocumentUrl: String,
    @JsonProperty("NB_DOC_ID") val precautionsDocumentUrl: String,
    @JsonProperty("INSERT_FILE") val insertDocumentUrl: String,
    @JsonProperty("STORAGE_METHOD") val storageMethod: String,
    @JsonProperty("VALID_TERM") val validityPeriod: String,
    @JsonProperty("REEXAM_TARGET") val reevaluationTarget: String?,
    @JsonProperty("REEXAM_DATE") val reevaluationDate: String?,
    @JsonProperty("PACK_UNIT") val packagingUnit: String,
    @JsonProperty("EDI_CODE") val ediCode: String?,
    @JsonProperty("PERMIT_KIND_NAME") val permitKind: String,
    @JsonProperty("ENTP_NO") val enterpriseNumber: String,
    @JsonProperty("MAKE_MATERIAL_FLAG") val manufacturingType: String,
    @JsonProperty("NEWDRUG_CLASS_NAME") val newDrugClassification: String,
    @JsonProperty("INDUTY_TYPE") val industryType: String,
    @JsonProperty("CANCEL_DATE") val cancelDate: String?,
    @JsonProperty("CANCEL_NAME") val cancelStatus: String,
    @JsonProperty("CHANGE_DATE") val lastChangeDate: String,
    @JsonProperty("NARCOTIC_KIND_CODE") val narcoticCategoryCode: String?,
    @JsonProperty("GBN_NAME") val historyChanges: String,
    @JsonProperty("TOTAL_CONTENT") val totalContent: String? = null,
    @JsonProperty("EE_DOC_DATA") val efficacyDocumentData: String,
    @JsonProperty("UD_DOC_DATA") val dosageDocumentData: String,
    @JsonProperty("NB_DOC_DATA") val precautionsDocumentData: String,
    @JsonProperty("PN_DOC_DATA") val pediatricDocumentData: String?,
    @JsonProperty("MAIN_ITEM_INGR") val mainIngredientKor: String,
    @JsonProperty("INGR_NAME") val ingredientNamesKor: String? = null,
    @JsonProperty("ATC_CODE") val atcCode: String? = null,
    @JsonProperty("ITEM_ENG_NAME") val itemNameEnglish: String? = null,
    @JsonProperty("ENTP_ENG_NAME") val enterpriseNameEnglish: String,
    @JsonProperty("MAIN_INGR_ENG") val mainIngredientEnglish: String,
    @JsonProperty("BIZRNO") val businessRegistrationNumber: String,
    @JsonProperty("RARE_DRUG_YN") val isRareDrug: String? = null
)


fun MedicalItemResponse.toMedicalItemExcludeDocumentsData(): MedicalDetailItem {
    return MedicalDetailItem(
        itemSequence = itemSequence,
        itemName = itemName,
        enterpriseName = enterpriseName,
        itemPermitDate = itemPermitDate,
        consignedManufacturer = consignedManufacturer,
        otcClassification = otcClassification,
        appearanceDescription = appearanceDescription,
        barCodes = barCodes ?: "",
        materialName = materialName,
        efficacyDocumentUrl = efficacyDocumentUrl,
        dosageDocumentUrl = dosageDocumentUrl,
        precautionsDocumentUrl = precautionsDocumentUrl,
        insertDocumentUrl = insertDocumentUrl,
        storageMethod = storageMethod,
        validityPeriod = validityPeriod,
        reevaluationTarget = reevaluationTarget,
        reevaluationDate = reevaluationDate,
        packagingUnit = packagingUnit,
        ediCode = ediCode,
        permitKind = permitKind,
        enterpriseNumber = enterpriseNumber,
        manufacturingType = manufacturingType,
        newDrugClassification = newDrugClassification,
        industryType = industryType,
        cancelDate = cancelDate,
        cancelStatus = cancelStatus,
        lastChangeDate = lastChangeDate,
        narcoticCategoryCode = narcoticCategoryCode,
        historyChanges = historyChanges,
        totalContent = totalContent,
        mainIngredientKor = mainIngredientKor,
        ingredientNamesKor = ingredientNamesKor ?: "",
        atcCode = atcCode,
        itemNameEnglish = itemNameEnglish ?: "",
        enterpriseNameEnglish = enterpriseNameEnglish,
        mainIngredientEnglish = mainIngredientEnglish,
        businessRegistrationNumber = businessRegistrationNumber,
        isRareDrug = isRareDrug
    )
}
