package medi.master.core.domain.api.decorator

interface DrugProductDecorator<R> {
    suspend fun decorate(ctx: DrugDecorationContext<R>): DrugDecorationContext<R>
    suspend fun flag(): Map<String, Boolean>
}
