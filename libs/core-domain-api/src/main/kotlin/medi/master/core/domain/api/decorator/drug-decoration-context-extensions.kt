package medi.master.core.domain.api.decorator

fun <T, R> DrugDecorationContext<R>.getOrPut(
    key: DecorationKey<T>,
    default: () -> T
): Pair<T, DrugDecorationContext<R>> {
    val current = this[key] ?: default()
    return current to this.plus(key to current)
}

fun <R> DrugDecorationContext<R>.withUpdatedData(newData: R): DrugDecorationContext<R> =
    this.copy(data = newData)
