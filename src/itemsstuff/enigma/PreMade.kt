package itemsstuff.enigma

enum class PreMadeRotor(
    val wire: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
    val notches: String = "",
    val named: String = "N/A",
    val model: String = "N/A",
    val date: String = "N/A"
) {
    IC(wire = "DMTWSILRUYQNKFEJCAZBPGXOHV", named = "IC", model = "Commercial Enigma A, B", date = "1924"),
    IIC(wire = "HQZGPJTMOBLNCIFDYAWVEUSRKX", named = "IIC", model = "Commercial Enigma A, B", date = "1924"),
    IIIC(wire = "UQNTLSZFMREHDPXKIBVYGJCWOA", named = "IIIC", model = "Commercial Enigma A, B", date = "1924"),
    GR_I(wire = "JGDQOXUSCAMIFRVTPNEWKBLZYH", named = "I", model = "German Railway (Rocket)", date = "7 February 1941"),
    GR_II(wire = "NTZPSFBOKMWRCJDIVLAEYUXHGQ", named = "II", model = "German Railway (Rocket)", date = "7 February 1941"),
    GR_III(wire = "JVIUBHTCDYAKEQZPOSGXNRMWFL", named = "III", model = "German Railway (Rocket)", date = "7 February 1941"),
    GR_ETW(wire = "QWERTZUIOASDFGHJKPYXCVBNML", named = "ETW", model = "German Railway (Rocket)", date = "7 February 1941"),
    I_K(wire = "PEZUOHXSCVFMTBGLRINQJWAYDK", named = "I-K", model = "Swiss K", date = "February 1939"),
    II_K(wire = "ZOUESYDKFWPCIQXHMVBLGNJRAT", named = "II-K", model = "Swiss K", date = "February 1939"),
    III_K(wire = "EHRVXGAOBQUSIMZFLYNWKTPDJC", named = "III-K", model = "Swiss K", date = "February 1939"),
    ETW_K(wire = "QWERTZUIOASDFGHJKPYXCVBNML", named = "ETW-K", model = "Swiss K", date = "February 1939"),
    I(wire = "EKMFLGDQVZNTOWYHXUSPAIBRCJ", notches = "R", named = "I", model = "Enigma 1", date = "1930"),
    II(wire = "AJDKSIRUXBLHWTMCQGZNPYFVOE", notches = "F", named = "II", model = "Enigma 1", date = "1930"),
    III(wire = "BDFHJLCPRTXVZNYEIWGAKMUSQO", notches = "W", named = "III", model = "Enigma 1", date = "1930"),
    IV(wire = "ESOVPZJAYQUIRHXLNFTGKDCMWB", notches = "K", named = "VI", model = "M3 Army", date = "December 1938"),
    V(wire = "VZBRGITYUPSDNHLXAWMJQOFECK", notches = "A", named = "V", model = "M3 Army", date = "December 1938"),
    VI(wire = "JPGVOUMFYQBENHZRDKASXLICTW", notches = "AN", named = "VI", model = "M3 & M4 Naval(February 1942)", date = "1939"),
    VII(wire = "NZJHGRCXMYSWBOUFAIVLPEKQDT", notches = "AN", named = "VII", model = "M3 & M4 Naval(February 1942)", date = "1939"),
    VIII(wire = "FKQHTLXOCBJSPDZRAMEWNIUYGV", notches = "AN", named = "VIII", model = "M3 & M4 Naval(February 1942)", date = "1939"),
    Beta(wire = "LEYJVCNIXWPBQMDRTAKZGFUHOS", named = "Beta", model = "M4 R2", date = "Spring 1941"),
    Gamma(wire = "FSOKANUERHMBTIYCWLQPZXVGJD", named = "Gamma", model = "M4 R2", date = "Spring 1941"),
    ETW(wire = "ABCDEFGHIJKLMNOPQRSTUVWXYZ", named = "ETW", model = "Enigma 1");

    fun getRotor() = Rotor(wire = wire, notches = notches, name = named, model = model, date = date)
}

enum class PreMadeReflector(
    val wiring: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
    val named: String = "N/A",
    val model: String = "N/A",
    val date: String = "N/A"
) {
    A(wiring = "EJMZALYXVBWFCRQUONTSPIKHGD", named = "Reflector A"),
    B(wiring = "YRUHQSLDPXNGOKMIEBFZCWVJAT", named = "Reflector B"),
    C(wiring = "FVPJIAOYEDRZXWGCTKUQSBNMHL", named = "Reflector C"),
    B_Thin(wiring = "ENKQAUYWJICOPBLMDXZVFTHRGS", named = "Reflector_B_Thin", model = "M4 R1 (M3 + Thin)", date = "1940"),
    C_Thin(wiring = "RDOBJNTKVEHMLFCWZAXGYIPSUQ", named = "Reflector_C_Thin", model = "M4 R1 (M3 + Thin)", date = "1940"),
    GR_UKW(wiring = "QYHOGNECVPUZTFDJAXWMKISRBL", named = "UTKW", model = "German Railway (Rocket)", date = "7 February 1941"),
    UKW_K(wiring = "IMETCGFRAYSQBZXWLHKDVUPOJN", named = "UKW-K", model = "Swiss K", date = "February 1939");

    fun getReflector() = Reflector(wiring = wiring, name = named, model = model, date = date)
}