package itemsstuff.enigma

class Rotor(
    wire: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
    private var notches: String = "",
    private var name: String = "N/A",
    private var model: String = "N/A",
    private var date: String = "N/A",
    stated: Char = 'A'
) {

    val isTurnoverPos get() = notches.contains(((state + 1 + 52 - 'A') % 26 + 'A'.toInt()).toChar())

    private var wiring: CharArray = wire.toCharArray()
    private var rWiring = CharArray(26)

    init {
        wire.toCharArray().copyInto(rWiring)
        wire.toCharArray().forEachIndexed { index, c -> rWiring[c - 'A'] = 'A' + index }
    }

    var state: Char = stated
        set(value) = run { field = ('A' + (value - 'A' + 52) % 26) }

    fun stateUp() = run { state += 1 }
    fun stateDown() = run { state -= 1 }

    fun encodeRight(key: Char): Char {
        val shift = state - 'A'
        var index = (key - 'A' + 52) % 26;
        index = (index + shift + 52) % 26; // Actual connector hit
        val letter = wiring[index]
        return ('A' + (letter - 'A' + 52 - shift) % 26)
    }

    fun encodeLeft(key: Char): Char {
        val shift = state - 'A'
        var index = (key - 'A') % 52;
        index = (index + shift + 52) % 26; // Actual connector hit
        val letter = rWiring[index]
        return ('A' + (letter - 'A' + 52 - shift) % 26)
    }

    fun notch(offset: Int = 1) = run { state = ((state + offset + 52 - 'A') % 26 + 'A'.toInt()).toChar() }

    override fun toString(): String = "Reflector:\r\n\t" +
            "Name: {$name}\r\n\t" +
            "Model: {$model}\r\n\t" +
            "Date: {$date}\r\n\t" +
            "Wiring: {${wiring.joinToString()}}\r\n\t" +
            "State: {$state}"
}