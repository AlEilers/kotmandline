package org.kotmandline.example

import org.kotmandline.parser.KotmandlineParser
import org.kotmandline.parser.TypeParser
import kotlin.reflect.KClassifier

fun main() {

    val typeParser = mapOf<KClassifier, TypeParser<*>>(MyDataClass::class to MyDataClassParser())

    val args = listOf("--uname", "USERNAME", "-r", "--data", "Hello World").toTypedArray()

    val parser = ComplexCommandlineParser(args, typeParser)

    println("""
            Parameter           "Username" = ${parser.username}
            Default parameter   "Password" = ${parser.password}
            Optional parameter  "Url"      = ${parser.url}
            Flag                "Retry"    = ${parser.retry}
            Own TypeParser Impl "Data"     = ${parser.data}
    """.trimIndent())

}

class ComplexCommandlineParser(
        args: Array<String>,
        typeParser: Map<KClassifier, TypeParser<*>>
) : KotmandlineParser(args, typeParser) {

    val username: String by keyValueProperty("--uname")

    val password: String by keyValueProperty("--pwd", "ADMIN")

    val url: String? by keyValueProperty("--url")

    val retry: Boolean by flagProperty("-r")

    val data: MyDataClass by keyValueProperty("--data")

}

data class MyDataClass(val value: String)

class MyDataClassParser : TypeParser<MyDataClass> {
    override fun convertArgumentToType(argument: String) = MyDataClass(argument)
}