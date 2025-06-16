package medi.master.core.domain.medical.product.model

import java.time.LocalDate

data class DetailsDrug(
    val identifier: String, // 품목기준코드
    val chart: String, // 성상
    val materialName: String, // 원료성분
    val storageMethod: String, // 저장방법
    val validTerm: String, // 유효기간
    val packUnit: String, // 포장단위
    val makeMaterialFlag: String, // 완제/원료구분
    val totalContent: String, // 총량
    val effects: String, // 효능효과
    val effectsDocUrl: String, // 효능효과 문서 주소
    val usage: String, // 용법용량
    val usageDocUrl: String, // 용법용량 문서 주소
    val cautions: String, // 주의사항
    val cautionsDocUrl: String, // 주의사항 문서 주소
    val majorIngredients: String, // 유효성분
    val ingredients: String, // 첨가제
    val rareDrugYn: Boolean = false, // 희귀의약품여부 (기본값 false)
    val atcCode: String?, // ATC코드
    val barCode: String?, // 바코드
    val changeDate: LocalDate?, // 변경일자
    val changeHistories: String?, // 변경이력
    val narcoticKindCode: String?, // 마약종류코드
    val newDrugClassName: String?, // 신약
    val createdAt: Long, // 생성일
    val lastModifiedAt: Long? // 최종 수정일
)

