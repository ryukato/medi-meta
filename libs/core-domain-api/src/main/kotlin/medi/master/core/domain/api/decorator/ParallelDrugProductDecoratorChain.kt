package medi.master.core.domain.api.decorator

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class ParallelDrugProductDecoratorChain<R>(
    private val decorators: List<DrugProductDecorator<R>>
) {
    suspend fun execute(initial: R): DrugDecorationContext<R> = coroutineScope {
        val initialCtx = DrugDecorationContext(initial)

        val results = decorators.map { decorator ->
            async {
                runCatching {
                    val updatedCtx = decorator.decorate(initialCtx)
                    val updatedFlags = (initialCtx[DecorationKey.Flags] ?: mutableMapOf())
                        .toMutableMap()
                        .apply { putAll(decorator.flag()) }

                    updatedCtx.plus(DecorationKey.Flags to updatedFlags)
                }
            }
        }.awaitAll()

        // 병합: 실패한 항목은 무시하고 성공한 컨텍스트만 merge
        results.filter { it.isSuccess }
            .map { it.getOrThrow() }
            .fold(initialCtx) { acc, ctx -> acc + ctx }
    }
}

