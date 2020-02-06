package net.savagelabs.corex

import net.prosavage.baseplugin.SavagePlugin
import net.savagelabs.corex.persist.Config

class CoreX : SavagePlugin() {
    override fun onEnable() {
        super.onEnable()
        Config.load()
    }

    override fun onDisable() {
        super.onDisable()
        Config.save()
    }
}