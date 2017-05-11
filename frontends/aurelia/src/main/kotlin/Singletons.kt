import rmg.apps.cards.backend.InMemorySignifiedRepository
import rmg.apps.cards.base.SignifiedRepository

@JsName("signifiedRepository")
val signifiedRepository: SignifiedRepository<*, *> = InMemorySignifiedRepository()

// TODO(rmgrimm): Add a dsl for adding signifieds into a repository
// TODO(rmgrimm): Populate the signified repository
