package org.kotmandline.parser

import kotlin.reflect.KClassifier
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class KeyValuePropertyTypeParserTests {

    data class MyType(val value: String)

    class MyTypeParser : TypeParser<MyType> {
        override fun convertArgumentToType(argument: String): MyType {
            return MyType(argument)
        }
    }

    @Test
    fun add_new_TypeParser_KeyValueProperty() {
        val parameter = listOf("--value", "123")
        val typeParser = mapOf<KClassifier, TypeParser<*>>(MyType::class to MyTypeParser())

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray(), typeParser) {
            val value: MyType by keyValueProperty("--value")
        }

        assertEquals(MyType(parameter[1]), unitUnderTest.value)
    }

    class MyStringTypeParser(private val prefix: String) : TypeParser<String> {
        override fun convertArgumentToType(argument: String): String {
            return prefix + argument
        }
    }

    @Test
    fun replace_StringTypeParser_KeyValueProperty() {
        val prefix = "XXX"
        val parameter = listOf("--value", "123")
        val typeParser = mapOf<KClassifier, TypeParser<*>>(String::class to MyStringTypeParser(prefix))

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray(), typeParser) {
            val value: String by keyValueProperty("--value")
        }

        assertEquals(prefix + parameter[1], unitUnderTest.value)
    }

    @Test
    fun add_faulty_TypeParser_KeyValueProperty() {
        assertFailsWith<IllegalStateException> {
            val parameter = listOf("--value", "ABCD")
            val typeParser = mapOf<KClassifier, TypeParser<*>>(String::class to MyTypeParser())

            val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray(), typeParser) {
                val value: String by keyValueProperty("--value")
            }

            assertEquals(parameter[1], unitUnderTest.value)
        }
    }
}