package org.kotmandline.parser

interface TypeParser <T>  {
    fun convertArgumentToType(argument: String) : T
}

class StringParser : TypeParser<String> {
    override fun convertArgumentToType(argument: String): String {
        return argument
    }
}

class IntParser : TypeParser<Int> {
    override fun convertArgumentToType(argument: String): Int {
        return argument.toInt()
    }
}

class LongParser : TypeParser<Long> {
    override fun convertArgumentToType(argument: String): Long {
        return argument.toLong()
    }
}

class FloatParser : TypeParser<Float> {
    override fun convertArgumentToType(argument: String): Float {
        return argument.toFloat()
    }
}

class DoubleParser : TypeParser<Double> {
    override fun convertArgumentToType(argument: String): Double {
        return argument.toDouble()
    }
}
