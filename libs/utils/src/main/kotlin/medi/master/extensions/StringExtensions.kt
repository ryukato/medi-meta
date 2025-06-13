package medi.master.extensions

fun String.toCamelCaseIfApplicable(): String {
    // SNAKE_CASE 여부 판단: 대문자+밑줄, 알파벳+숫자 허용
    val snakeCaseRegex = Regex("^[A-Za-z0-9]+(_[A-Za-z0-9]+)+$")

    return if (this.isSnakeCase()) {
        this
            .lowercase()
            .split("_")
            .mapIndexed { index, word ->
                if (index == 0) word
                else word.replaceFirstChar { it.uppercase() }
            }
            .joinToString("")
    } else {
        this // 변환하지 않음
    }
}

fun String.isSnakeCase(): Boolean {
    // SNAKE_CASE 여부 판단: 대문자+밑줄, 알파벳+숫자 허용
    val snakeCaseRegex = Regex("^[A-Za-z0-9]+(_[A-Za-z0-9]+)+$")
    return snakeCaseRegex.matches(this)
}
