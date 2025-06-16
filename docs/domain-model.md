# Medical product meta system : core-domain-model

## Types of models

* Drug: 의약품을 나타내고, simple, details로 나누어 설계 및 구현할 수 있다.

## Drug

### Basic

의약품의 공통적인 속성들을 가지고 있으며, 최대한 nullable 항목은 피하도록 한다.

#### Properties

| name                       | description            | type   | sample                                                                 | nullable | comments |
|----------------------------|------------------------|--------|------------------------------------------------------------------------|----------|----------|
| identifier                 | 품목기준코드                 | string | 195500005                                                              | NO       |          |
| serialNumber               | 품목일련번호                 | string |                                                                        | NO       |          |
| name                       | 품목명                    | string | 중외5%포도당생리식염액(수출명:5%DextroseinnormalsalineInj.)                         | NO       |          |
| nameInEng                  | 영문제품명                  | string | Choongwae 5% Dextrose In Normal Saline Inj.                            | YES      |          |
| approvedAt                 | 품목허가일자                 | Long   | 280454400000                                                           | NO       | 19781121 |
| classificationName         | 분류명                    | string | [02320]소화성궤양용제                                                         | NO       |          |
| specialtyType              | 전문/일반구분                | string | 전문의약품                                                                  | NO       |          |
| permissionNo               | 개별 의약품의 허가 번호          | string | 153                                                                    | NO       |          |
| permissionType             | 개별 의약품의 허가/신고구분(예: 허가) | string | 허가                                                                     | NO       | 신고, 허가   |
| majorIngredientName        | 주성분명                   | string | Cimetidine                                                             | NO       |          |
| ingredientCount            | 주성분수                   | string | 1                                                                      | NO       |          |
| mainItemImageUrl           | 큰제품이미지                 | string | https://nedrug.mfds.go.kr/pbp/cmn/itemImageDownload/147427336539800172 | YES      |          |
| ediCode                    | 보험코드                   | string | 644902212,644902220                                                    | YES      |          |
| companyName                | 업체명                    | string | 국제약품(주)                                                                | NO       |          |
| companyNameInEng           | 영문업체명                  | string | KUKJE pharmaceutical co., ltd                                          | YES      |          |
| companyIdentifier          | 업일련번호                  | string | 19620006                                                               | NO       |          |
| businessType               | 업종                     | string | 의약품                                                                    | NO       |          |
| businessRegistrationNumber | 사업자등록번호                | string | 1188102477                                                             | NO       |          |
| businessLicenseNumber      | 업허가번호                  | string | 1419                                                                   | NO       |          |
| createdAt                  | 생성일                    | Long   | 1750041682000                                                          | NO       |          |
| lastModifiedAt             | 최종 수정일                 | Long   |                                                                        | YES      |          |

#### Raw data mappings

| raw property name | model property name        | nullable | comments                             |
|-------------------|----------------------------|----------|--------------------------------------|
| ITEM_SEQ          | identifier                 | NO       |                                      |
| ITEM_NAME         | name                       | NO       |                                      |
| ITEM_ENG_NAME     | nameInEng                  | YES      |                                      |
| ENTP_NAME         | companyName                | NO       |                                      |
| ENTP_ENG_NAME     | companyNameInEng           | YES      |                                      |
| ENTP_SEQ          | companyIdentifier          | NO       |                                      |
| BIZRNO            | businessRegistrationNumber | NO       |                                      |
| ENTP_NO           | businessLicenseNumber      | NO       |                                      |
| ITEM_PERMIT_DATE  | approvedAt                 | NO       |                                      |
| INDUTY            | businessType               | NO       |                                      |
| PRDLST_STDR_CODE  | serialNumber               | NO       | 품목일련번호                               |
| SPCLTY_PBLC       | specialtyType              | NO       | 전문/일반구분                              |
| PRDUCT_TYPE       | type                       | NO       | 아래의 형식을 따름<br>`[type_code]type_name` |
| PRDUCT_PRMISN_NO  | permissionedNo             | NO       |                                      |
| ITEM_INGR_NAME    | majorIngredientName        | NO       |                                      |
| ITEM_INGR_CNT     | incredientCount            | NO       |                                      |
| BIG_PRDT_IMG_URL  | mainItemImageUrl           | YES      |                                      |
| PERMIT_KIND_CODE  | permissionType             | NO       |                                      |
| EDI_CODE          | ediCode                    | YES      |                                      |
| CANCEL_DATE       | ?                          |          | 취하일자                                 |
| CANCEL_NAME       | ?                          |          | 취하구분                                 |

#### Things to check

* 의약품과 사업자 정보를 분리(normalization)할지 여부

### Details

#### Properties

| name             | description | type    | sample                                                                                       | nullable | comments   |
|------------------|-------------|---------|----------------------------------------------------------------------------------------------|----------|------------|
| identifier       | 품목기준코드      | string  | 195500005                                                                                    | NO       |            |
| chart            | 성상          | string  | 폴리프로필렌, 다층필름플라스틱용기에 든 무색 투명한 액체                                                              | NO       |            |
| materialName     | 원료성분        | string  | 총량 : 1000밀리리터\|성분명 : 포도당..                                                                   | NO       |            |
| storageMethod    | 저장방법        | string  | 밀봉용기                                                                                         | NO       |            |
| validTerm        | 유효기간        | string  | 제조일로부터 36 개월                                                                                 | NO       |            |
| packUnit         | 포장단위        | string  | 500, 1000밀리리터                                                                                | NO       |            |
| makeMaterialFlag | 완제/원료구분     | string  | 완제의약품                                                                                        | NO       |            |
| totalContent     | 총량          | string  | 1000                                                                                         | NO       |            |
| effects          | 효능효과        | string  | ○ 탈수증, 수술전후 등의 수분ㆍ전해질 보                                                                      | NO       |            |
| effectsDocUrl    | 효능효과 문서 주소  | string  | https://nedrug.mfds.go.kr/pbp/cmn/pdfDownload/195500005/EE                                   | NO       |            |
| usage            | 용법용량        | string  | 보통 성인 1일 500～1,000mL                                                                         | NO       |            |
| usageDocUrl      | 용법용량 문서 주소  | string  | https://nedrug.mfds.go.kr/pbp/cmn/pdfDownload/195500005/UD                                   | NO       |            |
| cautions         | 주의사항        | string  | 포도당 함유제제를 정맥주사하는 환자는                                                                         | NO       |            |
| cautionsDocUrl   | 주의사항 문서 주소  | string  | https://nedrug.mfds.go.kr/pbp/cmn/pdfDownload/195500005/NB                                   | NO       |            |
| majorIngredients | 유효성분        | string  | [M040702]포도당\|[M040426]염화나트륨                                                                 | NO       |            |
| ingredients      | 첨가제         | string  | [M040534]주사용수                                                                                | NO       |            |
| rareDrugYn       | 희귀의약품여부     | boolean | false                                                                                        | NO       | 기본값: false |
| atcCode          | ATC코드       | string  | B05BB02                                                                                      |          |            |
| barCode          | 바코드         | string  | 8806449022106,8806449022113,                                                                 | YES      |            |
| changeDate       | 변경일자        | Date    | 20210827                                                                                     | YES      |            |
| changeHistories  | 변경이력        | string  | 사용상주의사항변경(부작용포함), 2024-05-22/사용상주의사항변경(부작용포함), 2024-02-15/성상, 2021-10-21/저장방법 및 유효기간(사용기간)변경 | YES      |            |
| narcoticKindCode | 마약종류코드      | string  |                                                                                              | YES      |            |
| newDrugClassName | 신약          | string  |                                                                                              | YES      |            |
| createdAt        | 생성일         | Long    | 1750041682000                                                                                | NO       |            |
| lastModifiedAt   | 최종 수정일      | Long    |                                                                                              | YES      |            |
|                  |             |         |                                                                                              |          |            |

#### Raw data mappings

| name               | model property name | nullable | comments                                             |
|--------------------|---------------------|----------|------------------------------------------------------|
| ITEM_SEQ           | identifier          | NO       |                                                      |
| CHART              | chart               | NO       |                                                      |
| MATERIAL_NAME      | materialName        | NO       | pipe(\|)로 구분된 형태                                     |
| STORAGE_METHOD     | storageMethod       | NO       |                                                      |
| VALID_TERM         | validTerm           | NO       |                                                      |
| PACK_UNIT          | packUnit            | NO       |                                                      |
| MAKE_MATERIAL_FLAG | makeMaterialFlag    | NO       | 완제/원료구분                                              |
| TOTAL_CONTENT      | totalContent        | NO       | 총량                                                   |
| EE_DOC_DATA        | effects             | NO       |                                                      |
| UD_DOC_DATA        | usage               | NO       |                                                      |
| NB_DOC_DATA        | cautions            | NO       |                                                      |
| MAIN_ITEM_INGR     | majorIngredients    | NO       | 아래의 형식을 따르며, comma로 구분된 형태<br>`[type_code]type_name` |
| INGR_NAME          | ingredients         | NO       | 아래의 형식을 따르며, comma로 구분된 형태<br>`[type_code]type_name` |
| RARE_DRUG_YN       | rareDrugYn          | NO       | 희귀의약품여부 (‘Y’, ’N’)                                   |
| ATC_CODE           | atcCode             | YES      | comma로 구분된 형태                                        |
| BAR_CODE           | barCode             | YES      |                                                      |
| CHANGE_DATE        | changeDate          | YES      | YYYYMMDD                                             |
| GBN_NAME           | changeHistories     | YES      | 변경이력                                                 |
| NARCOTIC_KIND_CODE | narcoticKindCode    | YES      | 마약종류코드                                               |
| NEWDRUG_CLASS_NAME | newDrugClassName    | YES      | 신약                                                   |
| CANCEL_DATE        | ?                   |          | 취하일자                                                 |
| CANCEL_NAME        | ?                   |          | 취하구분                                                 |
| CNSGN_MANUF        | ?                   |          | 위탁제조업체                                               |
| INSERT_FILE        | ?                   |          | 첨부문서                                                 |
| REEXAM_TARGET      |                     |          | 재심사대상                                                |
| REEXAM_DATE        |                     |          | 재심사기간                                                |
| PN_DOC_DATA        |                     |          | 주의사항(전문) 문서 데이터                                      |

#### Things to check

* 효능효과, 용법용량, 주의사항 항목이 nullable한 경우가 있는지 확인
