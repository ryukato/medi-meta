package medi.master.api.client.response

import com.fasterxml.jackson.annotation.JsonProperty

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
    @JsonProperty("ITEM_ENG_NAME") val itemNameEnglish: String? = null,
    @JsonProperty("ENTP_NAME") val enterpriseName: String,
    @JsonProperty("ENTP_ENG_NAME") val enterpriseNameEnglish: String,
    @JsonProperty("ENTP_SEQ") val enterpriseSequence: String,
    @JsonProperty("ENTP_NO") val enterpriseNumber: String,
    @JsonProperty("ITEM_PERMIT_DATE") val itemPermitDate: String,
    @JsonProperty("INDUTY") val industryType: String,
    @JsonProperty("SPCLTY_PBLC") val specialGeneralType: String,
    @JsonProperty("PRDLST_STDR_CODE") val productStandardCode: String,
    @JsonProperty("PRDUCT_TYPE") val productType: String,
    @JsonProperty("PRDUCT_PRMISN_NO") val productPermissionNo: String,
    @JsonProperty("ITEM_INGR_NAME") val ingredientName: String,
    @JsonProperty("ITEM_INGR_CNT") val ingredientCount: String,
    @JsonProperty("BIG_PRDT_IMG_URL") val bigProductImageUrl: String? = null,
    @JsonProperty("PERMIT_KIND_CODE") val permitKindCode: String? = null,
    @JsonProperty("CANCEL_DATE") val cancelDate: String?,
    @JsonProperty("CANCEL_NAME") val cancelStatus: String,
    @JsonProperty("EDI_CODE") val ediCode: String?,
    @JsonProperty("BIZRNO") val businessRegistrationNumber: String,
)


data class MedicalItemDetailsResponse(
    @JsonProperty("ITEM_SEQ") val itemSequence: String,
    @JsonProperty("ITEM_NAME") val itemName: String,
    @JsonProperty("ENTP_NAME") val enterpriseName: String,
    @JsonProperty("ITEM_PERMIT_DATE") val itemPermitDate: String,
    @JsonProperty("CNSGN_MANUF") val consignedManufacturer: String? = null,
    @JsonProperty("ETC_OTC_CODE") val otcClassification: String? = null,
    @JsonProperty("CHART") val appearanceDescription: String? = null,
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
