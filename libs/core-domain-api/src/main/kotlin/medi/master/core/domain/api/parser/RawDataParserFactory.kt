package medi.master.core.domain.api.parser

import medi.master.core.domain.medical.constants.ProductSourceTypes

interface RawDataParserFactory {
    /**
     * Creates a [RawDataParser] instance for the specified input and result types.
     *
     * @param I The type of the input data to be parsed.
     * @param R The type of the result after parsing.
     * @return An instance of [RawDataParser] that can parse data of type [I] into type [R].
     */
    fun <I, R> createParser(sourceTypes: ProductSourceTypes): RawDataParser<I, R>
}
