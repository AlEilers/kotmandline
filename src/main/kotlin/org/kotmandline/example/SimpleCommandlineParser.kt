package org.kotmandline.example

import org.kotmandline.parser.KotmandlineParser

fun main() {

    val args = listOf("--uname", "USERNAME", "-r").toTypedArray()

    val parser = SimpleCommandlineParser(args)

    println("""
            Parameter          "Username" = ${parser.username}
            Default parameter  "Password" = ${parser.password}
            Optional parameter "Url"      = ${parser.url}
            Flag               "Retry"    = ${parser.retry}
    """.trimIndent())

}

class SimpleCommandlineParser(args: Array<String>) : KotmandlineParser(args) {

    val username: String by keyValueProperty("--uname")

    val password: String by keyValueProperty("--pwd", "ADMIN")

    val url: String? by keyValueProperty("--url")

    val retry: Boolean by flagProperty("-r")

}