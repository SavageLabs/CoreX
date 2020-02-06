package net.savagelabs.corex.persist

import net.prosavage.baseplugin.serializer.Serializer

object Config {
    @Transient private val instance = this

    /* Save the config */
    fun save() = Serializer().save(instance)

    /* Load the config */
    fun load() = Serializer().load(instance, Config::class.java, "config")
}