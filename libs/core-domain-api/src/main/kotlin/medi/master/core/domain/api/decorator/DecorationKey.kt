package medi.master.core.domain.api.decorator

sealed interface DecorationKey<T> {
    data object Log : DecorationKey<MutableList<String>>
    data object Flags : DecorationKey<MutableMap<String, Boolean>>
    data class Custom<T>(val name: String) : DecorationKey<T>
}
