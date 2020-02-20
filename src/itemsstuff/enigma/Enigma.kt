package itemsstuff.enigma

class Enigma(
    private val reflector: Reflector,
    private val rotor1: Rotor,
    private val rotor2: Rotor,
    private val rotor3: Rotor,
    key: String = "AAA",
    plugs: String = ""
) {
    constructor(
        reflector: PreMadeReflector,
        rotor1: PreMadeRotor,
        rotor2: PreMadeRotor,
        rotor3: PreMadeRotor,
        key: String = "AAA",
        plugs: String = ""
    ) : this(reflector.getReflector(), rotor1.getRotor(), rotor2.getRotor(), rotor3.getRotor(), key, plugs)

    private val transTab = mutableMapOf<Char, Char>()

    init {
        rotor1.state = key[0]
        rotor2.state = key[1]
        rotor3.state = key[2]
        reflector.state = 'A'

        for (plugPair in plugs.split(" ")) {
            if (plugPair.length != 2) continue
            val k = plugPair[0]
            val v = plugPair[1]
            transTab[k] = v
            transTab[v] = k
        }
    }

    fun encode(plainTextIn: String): String {
        var ciphering = ""
        val plainText: String = plainTextIn.toUpperCase().map { transTab.getOrElse(it) { it } }.joinToString("")
        for (c in plainText) {
            if (rotor2.isTurnoverPos) rotor2.notch().also { rotor3.notch() }
            if (rotor1.isTurnoverPos) rotor2.notch()
            rotor1.notch()
            if (!c.isLetter()) {
                ciphering += c
                continue
            }
            ciphering += c
                .let { rotor1.encodeRight(it) }
                .let { rotor2.encodeRight(it) }
                .let { rotor3.encodeRight(it) }
                .let { reflector.encipher(it) }
                .let { rotor3.encodeLeft(it) }
                .let { rotor2.encodeLeft(it) }
                .let { rotor1.encodeLeft(it) }
        }
        return ciphering.map { transTab.getOrElse(it) { it } }.joinToString("")
    }

    override fun toString(): String =
        "Enigma machine.\r\n\r\n" +
                "Reflector: {$reflector}\r\n\r\n" +
                "Rotor 1: {$rotor1}\r\n\r\n" +
                "Rotor 2: {$rotor2}\r\n\r\n" +
                "Rotor 3: {$rotor3}\r\n\r\n"

}