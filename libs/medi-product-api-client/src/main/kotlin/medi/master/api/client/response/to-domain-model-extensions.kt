package medi.master.api.client.response

import medi.master.core.domain.medical.product.MedicalDetailItem


fun MedicalItemDetailsResponse.toMedicalItemExcludeDocumentsData(): MedicalDetailItem {
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
