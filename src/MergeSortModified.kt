class MergeSortModified {
    private val reset = "\u001b[0m"
    private val block = "\u2588"
    private var selected = "\u001b[0;31m"
    private var changing = "\u001b[0;34m"
    private var bar = "\u001b[0;37m"
    private var sleep = false
    fun selectedValue(color: Int) = apply { selected = getColor(color) }
    fun changingValue(color: Int) = apply { changing = getColor(color) }
    fun barColor(color: Int) = apply { bar = getColor(color) }
    fun sleep(shouldSleep: Boolean) = apply { sleep = shouldSleep }
    private fun getColor(color: Int) = color.valueOf().let { "\u001B${"[38;2;${it.first};${it.second};${it.third}"}m" }
    private fun Int.valueOf() = Triple(this shr 16 and 0xff, this shr 8 and 0xff, this and 0xff)
    private fun clearScreen() = System.out.printf("\u001b[H\u001b[2J").also { System.out.flush() }.let { Unit }

    private fun drawBar(arr: Array<Int>, currI: Int, currJ: Int) {
        clearScreen()
        for (i in arr.indices) {
            val barColor: String = when (i) {
                currI -> selected
                currJ -> changing
                else -> bar
            }
            val bar = String(CharArray(arr[i])).replace("\u0000", block)
            val line = String.format("%2d %s", arr[i], "$barColor$bar$reset")
            println(line)
        }
        if (sleep)
            try {
                Thread.sleep(200)
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
            }
    }

    // Merge two sorted sub-arrays A[from .. mid] and A[mid + 1 .. to]
    private fun merge(A: Array<Int>, temp: Array<Int>, from: Int, mid: Int, to: Int) {
        var k = from
        var i = from
        var j = mid + 1

        // loop till there are elements in the left and right runs
        while (i <= mid && j <= to) {
            temp[k++] = if (A[i] < A[j]) A[i++] else A[j++]
            drawBar(A, i, j)
        }

        // Copy remaining elements
        while (i < A.size && i <= mid) {
            temp[k++] = A[i++]
            drawBar(A, i, j)
        }

        // copy back to the original array to reflect sorted order
        i = from
        while (i <= to) {
            A[i] = temp[i]
            drawBar(A, i, -1)
            i++
        }
    }

    fun mergesort(A: Array<Int>) {
        val low = 0
        val high = A.size - 1
        val temp = A.copyOf()
        var m = 1
        while (m <= high - low) {
            var i = low
            while (i < high) {
                merge(A, temp, i, i + m - 1, Integer.min(i + 2 * m - 1, high))
                i += 2 * m
            }
            m *= 2
        }
        clearScreen()
        for (elm in A) {
            var chart = String(CharArray(elm)).replace("\u0000", block)
            chart = "$bar$chart$reset"
            System.out.printf(String.format("%2d %s %n", elm, chart))
        }
    }
}

fun main() {
    val a = arrayOf(32, 4, 12, 44, 36, 30, 1, 9, 23, 43, 24, 40, 41, 37, 15, 29)
    MergeSortModified()
        .sleep(true)
        .mergesort(a)
}