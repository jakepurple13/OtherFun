package itemsstuff.enigma

class Reflector(
    private var wiring: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
    private var name: String = "N/A",
    private var model: String = "N/A",
    private var date: String = "N/A",
    var state: Char = 'A'
) {

    fun encipher(key: Char): Char {
        val shift = (state) - 'A'
        val index = (((key - 'A' + 52) % 26) + shift + 52) % 26 // Actual connector hit
        return ('A' + (wiring[index] - 'A' + 52 - shift) % 26)
    }

    override fun toString(): String = "Reflector:\r\n\tName: {$name}\r\n\tModel: {$model}\r\n\tDate: {$date}\r\n\tWiring: {$wiring}"
}