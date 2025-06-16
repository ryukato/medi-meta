package medi.master.api.client.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class MedicalGenericResponse<T>(
    @field:JacksonXmlProperty(isAttribute = false, localName = "header")
    @field:JsonProperty("header")
    val header: MedicalGenericResponseHeader,
    @field:JacksonXmlProperty(isAttribute = false, localName = "body")
    @field:JsonProperty("body")
    val body: MedicalGenericResponseBody<T>,
)

data class MedicalGenericResponseHeader(
    @field:JacksonXmlProperty(isAttribute = false, localName = "resultCode")
    @field:JsonProperty("resultCode")
    val resultCode: String,
    @field:JacksonXmlProperty(isAttribute = false, localName = "resultMsg")
    @field:JsonProperty("resultMsg")
    val resultMsg: String,
)

data class MedicalGenericResponseBody<T>(
    @field:JacksonXmlProperty(isAttribute = false, localName = "items")
    @field:JsonProperty("items")
    val items: List<T>,
    @field:JacksonXmlProperty(isAttribute = false, localName = "numOfRows")
    @field:JsonProperty("numOfRows")
    val numOfRows: Int,
    @field:JacksonXmlProperty(isAttribute = false, localName = "pageNo")
    @field:JsonProperty("pageNo")
    val pageNo: Int,
    @field:JacksonXmlProperty(isAttribute = false, localName = "totalCount")
    @field:JsonProperty("totalCount")
    val totalCount: Int,
)

data class MedicalItemResponse(
    @field:JsonProperty("ITEM_SEQ") val itemSequence: String,
    @field:JsonProperty("ITEM_NAME") val itemName: String,
    @field:JsonProperty("ITEM_ENG_NAME") val itemNameEnglish: String? = null,
    @field:JsonProperty("ENTP_NAME") val enterpriseName: String,
    @field:JsonProperty("ENTP_ENG_NAME") val enterpriseNameEnglish: String,
    @field:JsonProperty("ENTP_SEQ") val enterpriseSequence: String,
    @field:JsonProperty("ENTP_NO") val enterpriseNumber: String,
    @field:JsonProperty("ITEM_PERMIT_DATE") val itemPermitDate: String,
    @field:JsonProperty("INDUTY") val industryType: String,
    @field:JsonProperty("SPCLTY_PBLC") val specialGeneralType: String,
    @field:JsonProperty("PRDLST_STDR_CODE") val productStandardCode: String,
    @field:JsonProperty("PRDUCT_TYPE") val productType: String,
    @field:JsonProperty("PRDUCT_PRMISN_NO") val productPermissionNo: String,
    @field:JsonProperty("ITEM_INGR_NAME") val ingredientName: String,
    @field:JsonProperty("ITEM_INGR_CNT") val ingredientCount: String,
    @field:JsonProperty("BIG_PRDT_IMG_URL") val bigProductImageUrl: String? = null,
    @field:JsonProperty("PERMIT_KIND_CODE") val permitKindCode: String? = null,
    @field:JsonProperty("CANCEL_DATE") val cancelDate: String?,
    @field:JsonProperty("CANCEL_NAME") val cancelStatus: String,
    @field:JsonProperty("EDI_CODE") val ediCode: String?,
    @field:JsonProperty("BIZRNO") val businessRegistrationNumber: String,
)

data class MedicalItemDetailsResponse(
    @field:JsonProperty("ITEM_SEQ") val itemSequence: String,
    @field:JsonProperty("ITEM_NAME") val itemName: String,
    @field:JsonProperty("ENTP_NAME") val enterpriseName: String,
    @field:JsonProperty("ITEM_PERMIT_DATE") val itemPermitDate: String,
    @field:JsonProperty("CNSGN_MANUF") val consignedManufacturer: String? = null,
    @field:JsonProperty("ETC_OTC_CODE") val otcClassification: String? = null,
    @field:JsonProperty("CHART") val appearanceDescription: String? = null,
    @field:JsonProperty("BAR_CODE") val barCodes: String? = null,
    @field:JsonProperty("MATERIAL_NAME") val materialName: String,
    @field:JsonProperty("EE_DOC_ID") val efficacyDocumentUrl: String,
    @field:JsonProperty("UD_DOC_ID") val dosageDocumentUrl: String,
    @field:JsonProperty("NB_DOC_ID") val precautionsDocumentUrl: String,
    @field:JsonProperty("INSERT_FILE") val insertDocumentUrl: String,
    @field:JsonProperty("STORAGE_METHOD") val storageMethod: String,
    @field:JsonProperty("VALID_TERM") val validityPeriod: String,
    @field:JsonProperty("REEXAM_TARGET") val reevaluationTarget: String?,
    @field:JsonProperty("REEXAM_DATE") val reevaluationDate: String?,
    @field:JsonProperty("PACK_UNIT") val packagingUnit: String,
    @field:JsonProperty("EDI_CODE") val ediCode: String?,
    @field:JsonProperty("PERMIT_KIND_NAME") val permitKind: String,
    @field:JsonProperty("ENTP_NO") val enterpriseNumber: String,
    @field:JsonProperty("MAKE_MATERIAL_FLAG") val manufacturingType: String,
    @field:JsonProperty("NEWDRUG_CLASS_NAME") val newDrugClassification: String,
    @field:JsonProperty("INDUTY_TYPE") val industryType: String,
    @field:JsonProperty("CANCEL_DATE") val cancelDate: String?,
    @field:JsonProperty("CANCEL_NAME") val cancelStatus: String,
    @field:JsonProperty("CHANGE_DATE") val lastChangeDate: String,
    @field:JsonProperty("NARCOTIC_KIND_CODE") val narcoticCategoryCode: String?,
    @field:JsonProperty("GBN_NAME") val historyChanges: String,
    @field:JsonProperty("TOTAL_CONTENT") val totalContent: String? = null,
    @field:JsonProperty("EE_DOC_DATA") val efficacyDocumentData: String,
    @field:JsonProperty("UD_DOC_DATA") val dosageDocumentData: String,
    @field:JsonProperty("NB_DOC_DATA") val precautionsDocumentData: String,
    @field:JsonProperty("PN_DOC_DATA") val pediatricDocumentData: String?,
    @field:JsonProperty("MAIN_ITEM_INGR") val mainIngredientKor: String,
    @field:JsonProperty("INGR_NAME") val ingredientNamesKor: String? = null,
    @field:JsonProperty("ATC_CODE") val atcCode: String? = null,
    @field:JsonProperty("ITEM_ENG_NAME") val itemNameEnglish: String? = null,
    @field:JsonProperty("ENTP_ENG_NAME") val enterpriseNameEnglish: String,
    @field:JsonProperty("MAIN_INGR_ENG") val mainIngredientEnglish: String,
    @field:JsonProperty("BIZRNO") val businessRegistrationNumber: String,
    @field:JsonProperty("RARE_DRUG_YN") val isRareDrug: String? = null
)


data class ItemWrapper<T>(
    @field:JacksonXmlProperty(localName = "item")
    @field:JsonProperty("item")
    val item: T,
)

@JsonRootName("item")
data class BundleItemResponse(
    @field:JsonProperty("trustIndutyCode")
    val trustIndutyCode: String,

    @field:JsonProperty("trustItemName")
    val trustItemName: String,

    @field:JsonProperty("trustMainingr")
    val trustMainingr: String,

    @field:JsonProperty("trustQntList")
    val trustQntList: String,

    @field:JsonProperty("trustEntpName")
    val trustEntpName: String,

    @field:JsonProperty("trustManuf")
    val trustManuf: String,

    @field:JsonProperty("trustItemPermitDate")
    val trustItemPermitDate: String,

    @field:JsonProperty("trustHiraMainingrCode")
    val trustHiraMainingrCode: String,

    @field:JsonProperty("trustHiraPrductCode")
    val trustHiraPrductCode: String,

    @field:JsonProperty("trustAtcCode")
    val trustAtcCode: String,

    @field:JsonProperty("trustCancelName")
    val trustCancelName: String,

    @field:JsonProperty("cnsgnItemSeq")
    val cnsgnItemSeq: String,

    @field:JsonProperty("cnsgnItemName")
    val cnsgnItemName: String,

    @field:JsonProperty("cnsgnEntpName")
    val cnsgnEntpName: String,

    @field:JsonProperty("cnsgnManuf")
    val cnsgnManuf: String,

    @field:JsonProperty("cnsgnItemPermitDate")
    val cnsgnItemPermitDate: String,

    @field:JsonProperty("cnsgnHiraPrductCode")
    val cnsgnHiraPrductCode: String? = null,

    @field:JsonProperty("cnsgnCancelName")
    val cnsgnCancelName: String
)


data class BasePriceItemResponse(

    @field:JacksonXmlProperty(localName = "adtStaDd")
    var appliedStartDate: String? = null,

    @field:JacksonXmlProperty(localName = "chgBfMdsCd")
    var changedBeforeDrugCode: String? = null,

    @field:JacksonXmlProperty(localName = "gnlNmCd")
    var generalNameCode: String? = null,

    @field:JacksonXmlProperty(localName = "injcPthNm")
    var injectionPathName: String? = null,

    @field:JacksonXmlProperty(localName = "itmNm")
    var itemName: String? = null,

    @field:JacksonXmlProperty(localName = "lprcEssTpNm")
    var lowPriceEssentialTypeName: String? = null,

    @field:JacksonXmlProperty(localName = "mdsCd")
    var drugCode: String? = null,

    @field:JacksonXmlProperty(localName = "meftDivNo")
    var effectDivisionNumber: String? = null,

    @field:JacksonXmlProperty(localName = "mnfEntpNm")
    var manufacturerName: String? = null,

    @field:JacksonXmlProperty(localName = "mxCprc")
    var maxPrice: String? = null,

    @field:JacksonXmlProperty(localName = "nomNm")
    var specificationName: String? = null,

    @field:JacksonXmlProperty(localName = "payTpNm")
    var paymentTypeName: String? = null,

    @field:JacksonXmlProperty(localName = "spcGnlTpNm")
    var specialtyGeneralTypeName: String? = null,

    @field:JacksonXmlProperty(localName = "unit")
    var unit: String? = null
)

data class RetrieveStopSaleInformationResponse(
    @field:JsonProperty("ITEM_SEQ")
    val itemSequence: String,
    @field:JsonProperty("BIZRNO")
    val businessRegistrationNumber: String,
    @field:JsonProperty("PRDUCT")
    val itemName: String,
    @field:JsonProperty("ENTRPS")
    val enterpriseName: String,
    @field:JsonProperty("ENFRC_YN")
    val enforceYn: String,
    @field:JsonProperty("RTRVL_RESN")
    val retrieveReason: String,
    @field:JsonProperty("RTRVL_CMMND_DT")
    val retrieveCommandDate: String? = null,
    @field:JsonProperty("RECALL_COMMAND_DATE")
    val recallCommandDate: String,
    @field:JsonProperty("STD_CD")
    val standardCode: String,
)


data class DrugPillIdentificationResponse(
    @field:JsonProperty("ITEM_SEQ")
    val itemSequence: String,

    @field:JsonProperty("ITEM_NAME")
    val itemName: String,

    @field:JsonProperty("ENTP_SEQ")
    val enterpriseSequence: String,

    @field:JsonProperty("ENTP_NAME")
    val enterpriseName: String,

    @field:JsonProperty("CHART")
    val chart: String,

    @field:JsonProperty("ITEM_IMAGE")
    val itemImage: String,

    @field:JsonProperty("PRINT_FRONT")
    val printFront: String,

    @field:JsonProperty("PRINT_BACK")
    val printBack: String? = null,

    @field:JsonProperty("DRUG_SHAPE")
    val drugShape: String,

    @field:JsonProperty("COLOR_CLASS1")
    val colorClass1: String,

    @field:JsonProperty("COLOR_CLASS2")
    val colorClass2: String? = null,

    @field:JsonProperty("LINE_FRONT")
    val lineFront: String? = null,

    @field:JsonProperty("LINE_BACK")
    val lineBack: String? = null,

    @field:JsonProperty("LENG_LONG")
    val longAxis: String,

    @field:JsonProperty("LENG_SHORT")
    val shortAxis: String,

    @field:JsonProperty("THICK")
    val thick: String,

    @field:JsonProperty("IMG_REGIST_TS")
    val imageCreatedDate: String,

    @field:JsonProperty("CLASS_NO")
    val classNo: String,

    @field:JsonProperty("CLASS_NAME")
    val className: String,

    @field:JsonProperty("ETC_OTC_NAME")
    val etcOtcName: String,

    @field:JsonProperty("ITEM_PERMIT_DATE")
    val itemPermitDate: String,

    @field:JsonProperty("FORM_CODE_NAME")
    val formCodeName: String,

    @field:JsonProperty("MARK_CODE_FRONT_ANAL")
    val markCodeFrontAnal: String,

    @field:JsonProperty("MARK_CODE_BACK_ANAL")
    val markCodeBackAnal: String,

    @field:JsonProperty("MARK_CODE_FRONT_IMG")
    val markCodeFrontImg: String,

    @field:JsonProperty("MARK_CODE_BACK_IMG")
    val markCodeBackImg: String,

    @field:JsonProperty("ITEM_ENG_NAME")
    val itemEngName: String,

    @field:JsonProperty("CHANGE_DATE")
    val changeDate: String,

    @field:JsonProperty("MARK_CODE_FRONT")
    val markCodeFront: String,

    @field:JsonProperty("MARK_CODE_BACK")
    val markCodeBack: String? = null,

    @field:JsonProperty("EDI_CODE")
    val ediCode: String,

    @field:JsonProperty("BIZRNO")
    val businessRegistrationNumber: String,

    @field:JsonProperty("STD_CD")
    val standardCode: String,
)



data class RetrieveStopSaleDetailsInformationResponse(
    @field:JsonProperty("ITEM_SEQ")
    val itemSequence: String,
    @field:JsonProperty("BIZRNO")
    val businessRegistrationNumber: String,
    @field:JsonProperty("PRDUCT")
    val itemName: String,
    @field:JsonProperty("USGPD")
    val validityPeriod: String,
    @field:JsonProperty("PACKNG_UNIT")
    val packingUnit: String,
    @field:JsonProperty("ENTRPS")
    val enterpriseName: String,
    @field:JsonProperty("ENTRPS_ADRES")
    val enterpriseAddress: String,
    @field:JsonProperty("ENTRPS_TELNO")
    val enterpriseTelNo: String? = null,
    @field:JsonProperty("MNFCTUR_NO")
    val manufacturerNo: String,
    @field:JsonProperty("RTRVL_RESN")
    val retrieveReason: String,
    @field:JsonProperty("RM")
    val comments: String? = null,
    @field:JsonProperty("RTRVL_CMMND_DT")
    val retrieveCommandDate: String? = null,
    @field:JsonProperty("RECALL_COMMAND_DATE")
    val recallCommandDate: String,
    @field:JsonProperty("OPEN_END_DATE")
    val openEndDate: String,
    @field:JsonProperty("STD_CD")
    val standardCode: String,
)
