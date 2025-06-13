# # Domain-Model
## Medical Product
### Simple Model

| name                       | description | type    | sample                                                       | comments |
|----------------------------|-------------|---------|--------------------------------------------------------------|----------|
| businessRegistrationNumber | 사업자등록번호     | string  | 1188102477                                                   |          |
| itemSequenceCode           | 품목기준코드      | string  | 195500005                                                    |          |
| itemName                   | 품목명         | string  | 중외5%포도당생리식염액(수출명:5%DextroseinnormalsalineInj.)               |          |
| itemEnglishName            | 영문제품명       | string  | Choongwae 5% Dextrose In Normal Saline Inj."                 |          |
| companyName                | 업체명         | string  | 국제약품(주)                                                      |          |
| companyEnglishName         | 영문업체명       | string  | KUKJE pharmaceutical co., ltd                                |          |
| companySequenceNumber      | 업일련번호       | string  | 19620006                                                     |          |
| businessLicenseNumber      | 업허가번호       | string  | 1419                                                         |          |
| itemApprovalDate           | 품목허가일자      | string  | 19781121                                                     |          |
| businessType               | 업종          | string  | 의약품                                                          |          |
| productSerialNumber        | 품목일련번호      | string  | 197800085                                                    |          |
| specialtyOrGeneralDivision | 전문/일반구분     | string  | 전문의약품                                                        |          |
| productClassificationName  | 분류명         | string  | [02320]소화성궤양용제                                               |          |
| itemApprovalNumber         | 품목허가번호      | string  | 153                                                          |          |
| mainIngredientName         | 주성분         | string  | Cimetidine                                                   |          |
| mainIngredientCount        | 주성분수        | string  | 1                                                            |          |
| largeProductImageUrl       | 큰제품이미지      | string? | https://nedrug.mfds.go.kr/pbp/cmn/itemImageDownload/147427336539800172 |          |
| approvalTypeCode           | 신고/허가구분     | string  | 신고                                                           |          |
| cancellationDate           | 취하일자        | string  | ?                                                            |          |
| cancellationType           | 취하구분        | string  | 정상                                                           |          |
| insuranceCode              | 보험코드        | string? | 644902212,644902220                                          |          |

### Detailed Model
| name               | description              | type   | sample | comments |
|--------------------|---------------------------|--------|--------|----------|
| itemSeq            | 품목기준코드              | string | 195500005 |          |
| cnsgnManuf         | 위탁제조업체              | string | 제이더블유생명과학(주) |          |
| chart              | 성상                      | string | 폴리프로필렌, 다층필름플라스틱용기에 든 무색 투명한 액. |          |
| barCode            | 표준코드                  | string | 8806449022106,8806449022113,... |          |
| materialName       | 원료성분                  | string | 총량 : 1000밀리리터\|성분명 : 포도당... |          |
| eeDocId            | 효능효과                  | string | https://nedrug.mfds.go.kr/pbp/cmn/pdfDownload/195500005/EE |          |
| udDocId            | 용법용량                  | string | https://nedrug.mfds.go.kr/pbp/cmn/pdfDownload/195500005/UD |          |
| nbDocId            | 주의사항                  | string | https://nedrug.mfds.go.kr/pbp/cmn/pdfDownload/195500005/NB |          |
| insertFile         | 첨부문서                  | string |         |          |
| storageMethod      | 저장방법                  | string | 밀봉용기 |          |
| validTerm          | 유효기간                  | string | 제조일로부터 36 개월 |          |
| reExamTarget       | 재심사대상                | string |         |          |
| reExamDate         | 재심사기간                | string |         |          |
| packUnit           | 포장단위                  | string | 500, 1000밀리리터 |          |
| makeMaterialFlag   | 완제/원료구분             | string | 완제의약품 |          |
| newDrugClassName   | 신약                      | string |         |          |
| changeDate         | 변경일자                  | string | 20210827 |          |
| narcoticKindCode   | 마약종류코드              | string |         |          |
| gbnName            | 변경이력                  | string | 저장방법 및 사용(유효)기간, 2021-08-27... |          |
| totalContent       | 총량                      | string | 1000 |          |
| eeDocData          | 효능효과 문서 데이터       | string | ○ 탈수증, 수술전후 등의 수분ㆍ전해질 보급 |          |
| udDocData          | 용법용량 문서 데이터       | string | 보통 성인 1일 500～1,000mL... |          |
| nbDocData          | 주의사항(일반) 문서 데이터 | string | 포도당 함유제제를 정맥주사하는 환자는... |          |
| pnDocData          | 주의사항(전문) 문서 데이터 | string |         |          |
| mainItemIngr       | 유효성분                  | string | [M040702]포도당\|[M040426]염화나트륨 |          |
| ingrName           | 첨가제                    | string | [M040534]주사용수 |          |
| atcCode            | ATC코드                   | string | B05BB02 |          |
| mainIngrEng        | 주성분영문명              | string | Glucose/Sodium Chloride |          |
| rareDrugYn         | 희귀의약품여부            | string |         |          |

