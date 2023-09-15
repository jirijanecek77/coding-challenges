package challenge

class TwoSum {

    // https://adventofcode.com/2020 - day 1
    fun calculateTwoSum(list: List<Int>, t: Int): Int {

        val data = list.sorted()
        var (i, j) = findTwoSumIndexes(data, t) ?: return -1

        return data[i] * data[j]
    }

    fun calculateThreeSum(list: List<Int>, t: Int): Int {
        val data = list.sorted()

        for (k in data.indices) {
            val (i, j) = findTwoSumIndexes(data, t - data[k]) ?: continue
            return data[i] * data[j] * data[k]
        }
        return -1
    }

    private fun findTwoSumIndexes(data: List<Int>, t: Int): Pair<Int, Int>? {
        var i = 0;
        var j = data.size - 1

        while (i < j) {
            if (data[i] + data[j] == t) {
                return i to j
            } else if (data[i] + data[j] < t) {
                i++
            } else {
                j--
            }
        }
        return null
    }
}