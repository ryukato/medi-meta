# Field Mapping Grouped by API Origin


## 제품 허가 정보

| API Field | Model Class | Model Field |
|-----------|-------------|--------------|
| ITEM_SEQ | DrugProduct | itemSeq |
| ITEM_NAME | DrugProduct | productName |
| ITEM_ENG_NAME | DrugProduct | productEngName |
| ENTP_NAME | Manufacturer | name |
| ENTP_ENG_NAME | Manufacturer | nameEng |
| ENTP_SEQ | DrugProduct | manufacturerId |
| ENTP_NO | Manufacturer | code |
| ITEM_PERMIT_DATE | DrugProduct | permitDate |
| INDUTY | Manufacturer | type |
| PRDLST_STDR_CODE | DrugProduct | standardCode |
| SPCLTY_PBLC | DrugProduct | specialtyClass |
| PRDUCT_TYPE | DrugProduct | productType |
| PRDUCT_PRMISN_NO | DrugProduct | permitNumber |
| ITEM_INGR_NAME | DrugProduct | ingredientNames |
| ITEM_INGR_CNT | DrugProduct | ingredientCount |
| BIG_PRDT_IMG_URL | DrugImageInfo | imageUrl |
| PERMIT_KIND_CODE | DrugProduct | permitKindCode |
| CANCEL_DATE | DrugProduct | cancelDate |
| CANCEL_NAME | DrugProduct | cancelStatus |
| EDI_CODE | DrugProduct | ediCode |
| BIZRNO | Manufacturer | businessRegistrationNumber |

## 제품 상세정보

| API Field | Model Class | Model Field |
|-----------|-------------|--------------|
| CNSGN_MANUF | Manufacturer | contractManufacturer |
| ETC_OTC_CODE | DrugProduct | etcOtcCode |
| CHART | DrugProduct | chart |
| BAR_CODE | DrugProduct | barCodes |
| MATERIAL_NAME | ProductIngredient | ingredients |
| STORAGE_METHOD | DrugProduct | storageMethod |
| VALID_TERM | DrugProduct | validTerm |
| PACK_UNIT | DrugProduct | packUnit |
| MAKE_MATERIAL_FLAG | DrugProduct | productForm |
| CHANGE_DATE | DrugProduct | changeDate |
| GBN_NAME | DrugProduct | changeHistory |
| TOTAL_CONTENT | DrugProduct | totalContent |
| EE_DOC_DATA | DrugProduct | efficacyDoc |
| UD_DOC_DATA | DrugProduct | usageDoc |
| NB_DOC_DATA | DrugProduct | cautionDoc |
| MAIN_ITEM_INGR | DrugProduct | mainIngredientCodes |
| INGR_NAME | ProductIngredient | ingredientCodes |
| ATC_CODE | DrugProduct | atcCode |
| MAIN_INGR_ENG | DrugProduct | mainIngredientEng |
| RARE_DRUG_YN | DrugProduct | isRareDrug |

## 회수 판매중지

| API Field | Model Class | Model Field |
|-----------|-------------|--------------|
| RTRVL_RESN | RecallInfo | recallReason |
| RECALL_COMMAND_DATE | RecallInfo | recallDate |
| ENFRC_YN | RecallInfo | enforced |
| STD_CD | RecallInfo | productStdCodes |
| ENTRPS | Manufacturer | name |
| PRDUCT | DrugProduct | productName |
| ENTRPS_ADRES | Manufacturer | address |
| ENTRPS_TELNO | Manufacturer | phoneNumber |

## 낱알식별 정보

| API Field | Model Class | Model Field |
|-----------|-------------|--------------|
| CHART | DrugImageInfo | chart |
| PRINT_FRONT | DrugImageInfo | printFront |
| PRINT_BACK | DrugImageInfo | printBack |
| DRUG_SHAPE | DrugImageInfo | shape |
| COLOR_CLASS1 | DrugImageInfo | color1 |
| COLOR_CLASS2 | DrugImageInfo | color2 |
| LENG_LONG | DrugImageInfo | lengthLong |
| LENG_SHORT | DrugImageInfo | lengthShort |
| THICK | DrugImageInfo | thickness |
| MARK_CODE_FRONT | DrugImageInfo | markFront |
| MARK_CODE_BACK | DrugImageInfo | markBack |
| ITEM_IMAGE | DrugImageInfo | imageUrl |

## e약은요

| API Field | Model Class | Model Field |
|-----------|-------------|--------------|
| EFCY_QESITM | EasyDrugInfo | efficacy |
| USEMETHOD_QESITM | EasyDrugInfo | usage |
| ATPN_QESITM | EasyDrugInfo | warning |
| INTRC_QESITM | EasyDrugInfo | interaction |
| SE_QESITM | EasyDrugInfo | sideEffect |
| DEPOSITMETHOD_QESITM | EasyDrugInfo | storageMethod |
