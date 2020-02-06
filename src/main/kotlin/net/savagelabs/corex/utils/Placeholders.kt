package net.savagelabs.corex.utils

import jdk.nashorn.internal.objects.annotations.Getter
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * Created on February, 06, 2020
 *
 * @author RarLab
 */
object Placeholders {
    /**
     * Process all placeholders in a string.
     *
     * @param entity LivingEntity to process information from.
     * @param line   String to be processed.
     * @return Processed String.
     */
    @Getter
    fun process(@Nullable entity: LivingEntity?, @NotNull line: String): String {
        // Null checking
        requireNotNull(entity, { "Entity cannot be null!" })

        // Handling
        var message = line
        var lastStartChar = -1
        var lastEndChar = -1

        for ((index, char) in line.withIndex()) {
            if (char == '{') lastStartChar = index
            if (char == '}') lastEndChar = index
            if (lastStartChar == -1 || lastEndChar == -1) continue

            var placeholder = ""
            for (charIndex in lastStartChar + 1 until lastEndChar) {
                placeholder += line[charIndex]
            }

            val result = processHolders(entity, placeholder)
            if (result != placeholder) message = message.replace("{$placeholder}", result)

            lastStartChar = -1
            lastEndChar = -1
        }

        return message
    }

    /**
     * Process a specific placeholder.
     *
     * @param entity      Entity to process information from.
     * @param placeholder Placeholder to process.
     * @return The corresponding string from the placeholder.
     */
    @Getter
    private fun processHolders(@NotNull entity: LivingEntity, @NotNull placeholder: String): String {
        return when (placeholder) {
            // Placeholders to be processed and returned.
            "name" -> entity.name
            "health" -> entity.health.toInt().toString()
            "max_health" -> entity.maxHealth.toInt().toString()

            // Return the checked placeholder if none found.
            else -> placeholder
        }
    }
}