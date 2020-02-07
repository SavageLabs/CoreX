package net.savagelabs.corex.listeners

import net.savagelabs.corex.persist.Config
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.SpawnerSpawnEvent

class EntityEvents : Listener {
    /**
     * Triggers when entities spawn.
     * Setup for disabledMobSpawners.
     *
     * @param event CreatureSpawnEvent
     */
    @EventHandler(ignoreCancelled = true)
    fun onSpawn(event: SpawnerSpawnEvent) {
        // Necessity
        val type = event.entityType
        val name = type.name.toLowerCase()

        // Checking
        if (Config.disabledMobSpawners.map(String::toLowerCase).contains(name)) {
            // Handling
            event.isCancelled = true
        }
    }
}