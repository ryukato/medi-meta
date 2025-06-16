package medi.master.core.domain.api.parser

import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory
import medi.master.core.domain.medical.product.model.MedicalItemArticleData
import org.w3c.dom.Document
import org.w3c.dom.Element

class MedicalDetailItemContentsParser: RawDataParser<String, Set<MedicalItemArticleData>> {
    override fun parse(input: String): Set<MedicalItemArticleData> {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()

        // 이스케이프 제거
        val cleanedXml = input.replace("\\\"", "\"")
        val inputStream = ByteArrayInputStream(cleanedXml.toByteArray(Charsets.UTF_8))
        val document = builder.parse(inputStream)

        return getParsedArticles(document)
    }



    private fun getParsedArticles(document: Document): Set<MedicalItemArticleData>{
        val documentTitle = (document.getElementsByTagName("DOC").item(0) as Element).getAttribute("title") ?: ""
        val result = mutableListOf<MedicalItemArticleData>()
        val articleNodes = document.getElementsByTagName("ARTICLE")

        for (i in 0 until articleNodes.length) {
            val articleElement = articleNodes.item(i) as Element
            val title = articleElement.getAttribute("title") ?: ""

            val paragraphs = getParsedParagraphs(articleElement)
            if (title.isBlank()) {
                result.add(MedicalItemArticleData(documentTitle, paragraphs))
            } else {
                result.add(MedicalItemArticleData(title, paragraphs))
            }
        }
        return result.toSet()
    }

    private fun getParsedParagraphs(articleElement: Element): MutableList<String> {
        val paragraphNodes = articleElement.getElementsByTagName("PARAGRAPH")
        val paragraphs = mutableListOf<String>()
        for (j in 0 until paragraphNodes.length) {
            val paraNode = paragraphNodes.item(j)
            val cdataNode = paraNode.firstChild
            if (cdataNode != null) {
                val text = cdataNode.nodeValue.trim()
                if (text.isNotEmpty()) {
                    paragraphs.add(text)
                }
            }
        }
        return paragraphs
    }
}
