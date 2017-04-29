package rmg.apps.cards.backend

import rmg.apps.cards.base.model.AbstractMutableSignifiedRepositoryUnitTest

/**
 * Unit tests for [InMemorySignifiedRepository] based on [AbstractMutableSignifiedRepositoryUnitTest]
 */
class InMemorySignifiedRepositoryUnitTest : AbstractMutableSignifiedRepositoryUnitTest<Int, Unit, InMemorySignifiedRepository>() {
    override fun createInstance(): InMemorySignifiedRepository = InMemorySignifiedRepository()
    override fun getFirstId(): Int = 0
    override fun getUserId(): Unit = Unit
}
