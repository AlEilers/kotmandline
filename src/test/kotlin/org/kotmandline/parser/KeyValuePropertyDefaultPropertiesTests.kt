package org.kotmandline.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class KeyValuePropertyDefaultPropertiesTests {

    @Test
    fun parse_String_KeyValueProperty_given_no_default() {
        val parameter = listOf("--name", "test")

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val name: String by keyValueProperty("--name")
        }

        assertEquals(parameter[1], unitUnderTest.name)
    }

    @Test
    fun parse_String_KeyValueProperty_not_given_no_default() {
        assertFailsWith<IllegalArgumentException> {
            val parameter = emptyList<String>()

            object : KotmandlineParser(parameter.toTypedArray()) {
                val name: String by keyValueProperty("--name")
            }
        }
    }

    @Test
    fun parse_String_KeyValueProperty_given_default() {
        val default = "XXXX"
        val parameter = listOf("--name", "test")

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val name: String by keyValueProperty("--name", default)
        }

        assertEquals(parameter[1], unitUnderTest.name)
    }

    @Test
    fun parse_String_KeyValueProperty_not_given_default() {
        val default = "test"
        val parameter = emptyList<String>()

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val name: String by keyValueProperty("--name", default)
        }

        assertEquals(default, unitUnderTest.name)
    }

    @Test
    fun parse_Nullable_String_KeyValueProperty() {
        val parameter = listOf("--name", "test")

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val name: String? by keyValueProperty("--name")
        }

        assertEquals(parameter[1], unitUnderTest.name)
    }

    @Test
    fun parse_Nullable_String_KeyValueProperty_not_given() {
        val parameter = emptyList<String>()

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val name: String? by keyValueProperty("--name")
        }

        assertNull(unitUnderTest.name)
    }

    @Test
    fun parse_Nullable_String_KeyValueProperty_not_given_default() {
        val default = "test"
        val parameter = emptyList<String>()

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val name: String? by keyValueProperty("--name", default)
        }

        assertEquals(default, unitUnderTest.name)
    }
}