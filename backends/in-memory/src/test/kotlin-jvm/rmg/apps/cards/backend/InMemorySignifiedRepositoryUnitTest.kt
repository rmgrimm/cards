package rmg.apps.cards.backend

import rmg.apps.cards.base.model.AbstractSignifiedRepositoryUnitTest

/**
 * Unit tests for [InMemorySignifiedRepository] based on [AbstractSignifiedRepositoryUnitTest]
 */
class InMemorySignifiedRepositoryUnitTest : AbstractSignifiedRepositoryUnitTest<Int, Unit, InMemorySignifiedRepository>() {
    override fun createInstance(): InMemorySignifiedRepository = InMemorySignifiedRepository()
    override fun getFirstId(): Int = 0
    override fun getUserId(): Unit = Unit
}
