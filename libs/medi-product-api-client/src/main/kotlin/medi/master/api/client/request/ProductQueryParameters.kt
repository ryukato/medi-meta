package medi.master.api.client.request

import medi.master.webclient.encodeQueryParameter
import medi.master.webclient.toMultiValueMap
import org.springframework.util.MultiValueMap


data class ProductQueryParameters(
    val pageNo: Int = 1,
    val pageSize: Int = 100,
    val name: String? = null,
    val manufacturerName: String? = null,
) {
    fun toQueryParameterMap(): MultiValueMap<String, String> {
        val mutableMap = mutableMapOf<String, String>()
        mutableMap["pageNo"] = pageNo.toString()
        mutableMap["numOfRows"] = pageSize.toString()
        name?.run {
            mutableMap["item_name"] = encodeQueryParameter()
        }
        manufacturerName?.run {
            mutableMap["entp_name"] = encodeQueryParameter()
        }
        return mutableMap.toMultiValueMap()
    }
}
