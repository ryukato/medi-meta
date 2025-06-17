package medi.master.core.domain.api.decorator

class SequentialDrugProductDecoratorChain<R>(
    private val decorators: List<DrugProductDecorator<R>>
): DrugProductDecoratorChain<R> {
    override suspend fun execute(initial: R): DrugDecorationContext<R> {
        var context = DrugDecorationContext(initial)
        for (decorator in decorators) {
            val currentFlags = context[DecorationKey.Flags] ?: mutableMapOf()
            currentFlags.putAll(decorator.flag())
            context = decorator.decorate(context)

            val updatedFlags = (context[DecorationKey.Flags] ?: mutableMapOf())
                .toMutableMap()
                .apply { putAll(decorator.flag()) }

            context = context.plus(DecorationKey.Flags to updatedFlags)

        }
        return context
    }
}

