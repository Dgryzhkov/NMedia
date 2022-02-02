package ru.netology.nmedia

object Services {
    //формат чисел с обавление суфикса
     fun formatCount(number: Int): String {
        val suffixes = charArrayOf('k', 'm')
        if (number < 1000) {
            return java.lang.String.valueOf(number)
        }

        val string = java.lang.String.valueOf(number)

        // разрядность числа
        val magnitude = (string.length - 1) / 3

        // количество цифр до суффикса
        val digits = (string.length - 1) % 3 + 1

        val value = CharArray(4)
        for (i in 0 until digits) {
            value[i] = string[i]
        }
        var valueLength = digits

        // добавление точки и числа
        if (digits == 1 && string[1] != '0') {
            value[valueLength++] = '.'
            value[valueLength++] = string[1]
        }

        // добавление суффикса
        value[valueLength++] = suffixes[magnitude - 1]
        return String(value)
    }


}