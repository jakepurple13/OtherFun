#!/usr/bin/env kscript

import itemsstuff.*
import itemsstuff.cards.Card
import itemsstuff.cards.Deck
import itemsstuff.cards.Suit
import itemsstuff.cards.YugiohCard
import itemsstuff.checkers.CheckersBoard
import itemsstuff.chess.Board
import itemsstuff.chess.toFEN
import itemsstuff.chess.*
import itemsstuff.enigma.Enigma
import itemsstuff.enigma.PreMadeReflector
import itemsstuff.enigma.PreMadeRotor
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

fun setup() {
    Loged.UNIT_TESTING = true
}

@ExperimentalTime
fun main() = runBlocking {
    setup()
    //checkers()
    //chess()
    //blackjack()
    //cards()
    //sequenceGuesser()
    //carGuesser()
    //guessingGameTest()
    //carGuesser2()
    //numberGuesser()
    //numberGuessBuilder()
    //doubleNumberGuess()
    //mergeSort()
    //measureTime { mergeSortModified() }.also { println(it.inMilliseconds) }
    //operateTest()
    enigmaTest()
}

fun enigmaTest() {
    val message = "Hello world!";

    val enigma = Enigma(
        PreMadeReflector.A.getReflector(),
        PreMadeRotor.I.getRotor(),
        PreMadeRotor.II.getRotor(),
        PreMadeRotor.III.getRotor(),
        "ABC",
        "AV BS CG DL FU HZ IN KM OW RX"
    )

    val encoded = enigma.encode(message)

    println(encoded)
    println("Should get: QGQOP VWOXN!")
}

fun operateTest() {
    //All of these classes and methods come from java
    val f = Operating()
    println(f.list)
    val c = f[4]
    println(c)
    f[3] = "Hello"
    f += "asdkl;fj"
    println(f.list)
    val g = f + "Hello"
    val x = "Hello" in f
    println(x)
    f("Hi")
}

fun randomIntList(size: Int, range: IntRange) = mutableListOf<Int>().apply { repeat(size) { this@apply += range.random() } }

fun mergeSortModified() {
    // Iterative Implementation of Mergesort algorithm
    // int[] A = { 12, 6, 18, 13, 7, 9, 20, 5, 11, 22, 17, 21, 19, 16, 23, 14, 25, 3, 15, 8, 2, 4, 24, 1, 10 };
    val a = arrayOf(32, 4, 12, 44, 36, 30, 1, 9, 23, 43, 24, 40, 41, 37, 15, 29)
    //val a = randomIntList(16, 0..99).toTypedArray()
    MergeSortModified()
        .barColor(java.awt.Color.DARK_GRAY.rgb)
        .selectedValue(java.awt.Color.RED.rgb)
        .changingValue(java.awt.Color.BLUE.rgb)
        .sleep(true)
        .mergesort(a)
}

fun mergeSort() {
    // Iterative Implementation of Mergesort algorithm
    // int[] A = { 12, 6, 18, 13, 7, 9, 20, 5, 11, 22, 17, 21, 19, 16, 23, 14, 25, 3, 15, 8, 2, 4, 24, 1, 10 };
    val A = intArrayOf(32, 4, 12, 44, 36, 30, 1, 9, 23, 43, 24, 40, 41, 37, 15, 29)
    MergeSort().mergesort(A)
}

fun doubleNumberGuess() = GuessingGameBuilder<Int?> {
    var count = 0
    val scan = Scanner(System.`in`)
    val range = 1..5
    val num = Random.nextInt(range)
    addItem(num)
    prompt { println("Guess a number between ${range.first} and ${range.last}") }
    endGame { println("End Game Here: $it") }
    nextItem { println("The item is $it") }
    sequenceReset {}
    victory {
        runBlocking {
            GuessingGameBuilder<Int?> {
                addItem(Random.nextInt(range))
                prompt { println("Guess another number between ${range.first} and ${range.last}") }
                endGame { println("It took you $count guesses but the answer was ${"$num, ${it[0]}"}") }
                nextItem { println("The item is $it") }
                sequenceReset {}
                victory { println("It took you $count guesses") }
                readIn { scan.nextInt().also { count++ } }
            }.also { it.startGame() }.start()
        }
    }
    readIn { scan.nextInt().also { count++ } }
}.start()

fun numberGuessBuilder() {
    GuessingGameBuilder<Int?> {
        val scan = Scanner(System.`in`)
        var count = 0
        addItem(Random.nextInt(1, 20))
        prompt { println("Guess a number between 1 and 20") }
        endGame { println("It took you $count guesses but the answer was $it") }
        nextItem { println("The item is $it") }
        sequenceReset {}
        victory { println("It took you $count guesses") }
        readIn { if (++count > 10) stopGame() else scan.nextInt() }
    }.start()
}

fun guesserBuilder() {
    GuessingGameBuilder<Int> {
        addItem(1, 2, 3)
        addItems(listOf(1, 2, 3))
        prompt {}
        endGame {}
        nextItem {}
        sequenceReset {}
        victory {}
        readIn { Random.nextInt(1, 3) }
    }
}

fun numberGuesser() {
    val scan = Scanner(System.`in`)
    val randNum = Random.nextInt(1, 20)
    do println("Guess the Number") while (randNum != scan.nextInt())
}

enum class Cars { AUDI, MINI, BMW }

fun carGuesser2() {
    val scan = Scanner(System.`in`)
    println("How long do you want the password? (Default will be 3 if your choice is not a number or less than 1)")
    val length = scan.nextLine().toIntOrNull()?.let { if (it < 1) 1 else it } ?: 3
    println("You will be playing with a password length of $length.")
    GuessingGame<Cars?>(customListOf(length) { Cars.values().random() }) { println("Achieved!") }
        .prompt { println("Guess a car brand:") }
        .nextItem { println("$it was correct") }
        .sequenceReset { println("Resetting") }
        .endGame { println("The correct sequence was $it") }
        .start {
            val read = scan.nextLine()
            if (read.equals("Stop", true)) {
                stopGame()
            } else try {
                Cars.valueOf(read.toUpperCase())
            } catch (e: Exception) {
                null
            }
        }
}

fun carGuesser() {
    val scan = Scanner(System.`in`)
    var stillPlaying = true
    println("How long do you want the password? (Default will be 3 if your choice is not a number or less than 1)")
    val length = scan.nextLine().toIntOrNull()?.let { if (it < 1) 1 else it } ?: 3
    println("You will be playing with a password length of $length.")
    val seq = OtherSequenceMaker<Cars?>(customListOf(length) { Cars.values().random() }) {
        println("You may now Enter").also { stillPlaying = false }
    }
    seq.nextItem { println("$it was correct") }
    seq.sequenceReset { println("Failed, resetting") }
    gameLoop@ while (stillPlaying) {
        println("Make your guess")
        val read = scan.nextLine()
        seq += if (read.equals("Stop", true)) break@gameLoop else try {
            Cars.valueOf(read.toUpperCase())
        } catch (e: Exception) {
            null
        }
    }
    println("The correct sequence was ${seq.correctSequence()}")
}

fun guessingGameTest() {
    val scan = Scanner(System.`in`)
    val game = GuessingGame<Int?>(customListOf(5) { it })
        .prompt { println("Make a guess") }
        .nextItem { println("$it was correct") }
        .sequenceReset { println("Failed, resetting") }
        .endGame { println("The correct sequence was $it") }
        .readIn {
            val read = scan.nextLine()
            if (read.equals("Stop", true)) stopGame() else read.toIntOrNull()
        }
    game.stopGame()
    game.start()
}

fun sequenceGuesser() {
    fun String.toCard() = split("").filter(String::isNotBlank).let { card ->
        when (val rank = card.firstOrNull()) {
            "K", "k" -> 13
            "Q", "q" -> 12
            "J", "j" -> 11
            "A", "a" -> 1
            "X", "x" -> 10
            null -> null
            else -> rank.toIntOrNull()
        }?.let { value ->
            when (card.lastOrNull()) {
                "S", "s" -> Suit.SPADES
                "C", "c" -> Suit.CLUBS
                "D", "d" -> Suit.DIAMONDS
                "H", "h" -> Suit.HEARTS
                else -> null
            }?.let { Card(value, it) }
        }
    }

    val scan = Scanner(System.`in`)
    var stillPlaying = true
    val randomSuit = Suit.values().random()
    val deck = Deck.defaultDeck().let { d -> customListOf(5) { d.randomDraw { it.suit == randomSuit && it.value in 2..7 } } }
    val sequence = OtherSequenceMaker<Card?>(deck) { println("Achieved!").also { stillPlaying = false } }
    sequence.nextItem { println("$it was correct") }
    sequence.sequenceReset { println("Failed, resetting") }
    println("The suit is $randomSuit")
    gameLoop@ while (stillPlaying) {
        println("Make your guess")
        val read = scan.nextLine()
        sequence += if (read.equals("Stop", true)) break@gameLoop else read.toCard()
    }
    println("The correct sequence was ${sequence.correctSequence()}")
}

fun intSequenceGuesser() {
    val scan = Scanner(System.`in`)
    var stillPlaying = true
    val sequence = SequenceMaker<Int>(customListOf(5) { it }) { println("Achieved!").also { stillPlaying = false } }
    sequence.sequenceReset { println("Failed, resetting") }
    while (stillPlaying) {
        println("Make your guess")
        sequence += scan.nextInt()
    }
}

fun getYugiohCards() = File("cards.json").readText().fromJson<List<YugiohCard>>()!!

fun cards() {
    val file = File("cards.json")
    val cardList = file.readText().fromJson<List<YugiohCard>>()!!
    //println(cardList)
    println(cardList.random())
    println(cardList.size)
    println()
    /*val byType = cardList.groupBy { it.type }.entries.joinToString("\n") { "${it.key} -> ${it.value.joinToString { it.name.toString() }}" }
    println(byType)
    println()
    val byRace = cardList.groupBy { it.race }.entries.joinToString("\n") { "${it.key} -> ${it.value.joinToString { it.name.toString() }}" }
    println(byRace)
    println()*/
    /*val byPricing = cardList.sortedBy { it.card_prices!!.maxBy { it.highestPrices() }?.highestPrices() }
    println(byPricing.joinToString("\n") { "${it.name} - ${it.highestPrice()} - ${it.card_prices}" })*/
    val d = Deck.DeckBuilder<YugiohCard> {
        //cards(cardList.takeRandom(40))
        val card = cardList.random()
        cards(card.findRelatedCards(cardList))
        cards(card)
        deckListener {
            onDraw { _, _ -> println("I draw!") }
        }
    }
    val hand = mutableListOf<YugiohCard>()
    hand += d.draw()
    hand += d.draw()
    hand += d.draw()
    hand += d.draw()
    hand += d.draw()
    println(hand.joinToString(", ") { it.name })
}

fun <T> Collection<T>.takeRandom(n: Int): List<T> = mutableListOf<T>().apply { repeat(n) { this += this@takeRandom.random() } }

fun checkers() {
    val b = CheckersBoard()
    /*println(b.board.joinToString("\n") { it.joinToString(" | ") { it.color.name } })*/
    println(b.publicBoard.joinToString("\n") { it.joinToString(" | ") { it.toString() } })
}

fun Random.nextColor(
    alpha: Int = nextInt(0, 255),
    red: Int = nextInt(0, 255),
    green: Int = nextInt(0, 255),
    blue: Int = nextInt(0, 255)
): Int = java.awt.Color(red, green, blue, alpha).rgb

fun chess() {
    val scan = Scanner(System.`in`)
    val b1 = Board { fromFEN("rnbqkbnr/2pppQp1/8/pp5p/2B1P3/8/PPPP1PPP/RNB1K1NR b KQkq - 0 4") }
    println(b1.toFEN())
    Board { moveCount = 5 }
    Board {
        pawn(1, 3, Color.WHITE)
        rook(1, 3, Color.WHITE)
        knight(1, 3, Color.WHITE)
        bishop(1, 3, Color.WHITE)
        queen(1, 3, Color.WHITE)
        king(1, 3, Color.WHITE)
    }
    val b = Board {
        boardUpdate {
            println(AnsiColor.colorText(it.joinToString("\n") {
                it.joinToString("|") { "${it.character}${it.y} - ${it.piece?.unicodeSymbol ?: "  "}" }
            }, Random.nextColor()))
        }
        pawnPromotion {
            println("Choose a Piece:")
            when (scan.nextLine()) {
                "P" -> Pawn(it)
                "R" -> Rook(it)
                "N" -> Knight(it)
                "B" -> Bishop(it)
                "Q" -> Queen(it)
                else -> null
            }
        }
        checkListen { p, k -> println("$p can take $k") }
        capture { println("$it was ${it.value} points") }
    }
    b.setup()
    b.move(6 to 0, 4 to 0)
    b.move(4 to 0 to 5 to 0)
    b.move(4 to 0, 3 to 0)
    b.move(1 to 1, 3 to 1)
    b.move(3 to 0, 2 to 1)
    b.move(2 to 1, 1 to 0)
    b.move("G0" to "E0")
    b.move("e0" to "d0")
    b.move(6 to 1 to 5 to 1)
    b.move(6, 0, 5, 0)
    b.customToString { it.toString() }
    //println(b)
    /*b.move(6, 0, 5, 0)
    b.move(5 to 0, 4 to 0)
    b.move(4 to 0, 3 to 0)
    b.move(3 to 0, 2 to 0)
    b.move(2 to 0, 1 to 1)
    b.move(1 to 1, 0 to 0)
    b.move(0 to 0, 0 to 1)
    b.move(0 to 1, 0 to 2)*/
    //println(b)
    //b.move(7 to 4, 7 to 6)
    //b.move(7 to 4, 7 to 1)
    //b.move(0 to 4, 0 to 6)
    //b.move(7 to 4, 7 to 1)
    //b.move(0 to 4, 0 to 1)
    println(b.getCaptured(Color.BLACK).joinToString { it.unicodeSymbol })
    println(b.getCaptured(Color.WHITE).joinToString { it.unicodeSymbol })
    println(b.getCaptured(Color.BLACK).sumBy { it.value })
    println(b.getCaptured(Color.WHITE).sumBy { it.value })
    println(b.toFEN())
}

object AnsiColor {
    private const val prefix = "\u001B"
    private const val RESET = "$prefix[0m"
    private val isCompatible = !System.getProperty("os.name")?.toLowerCase()?.contains("win")!!
    private fun getColor(r: Int, g: Int, b: Int) = "[38;2;$r;$g;$b"
    private fun regularColor(r: Int, g: Int, b: Int) = if (isCompatible) "$prefix${getColor(r, g, b)}m" else ""
    private fun regularColor(color: Int) = color.valueOf().let { regularColor(it.first, it.second, it.third) }
    fun colorText(s: String, color: Int) = "${regularColor(color)}$s$RESET"
    private fun Int.valueOf(): Triple<Int, Int, Int> {
        val r = (this shr 16 and 0xff)// / 255.0f
        val g = (this shr 8 and 0xff)// / 255.0f
        val b = (this and 0xff)// / 255.0f
        return Triple(r, g, b)
    }
}

enum class HitStay {
    HIT, STAY, EXIT;

    companion object {
        fun fromString(s: String) = when (s) {
            "H", "h", "Hit", "hit" -> HIT
            "S", "s", "Stay", "stay" -> STAY
            "E", "e", "Exit", "exit" -> EXIT
            else -> null
        }

        operator fun invoke(s: String) = fromString(s)
    }
}

enum class Continue {
    YES, NO;

    companion object {
        fun fromString(s: String) = when (s) {
            "Y", "y", "Yes", "yes" -> YES
            "N", "n", "No", "no" -> NO
            else -> null
        }

        operator fun invoke(s: String) = fromString(s)
    }
}

fun blackjack() {
    val deck = Deck.DeckBuilder<Card> {
        deck(Deck.defaultDeck())
        deckListener {
            onShuffle { println("Shuffling...") }
            onAdd { println("Adding Cards") }
        }

    }
    var playerWins = 0
    var dealerWins = 0
    var ties = 0
    var count = 0
    fun List<Card>.sumOfCards(): Int {
        val s = sortedWith(compareByDescending(Card::valueTen))
        var num = 0
        for (card in s) {
            num += if (card.value == 1 && num + 11 < 22) 11
            else if (card.value == 1) 1
            else card.valueTen
        }
        return num
    }

    fun List<Card>.cardToString() = joinToString(", ") { "${it.symbol}${it.suit.unicodeSymbol}" }
    val scan = Scanner(System.`in`)
    gameLoop@ while (deck.isNotEmpty) {
        try {
            val dealer = mutableListOf(deck.draw(), deck.draw())
            val player = mutableListOf(deck.draw(), deck.draw())
            fun printCurrentValues(msg: String = "") =
                println(
                    msg +
                            "Player: ${player.sumOfCards()} = (${player.cardToString()})" +
                            " | " +
                            "Dealer: ${dealer.sumOfCards()} = (${dealer.cardToString()})"
                )
            printCurrentValues("$count.\n")
            playerLoop@ while (true) {
                var choice: HitStay? = null
                while (choice == null) {
                    println("(H)it or (S)tay or (E)xit")
                    choice = HitStay(scan.nextLine())
                }
                if (choice == HitStay.EXIT) break@gameLoop
                if (choice == HitStay.HIT) player += deck.draw()
                println("Player: ${player.sumOfCards()} (${player.cardToString()})")
                if (choice == HitStay.STAY || player.sumOfCards() > 21) break@playerLoop
            }
            while (dealer.sumOfCards() < 16 && player.sumOfCards() < 22) {
                dealer += deck.draw()
                println("Dealer: ${dealer.sumOfCards()} (${dealer.cardToString()})")
            }
            printCurrentValues()
            val playerSum = player.sumOfCards()
            val dealerSum = dealer.sumOfCards()
            val end = when {
                playerSum in (dealerSum + 1)..21 || dealerSum > 21 -> "Player wins!".also { playerWins++ }
                dealerSum in (playerSum + 1)..21 || playerSum > 21 -> "Dealer wins!".also { dealerWins++ }
                playerSum == dealerSum -> "Pushed!".also { ties++ }
                playerSum > 21 && dealerSum > 21 -> "No one won.".also { ties++ }
                else -> null
            }
            println(end?.frame(FrameType.BOX))
            println("---------------${deck.size} cards left-------------------")
            if (deck.size < 4) throw Exception("No cards left")
        } catch (e: Exception) {
            loop@ while (true) {
                println("Deck is done. Want to play again? (Y)es or (N)o")
                when (Continue(scan.nextLine())) {
                    Continue.YES -> {
                        deck(Deck.defaultDeck())
                        deck.shuffle()
                        break@loop
                    }
                    Continue.NO -> break@loop
                    else -> continue@loop
                }

            }
        } finally {
            count++
        }
    }
    Loged.f("Player won $playerWins time(s). Dealer won $dealerWins time(s). There was $ties tie(s). There was a total of ${--count} hand(s).")
}