package rmg.apps.cards.backend

import rmg.apps.cards.base.model.AbstractSignifiedRepositoryUnitTest

/**
 * Unit tests for [InMemorySignifiedRepository] based on [AbstractSignifiedRepositoryUnitTest]
 */
class InMemorySignifiedRepositoryUnitTest : AbstractSignifiedRepositoryUnitTest<Int, InMemorySignifiedRepository>() {
    override fun createInstance(): InMemorySignifiedRepository = InMemorySignifiedRepository()
    override fun getFirstId(): Int = 0
}
