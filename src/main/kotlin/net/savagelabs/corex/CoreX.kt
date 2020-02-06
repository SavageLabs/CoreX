package net.savagelabs.corex

import net.prosavage.baseplugin.SavagePlugin
import net.savagelabs.corex.listeners.BlockEvents
import net.savagelabs.corex.listeners.DamageEvents
import net.savagelabs.corex.persist.Config
import net.savagelabs.corex.persist.Lang

class CoreX : SavagePlugin() {
    /* Triggered when the plugin enables */
    override fun onEnable() {
        super.onEnable()

        /* System */
        Config.load()
        Lang.load()

        /* General */
        this.loadListeners()
    }

    /* Triggered when the plugin disables */
    override fun onDisable() {
        super.onDisable()

        /* System */
        Config.save()
        Lang.save()
    }

    /* Register all plugin listeners */
    private fun loadListeners() {
        this.registerListeners(
            BlockEvents(),
            DamageEvents()
        )
    }
}