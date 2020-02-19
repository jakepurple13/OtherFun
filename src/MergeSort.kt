
class MergeSort {
    private fun clearScreen() {
        System.out.printf("\u001b[H\u001b[2J")
        System.out.flush()
    }

    private fun drawBar(arr: IntArray, currI: Int, currJ: Int) {
        clearScreen()
        for (i in arr.indices) {
            val barColor: String = when (i) {
                currI -> "\u001b[0;31m"
                currJ -> "\u001b[0;34m"
                else -> "\u001b[0;37m"
            }
            val bar = String(CharArray(arr[i])).replace("\u0000", "\u2588")
            val line = String.format("%2d %s", arr[i], "$barColor$bar\u001b[0m")
            println(line)
        }
        try {
            Thread.sleep(200)
        } catch (ex: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    // Merge two sorted sub-arrays A[from .. mid] and A[mid + 1 .. to]
    private fun merge(A: IntArray, temp: IntArray, from: Int, mid: Int, to: Int) {
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

    fun mergesort(A: IntArray) {
        val low = 0
        val high = A.size - 1
        val temp = A.copyOf(A.size)
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
            var chart = String(CharArray(elm)).replace("\u0000", "\u2588")
            chart = "\u001b[0;37m$chart\u001b[0m"
            val line = String.format("%2d %s %n", elm, chart)
            System.out.printf(line)
        }
    }
}