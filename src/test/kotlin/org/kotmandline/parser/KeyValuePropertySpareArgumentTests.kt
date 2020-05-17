package org.kotmandline.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KeyValuePropertySpareArgumentTests {

    @Test
    fun parse_with_spare_argument() {
        val spareArguemnt = "--spare"
        val parameter = listOf("--name", "test", spareArguemnt)

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val name: String by keyValueProperty("--name")
        }

        assertEquals(parameter[1], unitUnderTest.name)
        assertTrue(unitUnderTest.hasSpareArguments())

        val spareArguments = unitUnderTest.getSpareArguments()
        assertEquals(1, spareArguments.size)
        assertEquals(spareArguemnt, spareArguments[0])
    }

    @Test
    fun parse_without_spare_argument() {
        val parameter = listOf("--name", "test")

        val unitUnderTest = object : KotmandlineParser(parameter.toTypedArray()) {
            val name: String by keyValueProperty("--name")
        }

        assertEquals(parameter[1], unitUnderTest.name)
        assertFalse(unitUnderTest.hasSpareArguments())

        val spareArguments = unitUnderTest.getSpareArguments()
        assertEquals(0, spareArguments.size)
    }
}