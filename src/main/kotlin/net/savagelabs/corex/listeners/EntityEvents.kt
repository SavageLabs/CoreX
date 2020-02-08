package net.savagelabs.corex.listeners

import net.savagelabs.corex.persist.Config
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
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
        val hasSpawner = Config.disabledMobSpawners.map(String::toLowerCase).contains(name)

        // Handling
        if (hasSpawner) event.isCancelled = true
    }

    /**
     * Triggers when entities takes damage.
     * Setup for disabledBurnableItems.
     *
     * @param event EntityDamageEvent
     */
    @EventHandler(ignoreCancelled = true)
    fun onBurn(event: EntityDamageEvent) {
        // Checks
        if (event.entityType != EntityType.DROPPED_ITEM) return

        // Necessity
        val entity = event.entity
        val entityName = entity.name.toLowerCase().replace(" ", "_")
        val hasItem = Config.disabledBurnableItems.map(String::toLowerCase).contains(entityName)

        // Handling
        if (hasItem) event.isCancelled = true
    }
}