package org.kotmandline.parser

import kotlin.reflect.KClassifier
import kotlin.reflect.KProperty

abstract class KotmandlineParser(
    args: Array<String>,
    typeParser: Map<KClassifier, TypeParser<*>> = emptyMap()
) {
    private val arguments = args.toMutableList()
    private val properties = mutableMapOf<String, Any?>()
    private val parser : Map<KClassifier, TypeParser<*>>

    init {
        parser = mutableMapOf<KClassifier, TypeParser<*>>(
            String::class to StringParser(),
            Int::class to IntParser(),
            Long::class to LongParser(),
            Float::class to FloatParser(),
            Double::class to DoubleParser()
        )

        parser.putAll(typeParser)
    }

    //<editor-fold desc="KeyValueProperty">
    fun <T> keyValueProperty(
        key: String,
        default: T? = null
    ) = KeyValueProperty<T>(key, default)

    internal fun addKeyValueProperty(key: String, property: KProperty<*>, default: Any?) {

        val value = getValueFromArguments(key, property)
        if (value != null) {
            properties[property.name] = value
            return
        }

        if (default != null) {
            properties[property.name] = default
            return
        }

        if (property.returnType.isMarkedNullable) {
            properties[property.name] = null
            return
        }

        throw IllegalArgumentException("Key [$key] not found in Parameters")
    }

    private fun getValueFromArguments(key: String, property: KProperty<*>) : Any? {
        for (i in 0 until arguments.size - 1) {
            if (key == arguments[i]) {
                val value = getArgumentAsType(arguments[i+1], property.returnType.classifier)
                arguments.removeAt(i+1)
                arguments.removeAt(i)
                return value
            }
        }
        return null
    }

    // The type is checked inside the TypeParser
    @Suppress("UNCHECKED_CAST")
    private fun getArgumentAsType(argument: String, type: KClassifier?) : Any {
        val value = try {
            val typeParser = parser[type]
            if (typeParser == null) {
                throw IllegalArgumentException("Unsupported Property-Type: $type. Please add TypeParser for this type")
            }

            typeParser.convertArgumentToType(argument)
        } catch (exception: Exception) {
            throw IllegalArgumentException("Cannot parse Value [$argument] to Type [$type]", exception)
        }

        if (value == null) {
            throw IllegalStateException("TypeParser of type {$type] returned null")
        }

        if(value::class != type){
            throw IllegalStateException("TypeParser of type {$type] returned value of type [${value::class}]")
        }

        return value
    }
    //</editor-fold>

    //<editor-fold desc="FlagProperty">
    fun flagProperty(key: String) = FlagProperty(key)

    internal fun addFlagProperty(key: String, property: KProperty<*>) {
        for (i in 0 until arguments.size) {
            if (key == arguments[i]) {
                properties[property.name] = true
                arguments.removeAt(i)
                return
            }
        }

        properties[property.name] = false
    }
    //</editor-fold>

    //<editor-fold desc="SpareArguments">
    fun hasSpareArguments() : Boolean {
        return arguments.isNotEmpty()
    }

    fun getSpareArguments() : Array<String> {
        return arguments.toTypedArray()
    }
    //</editor-fold>

    internal fun getValue(key: String) : Any? {
        return properties[key]
    }
}

