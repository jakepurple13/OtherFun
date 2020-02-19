package itemsstuff

class OtherSequenceMaker<T>(sequence: List<T>, sequenceAchieved: () -> Unit) : SequenceMaker<T>(sequence, sequenceAchieved) {
    private var nextItemListener: (T) -> Unit = {}
    fun nextItem(block: (T) -> Unit) = apply { nextItemListener = block }
    override fun nextItem(item: T) = nextItemListener(item)
}

class GuessingGame<T>(password: Iterable<T>, sequenceAchieved: () -> Unit = {}) {
    private val sequence = OtherSequenceMaker(password.toList()) { sequenceAchieved().also { stillPlaying = false } }
    private val state = GameState()
    private var stillPlaying = true
    private var prompt: () -> Unit = {}
    private var endGame: (List<T>) -> Unit = {}
    private var playingState: State = State.Play
    private var readInPrompt: (GameState.() -> T)? = null
    fun stopGame() = state.stopGame()
    fun startGame() = state.startGame()
    fun nextItem(item: (T) -> Unit) = apply { sequence.nextItem(item) }
    fun sequenceReset(block: () -> Unit) = apply { sequence.sequenceReset(block) }
    fun prompt(block: () -> Unit) = apply { prompt = block }
    fun endGame(block: (List<T>) -> Unit) = apply { endGame = block }
    fun readIn(block: GameState.() -> T) = apply { readInPrompt = block }
    fun start() = Unit.apply { readInPrompt?.let { start(it) } }
    fun start(readIn: GameState.() -> T) {
        while (stillPlaying && playingState == State.Play) {
            prompt()
            val item = state.readIn()
            sequence += if (playingState == State.Stop) break else item
        }
        endGame(sequence.correctSequence())
    }

    sealed class State {
        object Play : State()
        object Stop : State()
    }

    inner class GameState {
        fun stopGame() = run { playingState = State.Stop }.let { null }
        fun startGame() = run { playingState = State.Play }.let { null }
    }
}

@DslMarker
annotation class GameMarker

@GameMarker
class GuessingGameBuilder<T> private constructor() {
    private var readInPrompt: (GuessingGame<T>.GameState.() -> T)? = null

    @GameMarker
    fun readIn(block: GuessingGame<T>.GameState.() -> T) {
        readInPrompt = block
    }

    private var nextItem: (T) -> Unit = {}

    @GameMarker
    fun nextItem(item: (T) -> Unit) {
        nextItem = item
    }

    private var reset: () -> Unit = {}

    @GameMarker
    fun sequenceReset(block: () -> Unit) {
        reset = block
    }

    private var prompt: () -> Unit = {}

    @GameMarker
    fun prompt(block: () -> Unit) {
        prompt = block
    }

    private var endGame: (List<T>) -> Unit = {}

    @GameMarker
    fun endGame(block: (List<T>) -> Unit) {
        endGame = block
    }

    private var victory: () -> Unit = {}

    @GameMarker
    fun victory(block: () -> Unit) {
        victory = block
    }

    private val list = mutableListOf<T>()

    @GameMarker
    fun addItem(vararg items: T) = list.addAll(items)

    @GameMarker
    fun addItems(items: Iterable<T>) = list.addAll(items)

    private fun build() = GuessingGame(list, victory)
        .prompt(prompt)
        .nextItem(nextItem)
        .sequenceReset(reset)
        .endGame(endGame)
        .apply { readInPrompt?.let { readIn(it) } }

    companion object {
        @GameMarker
        operator fun <T> invoke(block: GuessingGameBuilder<T>.() -> Unit) = GuessingGameBuilder<T>().apply(block).build()
    }
}
