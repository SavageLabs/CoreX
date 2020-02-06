package net.savagelabs.corex.persist

import net.prosavage.baseplugin.serializer.Serializer

/**
 * Created on February, 06, 2020
 *
 * @author RarLab
 */
object Lang {
    @Transient private val instance = this

    /* Fields -> Damage Related */
    var damageInformation = "&b{name} &7has &b{health}/{max_health} &7health left."

    /* Save the config */
    fun save() = Serializer().save(instance)

    /* Load the config */
    fun load() = Serializer().load(instance, Lang::class.java, "lang")!!
}