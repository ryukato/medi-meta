package medi.master.core.domain.api.decorator

data class DrugDecorationContext<R>(
    val data: R,
    private val attributes: Map<DecorationKey<*>, Any?> = emptyMap()
) {
    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: DecorationKey<T>): T? =
        attributes[key] as? T

    operator fun <T> plus(pair: Pair<DecorationKey<T>, T?>): DrugDecorationContext<R> =
        this.copy(attributes = attributes + (pair.first to pair.second))

    operator fun plus(other: DrugDecorationContext<R>): DrugDecorationContext<R> {
        return this.copy(
            data = other.data,
            attributes = this.attributes + other.attributes
        )
    }

    fun keys(): Set<DecorationKey<*>> = attributes.keys
}
