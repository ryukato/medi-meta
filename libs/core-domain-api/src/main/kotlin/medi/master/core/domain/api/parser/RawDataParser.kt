package medi.master.core.domain.api.parser

interface RawDataParser<I, R> {
    /**
     * Parses the raw data of type [I] into a result of type [R].
     *
     * @param input The raw data to be parsed.
     * @return The parsed result.
     */
    fun parse(input: I): R
}
