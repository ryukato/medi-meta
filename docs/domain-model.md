# Domain-Model

## Medical Product

### Simple Model

| name                       | description | type    | sample                                                                 | comments |
|----------------------------|-------------|---------|------------------------------------------------------------------------|----------|
| businessRegistrationNumber | 사업자등록번호     | string  | 1188102477                                                             |          |
| itemSequenceCode           | 품목기준코드      | string  | 195500005                                                              |          |
| itemName                   | 품목명         | string  | 중외5%포도당생리식염액(수출명:5%DextroseinnormalsalineInj.)                         |          |
| itemEnglishName            | 영문제품명       | string  | Choongwae 5% Dextrose In Normal Saline Inj."                           |          |
| companyName                | 업체명         | string  | 국제약품(주)                                                                |          |
| companyEnglishName         | 영문업체명       | string  | KUKJE pharmaceutical co., ltd                                          |          |
| companySequenceNumber      | 업일련번호       | string  | 19620006                                                               |          |
| businessLicenseNumber      | 업허가번호       | string  | 1419                                                                   |          |
| itemApprovalDate           | 품목허가일자      | string  | 19781121                                                               |          |
| businessType               | 업종          | string  | 의약품                                                                    |          |
| productSerialNumber        | 품목일련번호      | string  | 197800085                                                              |          |
| specialtyOrGeneralDivision | 전문/일반구분     | string  | 전문의약품                                                                  |          |
| productClassificationName  | 분류명         | string  | [02320]소화성궤양용제                                                         |          |
| itemApprovalNumber         | 품목허가번호      | string  | 153                                                                    |          |
| mainIngredientName         | 주성분         | string  | Cimetidine                                                             |          |
| mainIngredientCount        | 주성분수        | string  | 1                                                                      |          |
| largeProductImageUrl       | 큰제품이미지      | string? | https://nedrug.mfds.go.kr/pbp/cmn/itemImageDownload/147427336539800172 |          |
| approvalTypeCode           | 신고/허가구분     | string  | 신고                                                                     |          |
| cancellationDate           | 취하일자        | string  | ?                                                                      |          |
| cancellationType           | 취하구분        | string  | 정상                                                                     |          |
| insuranceCode              | 보험코드        | string? | 644902212,644902220                                                    |          |

### Detailed Model

| name             | description     | type   | sample                                                     | comments |
|------------------|-----------------|--------|------------------------------------------------------------|----------|
| itemSeq          | 품목기준코드          | string | 195500005                                                  |          |
| cnsgnManuf       | 위탁제조업체          | string | 제이더블유생명과학(주)                                               |          |
| chart            | 성상              | string | 폴리프로필렌, 다층필름플라스틱용기에 든 무색 투명한 액.                            |          |
| barCode          | 표준코드            | string | 8806449022106,8806449022113,...                            |          |
| materialName     | 원료성분            | string | 총량 : 1000밀리리터\|성분명 : 포도당...                                |          |
| eeDocId          | 효능효과            | string | https://nedrug.mfds.go.kr/pbp/cmn/pdfDownload/195500005/EE |          |
| udDocId          | 용법용량            | string | https://nedrug.mfds.go.kr/pbp/cmn/pdfDownload/195500005/UD |          |
| nbDocId          | 주의사항            | string | https://nedrug.mfds.go.kr/pbp/cmn/pdfDownload/195500005/NB |          |
| insertFile       | 첨부문서            | string |                                                            |          |
| storageMethod    | 저장방법            | string | 밀봉용기                                                       |          |
| validTerm        | 유효기간            | string | 제조일로부터 36 개월                                               |          |
| reExamTarget     | 재심사대상           | string |                                                            |          |
| reExamDate       | 재심사기간           | string |                                                            |          |
| packUnit         | 포장단위            | string | 500, 1000밀리리터                                              |          |
| makeMaterialFlag | 완제/원료구분         | string | 완제의약품                                                      |          |
| newDrugClassName | 신약              | string |                                                            |          |
| changeDate       | 변경일자            | string | 20210827                                                   |          |
| narcoticKindCode | 마약종류코드          | string |                                                            |          |
| gbnName          | 변경이력            | string | 저장방법 및 사용(유효)기간, 2021-08-27...                             |          |
| totalContent     | 총량              | string | 1000                                                       |          |
| eeDocData        | 효능효과 문서 데이터     | string | ○ 탈수증, 수술전후 등의 수분ㆍ전해질 보급                                   |          |
| udDocData        | 용법용량 문서 데이터     | string | 보통 성인 1일 500～1,000mL...                                    |          |
| nbDocData        | 주의사항(일반) 문서 데이터 | string | 포도당 함유제제를 정맥주사하는 환자는...                                    |          |
| pnDocData        | 주의사항(전문) 문서 데이터 | string |                                                            |          |
| mainItemIngr     | 유효성분            | string | [M040702]포도당\|[M040426]염화나트륨                               |          |
| ingrName         | 첨가제             | string | [M040534]주사용수                                              |          |
| atcCode          | ATC코드           | string | B05BB02                                                    |          |
| mainIngrEng      | 주성분영문명          | string | Glucose/Sodium Chloride                                    |          |
| rareDrugYn       | 희귀의약품여부         | string |                                                            |          |

### Bundle Medical Product

| name                  | description | type   | sample                | comments                   |
|-----------------------|-------------|--------|-----------------------|----------------------------|
| itemSeq               | 대표 품목기준코드   | string | 196900027             | "의약품 제품 허가 정보"의 "ITEM_SEQ" |
| manufacturerPlaceName | 대표 제조소      | string | 대한약품공업(주) 안산1공장 제 1공장 |                            |
| hiraMainingrCode      | 대표 심평원주성분코드 | string | 248830BIJ             |                            |
| hiraPrductCode        | 대표 심평원제품코드  | string | 645101681             |                            |

### Drug Pricing Standard Information (약가기준정보)
| name                  | description       | type    | sample | comments |
|-----------------------|------------------|---------|--------|----------|
| adtStaDd              | 적용시작일자       | string  |        |          |
| chgAfMdsCd            | 변경이후약품코드    | string  |        |          |
| chgBfMdsCd            | 변경이전약품코드    | string  |        |          |
| expTpTxt1             | 예외구분1         | string  |        |          |
| expTpTxt2             | 예외구분2         | string  |        |          |
| expTpTxt3             | 예외구분3         | string  |        |          |
| gnlNmCd               | 일반명코드         | string  |        |          |
| injcPthNm             | 투여경로명         | string  |        |          |
| itmNm                 | 품목명            | string  |        |          |
| lprcEssAddcCuprc      | 저가필수가산산출단가 | string  |        |          |
| lprcEssTpNm           | 저가필수구분명      | string  |        |          |
| mdsCd                 | 약품코드           | string  |        |          |
| meftDivNo             | 약효분류번호        | string  |        |          |
| mnfEntpNm             | 제조업체명         | string  |        |          |
| mxCprc                | 상한가            | string  |        |          |
| nomNm                 | 규격명            | string  |        |          |
| optCpmdImplTpNm       | 임의조제불가능구분명 | string  |        |          |
| payTpNm               | 급여구분명         | string  |        |          |
| sbstPsblTpNm          | 대체가능구분명      | string  |        |          |
| sellEptDd             | 판매예정일자        | string  |        |          |
| spcGnlTpNm            | 전문일반구분명      | string  |        |          |
| unit                  | 단위             | string  |        |          |

### Recalled and Suspended Drugs(회수 판매중지) 
#### List item
| name                 | description       | type    | sample | comments |
|----------------------|------------------|---------|--------|----------|
| prduct               | 품목명            | string  |        |          |
| entrps               | 업체명            | string  |        |          |
| rtrvlResn            | 회수사유내용       | string  |        |          |
| enfrcYn              | 구분(강제여부)     | string  |        |          |
| rtrvlCmmndDt         | 승인일자          | string  |        |          |
| recallCommandDate    | 회수명령일자       | string  |        |          |
| itemSeq              | 품목기준코드       | string  |        |          |
| bizrno               | 사업자등록번호     | string  |        |          |
| stdCd                | 표준코드          | string  |        |          |

#### Details item
| name                 | description         | type    | sample | comments |
|----------------------|----------------------|---------|--------|----------|
| entrps               | 업체명                | string  |        |          |
| entrpsAdres          | 공장주소              | string  |        |          |
| entrpsTelno          | 업체담당자전화번호       | string  |        |          |
| prduct               | 품목명                | string  |        |          |
| rtrvlResn            | 회수사유내용           | string  |        |          |
| mnfcturNo            | 제조번호              | string  |        |          |
| mnfcturDt            | 제조일자              | string  |        |          |
| usgpd                | 유효기간              | string  |        |          |
| packngUnit           | 포장단위              | string  |        |          |
| rtrvlCmmndDt         | 승인일자              | string  |        |          |
| rm                   | 비고                 | string  |        |          |
| recallCommandDate    | 회수명령일자           | string  |        |          |
| openEndDate          | 공개마감일            | string  |        |          |
| bizrno               | 사업자등록번호         | string  |        |          |
| stdCd                | 표준코드              | string  |        |          |
| itemSeq              | 품목기준코드           | string  |        |          |

### Drug Pill Identification Information (의약품 낱알식별 정보)
| name                      | description     | type   | sample | comments |
|---------------------------|------------------|--------|--------|----------|
| itemSeq                   | 품목일련번호         | string |        |          |
| itemName                  | 품목명             | string |        |          |
| entpSeq                   | 업체일련번호         | string |        |          |
| entpName                  | 업체명             | string |        |          |
| chart                     | 성상              | string |        |          |
| itemImage                 | 큰제품이미지         | string |        |          |
| printFront                | 표시(앞)           | string |        |          |
| printBack                 | 표시(뒤)           | string |        |          |
| drugShape                 | 의약품모양           | string |        |          |
| colorClass1               | 색깔(앞)           | string |        |          |
| colorClass2               | 색깔(뒤)           | string |        |          |
| lineFront                 | 분할선(앞)          | string |        |          |
| lineBack                  | 분할선(뒤)          | string |        |          |
| lengLong                  | 크기(장축)          | string |        |          |
| lengShort                 | 크기(단축)          | string |        |          |
| thick                     | 크기(두께)          | string |        |          |
| imgRegistTs               | 약학정보원 이미지 생성일 | string |        |          |
| classNo                   | 분류번호            | string |        |          |
| className                 | 분류명              | string |        |          |
| etcOtcName                | 전문/일반           | string |        |          |
| itemPermitDate            | 품목허가일자         | string |        |          |
| formCodeName              | 제형코드이름         | string |        |          |
| markCodeFrontAnal         | 마크내용(앞)         | string |        |          |
| markCodeBackAnal          | 마크내용(뒤)         | string |        |          |
| markCodeFrontImg          | 마크이미지(앞)       | string |        |          |
| markCodeBackImg           | 마크이미지(뒤)       | string |        |          |
| itemEngName               | 품목영문명           | string |        |          |
| changeDate                | 변경일              | string |        |          |
| markCodeFront             | 마크코드(앞)         | string |        |          |
| markCodeBack              | 마크코드(뒤)         | string |        |          |
| ediCode                   | 보험코드            | string |        |          |
| bizrno                    | 사업자등록번호        | string |        |          |
| stdCd                     | 표준코드            | string |        |          |


