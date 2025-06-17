package medi.master.core.domain.api.decorator

import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import medi.master.core.domain.medical.product.model.DrugMaster
import medi.master.core.domain.medical.product.model.PricingInfo
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class DrugProductDecoratorChainTest {
    @Test
    fun testExecuteByParallelDecoratorChain() = runBlocking {
        val initialDrugMaster = DrugMaster.createEmpty()
        val pricingInfo = PricingInfo(
            id = 1,
            productItemSequence = "12345",
            unitPrice = 100,
            paymentType = "CASH"
        )

        val chain = ParallelDrugProductDecoratorChain(
            listOf(
                PricingInfoDecorator(pricingInfo),
            )
        )
        val resultContext = chain.execute(initialDrugMaster)
        val finalMaster = resultContext.data
        assertNotNull(finalMaster.pricing)
        assertEquals(pricingInfo.unitPrice, finalMaster.pricing!!.unitPrice)
        assertEquals(pricingInfo.productItemSequence, finalMaster.pricing!!.productItemSequence)
        assertEquals(pricingInfo.paymentType, finalMaster.pricing!!.paymentType)

        assertEquals(true, resultContext[DecorationKey.Flags]!!["pricingFetched"])
        println(resultContext[DecorationKey.Log]!!.joinToString("\n"))
    }

    @Test
    fun testExecuteBySequentialDecoratorChain() = runBlocking {
        val initialDrugMaster = DrugMaster.createEmpty()
        val pricingInfo = PricingInfo(
            id = 1,
            productItemSequence = "12345",
            unitPrice = 100,
            paymentType = "CASH"
        )

        val chain = SequentialDrugProductDecoratorChain(
            listOf(
                PricingInfoDecorator(pricingInfo),
            )
        )
        val resultContext = chain.execute(initialDrugMaster)
        val finalMaster = resultContext.data
        assertNotNull(finalMaster.pricing)
        assertEquals(pricingInfo.unitPrice, finalMaster.pricing!!.unitPrice)
        assertEquals(pricingInfo.productItemSequence, finalMaster.pricing!!.productItemSequence)
        assertEquals(pricingInfo.paymentType, finalMaster.pricing!!.paymentType)

        assertEquals(true, resultContext[DecorationKey.Flags]!!["pricingFetched"])
        println(resultContext[DecorationKey.Log]!!.joinToString("\n"))
    }
}

class PricingInfoDecorator(val pricingInfo: PricingInfo): DrugProductDecorator<DrugMaster> {
    override suspend fun decorate(ctx: DrugDecorationContext<DrugMaster>): DrugDecorationContext<DrugMaster> {
        val log = ctx[DecorationKey.Log] ?: mutableListOf()
        log.add("Executing decorator: ${this::class.simpleName} at ${System.currentTimeMillis()}")
        val updatedData = ctx.data.copy(pricing = pricingInfo)

        log.add("Decorator ${this::class.simpleName} completed at ${System.currentTimeMillis()}")
        return ctx.withUpdatedData(updatedData).plus(Pair(DecorationKey.Log, log))
    }

    override suspend fun flag(): Map<String, Boolean> {
        return mapOf("pricingFetched" to true)
    }
}
