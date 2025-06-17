package medi.master.core.domain.api.decorator

interface DrugProductDecoratorChain<R> {
    suspend fun execute(initial: R): DrugDecorationContext<R>
}
