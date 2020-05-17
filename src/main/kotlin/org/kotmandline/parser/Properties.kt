package org.kotmandline.parser

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class KotmandlineProperty<T> internal constructor(): ReadOnlyProperty<KotmandlineParser, T> {

    /* Type is already checked on insert */
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: KotmandlineParser, property: KProperty<*>): T {
        return thisRef.getValue(property.name) as T
    }
}

class KeyValueProperty<T> internal constructor(
    private val key: String,
    private val default: T? = null
) {

    operator fun provideDelegate(
            thisRef: KotmandlineParser,
            prop: KProperty<*>
    ): KotmandlineProperty<T> {
        thisRef.addKeyValueProperty(key, prop, default)
        return KotmandlineProperty()
    }

}

class FlagProperty internal constructor(
    private val key: String
) {

    operator fun provideDelegate(
            thisRef: KotmandlineParser,
            prop: KProperty<*>
    ): KotmandlineProperty<Boolean> {
        thisRef.addFlagProperty(key, prop)
        return KotmandlineProperty()
    }

}