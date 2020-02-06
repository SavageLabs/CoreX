package net.savagelabs.corex.listeners

import net.savagelabs.corex.persist.Config
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockIgniteEvent

/**
 * Created on February, 06, 2020
 *
 * @author RarLab
 */
class BlockEvents : Listener {
    /**
     * Triggered when a block ignites.
     *
     * @param event BlockIgniteEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onIgnite(event: BlockIgniteEvent) {
        // Necessity
        val cause = event.cause
        val spreadCause = BlockIgniteEvent.IgniteCause.SPREAD

        // Check
        if (Config.antiFireSpread && cause == spreadCause) {
            // Handling
            event.isCancelled = true
        }
    }
}