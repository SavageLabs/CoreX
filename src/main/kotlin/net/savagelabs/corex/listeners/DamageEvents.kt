package net.savagelabs.corex.listeners

import net.prosavage.baseplugin.JSONMessage
import net.prosavage.baseplugin.strings.StringProcessor
import net.savagelabs.corex.persist.Config
import net.savagelabs.corex.persist.Lang
import net.savagelabs.corex.utils.Placeholders
import org.bukkit.entity.Arrow
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

/**
 * Created on February, 06, 2020
 *
 * @author RarLab
 */
class DamageEvents : Listener {
    /**
     * Triggers when entities hit each other.
     * Setup for antiPvPWorlds.
     *
     * @param event EntityDamageByEntityEvent
     */
    @EventHandler
    fun onAntiDamage(event: EntityDamageByEntityEvent) {
        // Checks
        if (event.entity !is Player || event.damager !is Player || Config.antiPvPWorlds.isEmpty()) return

        // Handling - Not case-sensitive
        val world = event.damager.world.name.toLowerCase()
        val hasWorld = Config.antiPvPWorlds.map(String::toLowerCase).contains(world)

        if (hasWorld) event.isCancelled = true
    }

    /**
     * Triggers when entities hit each other.
     * Setup for damageInformation.
     *
     * @param event EntityDamageByEntityEvent
     */
    @EventHandler
    fun onInformative(event: EntityDamageByEntityEvent) {
        // Checks
        if (event.isCancelled || !Config.damageInformation || (event.damager !is Player && event.damager !is Arrow)) return

        // Handling
        val attacked = event.entity as LivingEntity
        val player = when {
            event.damager is Player -> event.damager as Player

            (event.damager as Arrow).shooter is Player -> {
                (event.damager as Arrow).shooter as Player
            }

            else -> return
        }

        val processedMessage = Placeholders.process(attacked, Lang.damageInformation)
        JSONMessage.actionbar(StringProcessor.color(processedMessage), player)
    }
}