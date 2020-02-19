package itemsstuff.enigma

class Enigma(
    private val reflector: Reflector,
    private val rotor1: Rotor,
    private val rotor2: Rotor,
    private val rotor3: Rotor,
    key: String = "AAA",
    plugs: String = ""
) {

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
        var plainText = plainTextIn.toUpperCase()
        var plainTextTemp = ""

        plainText.forEach { plainTextTemp += if(transTab.containsKey(it)) transTab[it] else it }
        plainText = plainTextTemp
        for (c in plainText) {
            if (rotor2.isTurnoverPos) {
                rotor2.notch()
                rotor3.notch()
            }

            if (rotor1.isTurnoverPos) rotor2.notch()

            rotor1.notch()

            if (!c.isLetter()) {
                ciphering += c
                continue
            }

            var t = rotor1.encodeRight(c)
            t = rotor2.encodeRight(t)
            t = rotor3.encodeRight(t)
            t = reflector.encipher(t)
            t = rotor3.encodeLeft(t)
            t = rotor2.encodeLeft(t)
            t = rotor1.encodeLeft(t)
            ciphering += t
        }

        var cipheringTmp = ""
        ciphering.forEach { cipheringTmp += if(transTab.containsKey(it)) transTab[it] else it }
        return cipheringTmp

    }

    override fun toString(): String =
        "Enigma machine.\r\n\r\n" +
                "Reflector: {$reflector}\r\n\r\n" +
                "Rotor 1: {$rotor1}\r\n\r\n" +
                "Rotor 2: {$rotor2}\r\n\r\n" +
                "Rotor 3: {$rotor3}\r\n\r\n"

}