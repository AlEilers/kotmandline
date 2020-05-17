package org.kotmandline.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class KeyValuePropertyDefaultTypeParserTests {

    @Test
    fun parse_Int_KeyValueProperty() {
        val parameter = listOf("--value", "123")

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val value: Int by keyValueProperty("--value")
        }

        assertEquals(parameter[1].toInt(), unitUnderTest.value)
    }

    @Test
    fun parse_invalid_Int_KeyValueProperty() {
        assertFailsWith<IllegalArgumentException> {
            val parameter = listOf("--value", "ABCD")

            val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
                val value: Int by keyValueProperty("--value")
            }

            assertEquals(parameter[1].toInt(), unitUnderTest.value)
        }
    }

    @Test
    fun parse_Long_KeyValueProperty() {
        val parameter = listOf("--value", Long.MAX_VALUE.toString())

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val value: Long by keyValueProperty("--value")
        }

        assertEquals(parameter[1].toLong(), unitUnderTest.value)
    }

    @Test
    fun parse_Float_KeyValueProperty() {
        val parameter = listOf("--value", Float.MAX_VALUE.toString())

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val value: Float by keyValueProperty("--value")
        }

        assertEquals(parameter[1].toFloat(), unitUnderTest.value)
    }

    @Test
    fun parse_Double_KeyValueProperty() {
        val parameter = listOf("--value", Double.MAX_VALUE.toString())

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val value: Double by keyValueProperty("--value")
        }

        assertEquals(parameter[1].toDouble(), unitUnderTest.value)
    }
}