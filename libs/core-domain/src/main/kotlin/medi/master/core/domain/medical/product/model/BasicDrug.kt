package medi.master.core.domain.medical.product.model

data class BasicDrug(
    val identifier: String, // 품목기준코드
    val serialNumber: String, // 품목일련번호
    val name: String, // 품목명
    val nameInEng: String?, // 영문제품명
    val approvedAt: Long, // 품목허가일자 (e.g., 19781121)
    val classificationName: String, // 분류명
    val specialtyType: String, // 전문/일반구분
    val permissionNo: String, // 개별 의약품의 허가 번호
    val permissionType: String, // 개별 의약품의 허가/신고구분
    val majorIngredientName: String, // 주성분명
    val ingredientCount: String, // 주성분수
    val mainItemImageUrl: String?, // 큰제품이미지
    val ediCode: String?, // 보험코드
    val companyName: String, // 업체명
    val companyNameInEng: String?, // 영문업체명
    val companyIdentifier: String, // 업일련번호
    val businessType: String, // 업종
    val businessRegistrationNumber: String, // 사업자등록번호
    val businessLicenseNumber: String, // 업허가번호
    val createdAt: Long, // 생성일
    val lastModifiedAt: Long? // 최종 수정일
)
