package medi.master.core.domain.medical.product.model

import medi.master.core.domain.medical.company.model.Manufacturer

data class DrugMaster(
    val product: DrugProduct,
    val manufacturer: Manufacturer,
    val ingredients: List<DrugIngredient>,
    val pricing: PricingInfo?,
    val recall: RecallInfo?,
    val imageInfo: DrugImageInfo?,
    val durInfos: List<DURInfo>
) {
    companion object {
        fun createEmpty(): DrugMaster {
            return DrugMaster(
                product = DrugProduct.createEmpty(),
                manufacturer = Manufacturer.createEmpty(),
                ingredients = emptyList(),
                pricing = null,
                recall = null,
                imageInfo = null,
                durInfos = emptyList()
            )
        }
    }
}
