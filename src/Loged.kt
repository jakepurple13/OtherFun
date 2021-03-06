/**
 * Created by Jacob on 10/3/17.
 */
object Loged {
    /**
     * If there are any other classes that you do not want to show up
     */
    var OTHER_CLASS_FILTER: (String) -> Boolean = { true }
    /**
     * Will pretty print all log messages if true
     */
    var SHOW_PRETTY: Boolean = true
    /**
     * Will include the thread name in with the tag for all logs if true
     */
    var WITH_THREAD_NAME: Boolean = true
    /**
     * Makes this the name of your package to prevent unwanted logs
     */
    var FILTER_BY_CLASS_NAME = ""
    /**
     * A log tag for all log messages using the [Loged] class
     * Default is "Loged"
     */
    var TAG = "Loged"
    /**
     * Enable this if you are unit testing. This will do a normal [println] instead of a [Log.println] if true
     */
    var UNIT_TESTING = false
    /**
     * This class name
     */
    private const val HELPER_NAME = "Loged"
    /**
     * Converting the StackTraceElement into a string that will let us click straight to it
     */
    private val stackToString: StackTraceElement.() -> String =
        { "${className}.${methodName}(${fileName}:${lineNumber})" }
    /**
     * Filtering out the classes we do not want to see
     */
    private val filter: (StackTraceElement) -> Boolean =
        { !it.className.contains(HELPER_NAME) && it.className.contains(FILTER_BY_CLASS_NAME) && OTHER_CLASS_FILTER(it.className) }

    /**
     * If you want to set the [OTHER_CLASS_FILTER] up via Higher-Order Functions
     */
    fun OTHER_CLASS_FILTER(block: (String) -> Boolean) {
        OTHER_CLASS_FILTER = block
    }

    /**
     * The show pretty method
     */
    private fun prettyLog(tag: String, msg: Any?, level: Int, threadName: Boolean) {
        val wanted = Thread.currentThread().stackTrace.filter(filter).map(stackToString)
            .let { it.mapIndexed { index, s -> "\n${if (index == 0) "╚" else "\t${if (index + 1 < it.size) '╠' else '╚'}"}═▷\t$s" } }
            .joinToString("")
        print(tag, "$msg$wanted", level, threadName)
    }

    /**
     * The not show pretty method
     */
    private fun log(tag: String, msg: Any?, level: Int, threadName: Boolean) =
        print(
            tag,
            "${msg.toString()}\n╚═▷\t${Thread.currentThread().stackTrace.firstOrNull(filter)?.stackToString()}",
            level,
            threadName
        )

    /**
     * Actually printing to the console
     */
    private fun print(tag: String, msg: String, level: Int, threadName: Boolean): Any =
        println(msg)// else Log.println(level, tag + if (threadName) "/${Thread.currentThread().name}" else "", msg)

    /**
     * Delegating the showPretty or not
     */
    private fun delegate(tag: String, msg: Any?, level: Int, threadName: Boolean, showPretty: Boolean = SHOW_PRETTY) =
        if (showPretty) prettyLog(tag, msg, level, threadName) else log(tag, msg, level, threadName)

    /**
     * Error log
     *
     * @param msg the message to log
     */
    fun p(
        msg: Any? = null,
        tag: String = TAG,
        showPretty: Boolean = SHOW_PRETTY,
        threadName: Boolean = WITH_THREAD_NAME
    ) = delegate(tag, msg, 3, threadName, showPretty)
}


class Frame internal constructor(
    var top: String = "", var bottom: String = "",
    var left: String = "", var right: String = "",
    var topLeft: String = "", var topRight: String = "",
    var bottomLeft: String = "", var bottomRight: String = "",
    var topFillIn: String = "", var bottomFillIn: String = ""
)

enum class FrameType(val frame: Frame) {
    /**
     * BOX Frame
     * Will look like this
     *
    ```
    ╔==========================╗
    ║ Hello World              ║
    ╚==========================╝
    or
    If the top if modified
    ╔==========Hello===========╗
    ║ World                    ║
    ╚==========================╝
    or
    If the bottom is modified
    ╔==========================╗
    ║ World                    ║
    ╚==========Hello===========╝
    ```
     */
    BOX(Frame("=", "=", "║", "║", "╔", "╗", "╚", "╝", "=", "=")),
    /**
     * ASTERISK Frame
     * Will look like this
     *
    ```
    1.   ****************************
    2.   * Hello World              *
    3.   ****************************
    or
    If the top if modified
    1.   ***********Hello************
    2.   * World                    *
    3.   ****************************
    or
    If the bottom is modified
    1.   ****************************
    2.   * World                    *
    3.   ***********Hello************
    ```
     */
    ASTERISK(Frame("*", "*", "*", "*", "*", "*", "*", "*", "*", "*")),
    /**
     * Plus Frame
     * Will look like this
     *
    ```
    ++++++++++++++++++++++++++++
    + Hello World              +
    ++++++++++++++++++++++++++++
    or
    If the top if modified
    +++++++++++Hello++++++++++++
    + World                    +
    ++++++++++++++++++++++++++++
    or
    If the bottom is modified
    ++++++++++++++++++++++++++++
    + World                    +
    +++++++++++Hello++++++++++++
    ```
     */
    PLUS(Frame("+", "+", "+", "+", "+", "+", "+", "+", "+", "+")),
    /**
     * DIAGONAL Frame
     * Will look like this
     *
    ```
    ╱--------------------------╲
    | Hello World              |
    ╲--------------------------╱
    or
    If the top if modified
    ╱----------Hello-----------╲
    | World                    |
    ╲--------------------------╱
    or
    If the bottom is modified
    ╱--------------------------╲
    | World                    |
    ╲----------Hello-----------╱
    ```
     */
    DIAGONAL(Frame("-", "-", "│", "│", "╱", "╲", "╲", "╱", "-", "-")),
    /**
     * OVAL Frame
     * Will look like this
     *
    ```
    ╭--------------------------╮
    | Hello World              |
    ╰--------------------------╯
    or
    If the top if modified
    ╭----------Hello-----------╮
    | World                    |
    ╰--------------------------╯
    or
    If the bottom is modified
    ╭--------------------------╮
    | World                    |
    ╰----------Hello-----------╯
    ```
     */
    OVAL(Frame("-", "-", "│", "│", "╭", "╮", "╰", "╯", "-", "-")),
    /**
     * BOXED Frame
     * Will look like this
     *
    ```
    ▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▜
    ▌ Hello World              ▐
    ▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟
    or
    If the top if modified
    ▛▀▀▀▀▀▀▀▀▀▀Hello▀▀▀▀▀▀▀▀▀▀▀▜
    ▌ World                    ▐
    ▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟
    or
    If the bottom is modified
    ▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▜
    ▌ World                    ▐
    ▙▄▄▄▄▄▄▄▄▄▄Hello▄▄▄▄▄▄▄▄▄▄▄▟
    ```
     */
    BOXED(Frame("▀", "▄", "▌", "▐", "▛", "▜", "▙", "▟", "▀", "▄")),
    /**
     * CUSTOM Frame
     * You decide how all of it looks
     */
    CUSTOM(Frame());

    companion object {
        /**
         * Use this to create a custom [FrameType]
         */
        @Suppress("FunctionName")
        fun CUSTOM(frame: Frame.() -> Unit) = CUSTOM.frame.apply(frame)
    }
}

fun String.frame(frameType: FrameType, rtl: Boolean = false) = split("\n").frame(
    top = frameType.frame.top, bottom = frameType.frame.bottom,
    left = frameType.frame.left, right = frameType.frame.right,
    topLeft = frameType.frame.topLeft, topRight = frameType.frame.topRight,
    bottomLeft = frameType.frame.bottomLeft, bottomRight = frameType.frame.bottomRight,
    topFillIn = frameType.frame.topFillIn, bottomFillIn = frameType.frame.bottomFillIn, rtl = rtl
)

fun <T> Iterable<T>.frame(frameType: FrameType, rtl: Boolean = false, transform: (T) -> String = { it.toString() }) =
    frame(
        top = frameType.frame.top, bottom = frameType.frame.bottom,
        left = frameType.frame.left, right = frameType.frame.right,
        topLeft = frameType.frame.topLeft, topRight = frameType.frame.topRight,
        bottomLeft = frameType.frame.bottomLeft, bottomRight = frameType.frame.bottomRight,
        topFillIn = frameType.frame.topFillIn, bottomFillIn = frameType.frame.bottomFillIn,
        rtl = rtl, transform = transform
    )

fun <T> Iterable<T>.frame(
    top: String, bottom: String,
    left: String, right: String,
    topLeft: String, topRight: String,
    bottomLeft: String, bottomRight: String,
    topFillIn: String = "", bottomFillIn: String = "",
    rtl: Boolean = false, transform: (T) -> String = { it.toString() }
): String {
    val fullLength =
        mutableListOf(top, bottom).apply { addAll(this@frame.map(transform)) }.maxBy { it.length }!!.length + 2
    val space: (String) -> String = { " ".repeat(fullLength - it.length - 1) }
    val mid = joinToString(separator = "\n") {
        "$left${if (rtl) space(transform(it)) else " "}$it${if (rtl) " " else space(transform(it))}$right"
    }
    val space2: (String, Boolean) -> String =
        { spacing, b -> (if (b) topFillIn else bottomFillIn).repeat((fullLength - spacing.length) / 2) }
    val topBottomText: (String, Boolean) -> String = { s, b ->
        if (s.length == 1) s.repeat(fullLength)
        else space2(
            s,
            b
        ).let { spaced -> "$spaced$s${if ((fullLength - s.length) % 2 == 0) "" else (if (b) topFillIn else bottomFillIn)}$spaced" }
    }
    return "$topLeft${topBottomText(top, true)}$topRight\n$mid\n$bottomLeft${topBottomText(bottom, false)}$bottomRight"
}

private fun frameLog(tag: String): FrameType.() -> Unit = {
    frame.top = tag
    frame.bottomLeft = "╠"
}

/**
 * [Loged.r] but adds a frame around the [msg] using the [String.frame] method
 */
fun Loged.f(
    msg: Any? = null,
    tag: String = TAG, infoText: String = TAG,
    showPretty: Boolean = SHOW_PRETTY, threadName: Boolean = WITH_THREAD_NAME
) = p(
    "${if (UNIT_TESTING) "" else "$infoText\n"}${msg.toString().frame(FrameType.BOX.apply(frameLog(tag)))}",
    tag,
    showPretty,
    threadName
)

/**
 * [Loged.r] but adds a frame around the [msg] using the [Iterable.frame] method
 */
fun Loged.f(
    msg: Iterable<*>,
    tag: String = TAG, infoText: String = TAG,
    showPretty: Boolean = SHOW_PRETTY, threadName: Boolean = WITH_THREAD_NAME
) = p(
    "${if (UNIT_TESTING) "" else "$infoText\n"}${msg.frame(FrameType.BOX.apply(frameLog(tag)))}",
    tag,
    showPretty,
    threadName
)

/**
 * [Loged.r] but adds a frame around the [msg] using the [Iterable.frame] method. Also allows you to [transform] the objects if you wish, otherwise
 * [toString] will be called
 */
fun <T> Loged.f(
    msg: Iterable<T>,
    tag: String = TAG,
    infoText: String = TAG,
    showPretty: Boolean = SHOW_PRETTY,
    threadName: Boolean = WITH_THREAD_NAME,
    transform: (T) -> String = { it.toString() }
) = p(
    "${if (UNIT_TESTING) "" else "$infoText\n"}${msg.frame(FrameType.BOX.apply(frameLog(tag)), transform = transform)}",
    tag,
    showPretty,
    threadName
)
