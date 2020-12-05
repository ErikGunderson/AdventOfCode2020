import java.io.File

fun day4Solution1() {
    val inputFileName = "puzzle4"
    val inputFileLocation = System.getProperty("user.dir") + File.separator + "inputs" + File.separator + inputFileName
    val inputFile = File(inputFileLocation)

    if (!inputFile.exists()) { print("Input file does not exist!"); return }

    val requiredDataKeys = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    var validPassportCount = 0

    val passportDataRaw = mutableListOf<MutableList<String>>()
    passportDataRaw.add(0, mutableListOf())
    var currentPassportIndex = 0

    inputFile.readLines().let { inputLines ->
        inputLines.map { it.trim() }.forEachIndexed { index, input ->
            if (input != "") {
                val dataSections = input.split(" ")
                val dataKeys = dataSections.map { it.split(":")[0] }

                passportDataRaw[currentPassportIndex].addAll(dataKeys)
            }

            if (index == inputLines.lastIndex || input == "") {
                if (requiredDataKeys.subtract(passportDataRaw[currentPassportIndex]).isEmpty()) validPassportCount += 1

                currentPassportIndex += 1
                passportDataRaw.add(currentPassportIndex, mutableListOf())
                return@forEachIndexed
            }
        }
    }

    print("Valid passport count is $validPassportCount")
    print("\nDONE :D")
}

fun day4Solution2() {
    val inputFileName = "puzzle4"
    val inputFileLocation = System.getProperty("user.dir") + File.separator + "inputs" + File.separator + inputFileName
    val inputFile = File(inputFileLocation)

    if (!inputFile.exists()) { print("Input file does not exist!"); return }

    val requiredDataPairs = listOf(
        "byr" to Regex("^(19[2-9][0-9])|(200[0-2])\$"),
        "iyr" to Regex("^(201[0-9])|(2020)\$"),
        "eyr" to Regex("^(202[0-9])|(2030)\$"),
        "hgt" to Regex("^(1(([5-8][0-9])|(9[0-3]))cm)|(((59)|(6[0-9])|(7[0-6]))in)\$"),
        "hcl" to Regex("(#([0-9]|[a-f]){6})"),
        "ecl" to Regex("^(amb)|(blu)|(brn)|(gry)|(grn)|(hzl)|(oth)\$"),
        "pid" to Regex("^[0-9]{9}\$")
    )
    var validPassportCount = 0

    val passportDataRaw = mutableListOf<MutableList<Pair<String, String>>>()
    passportDataRaw.add(0, mutableListOf())
    var currentPassportIndex = 0

    inputFile.readLines().let { inputLines ->
        inputLines.map { it.trim() }.forEachIndexed { index, input ->
            if (input != "") {
                val dataSections = input.split(" ")
                val dataPairs = dataSections.map { it.split(":").let { it[0] to it[1] } }

                passportDataRaw[currentPassportIndex].addAll(dataPairs)
            }

            if (index == inputLines.lastIndex || input == "") {
                val validPassport = requiredDataPairs.all { (key, regex) ->
                    val indexOfRequiredData = passportDataRaw[currentPassportIndex].indexOfFirst { it.first == key }

                    if (indexOfRequiredData < 0) false
                    else regex.matchEntire(passportDataRaw[currentPassportIndex][indexOfRequiredData].second)?.let { true } ?: false
                }

                if (validPassport) validPassportCount += 1

                currentPassportIndex += 1
                passportDataRaw.add(currentPassportIndex, mutableListOf())
                return@forEachIndexed
            }
        }
    }

    print("Valid passport count is $validPassportCount")
    print("\nDONE :D")
}