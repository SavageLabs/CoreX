package net.savagelabs.corex.persist

import net.prosavage.baseplugin.serializer.Serializer

object Config {
    @Transient private val instance = this

    /* Fields -> Blocks Related */
    var antiFireSpread = false

    /* Fields -> Damage Related */
    var antiPvPWorlds = mutableListOf<String>()
    var damageInformation = false

    /* Fields -> Entity Related */
    var disabledMobSpawners = mutableListOf<String>()

    /* Save the config */
    fun save() = Serializer().save(instance)

    /* Load the config */
    fun load() = Serializer().load(instance, Config::class.java, "config")!!
}