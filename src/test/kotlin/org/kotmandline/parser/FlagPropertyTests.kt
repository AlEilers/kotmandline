package org.kotmandline.parser

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FlagPropertyTests {

    @Test
    fun parse_FlagProperty_given() {
        val parameter = listOf("--enabled")

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val enabled: Boolean by flagProperty("--enabled")
        }

        assertTrue(unitUnderTest.enabled)
    }

    @Test
    fun parse_FlagProperty_not_given() {
        val parameter = emptyList<String>()

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val enabled: Boolean by flagProperty("--enabled")
        }

        assertFalse(unitUnderTest.enabled)
    }

    @Test
    fun parse_Nullable_FlagProperty_given() {
        val parameter = listOf("--enabled")

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val enabled: Boolean? by flagProperty("--enabled")
        }

        val value = unitUnderTest.enabled
        assertNotNull(value)
        assertTrue(value)
    }

    @Test
    fun parse_Nullable_FlagProperty_not_given() {
        val parameter = emptyList<String>()

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val enabled: Boolean? by flagProperty("--enabled")
        }

        val value = unitUnderTest.enabled
        assertNotNull(value)
        assertFalse(value)
    }
}