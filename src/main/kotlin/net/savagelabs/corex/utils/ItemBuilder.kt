package net.savagelabs.corex.utils

import jdk.nashorn.internal.objects.annotations.Getter
import jdk.nashorn.internal.objects.annotations.Setter
import net.prosavage.baseplugin.XMaterial
import net.prosavage.baseplugin.strings.StringProcessor
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class ItemBuilder(@Nullable private val material: XMaterial) {
    // Necessary fields.
    @NotNull private val defaultItem: ItemStack = XMaterial.AIR.parseItem()!!
    @NotNull private val itemStack: ItemStack = material.parseItem() ?: defaultItem
    @NotNull private var itemMeta: ItemMeta = itemStack.itemMeta!!

    /**
     * Set the itemstack's display name.
     *
     * @param name Name to be applied.
     * @return this.
     */
    @Setter @Getter
    fun name(@Nullable name: String?): ItemBuilder {
        // Null checking.
        requireNotNull(name, { "Name cannot be null!" })

        // Handling.
        itemMeta.setDisplayName(StringProcessor.color(name))
        return this
    }

    /**
     * Set the itemstack's lore.
     *
     * @param lore List to be set.
     * @return this.
     */
    @Setter @Getter
    fun lore(@Nullable lore: List<String>?): ItemBuilder {
        // Null checking.
        requireNotNull(lore, { "Lore cannot be null!" })

        // Handling.
        itemMeta.lore = lore.map(StringProcessor::color).toList()
        return this
    }

    /**
     * Set the itemstack's lore.
     *
     * @param loreLines Lines to be set as lore.
     * @return this.
     */
    @Setter @Getter
    fun lore(@Nullable vararg loreLines: String?): ItemBuilder {
        itemMeta.lore = loreLines.filterNotNull().map(StringProcessor::color).toList()
        return this
    }

    /**
     * Adds a line to the lore, THO, if lore is null then it
     * creates an empty list and adds {@see line} to it.
     *
     * @param line Line to be added.
     * @return this.
     */
    @Setter @Getter
    fun loreLine(@Nullable line: String?): ItemBuilder {
        // Null checking.
        requireNotNull(line, { "Line cannot be null!" })

        // Handling.
        val loreList = itemMeta.lore ?: mutableListOf<String>()
        loreList.add(StringProcessor.color(line))
        itemMeta.lore = loreList
        return this
    }

    /**
     * Set the itemstack's amount.
     *
     * @param amount Amount to be set.
     * @return this.
     */
    @Setter @Getter
    fun amount(amount: Int): ItemBuilder {
        itemStack.amount = amount
        return this
    }

    /**
     * Set the itemstack's durability.
     *
     * @param durability Durability to set.
     * @return this.
     */
    @Setter @Getter @Deprecated("1.13 >", ReplaceWith("damage(durability)"))
    fun durability(durability: Int): ItemBuilder {
        itemStack.durability = durability.toShort()
        return this
    }

    /**
     * Set the itemstack's durability (damage).
     *
     * @since 1.13
     *
     * @param damage Damage (durability) to be set.
     * @return this.
     */
    @Setter @Getter
    fun damage(damage: Int): ItemBuilder {
        val damageable = itemMeta as Damageable
        damageable.damage = damage
        itemMeta = damageable as ItemMeta
        return this
    }

    /**
     * Add an enchantment to the itemstack/itemmeta.
     *
     * @param enchantment Enchantment to be added.
     * @param level       Level of the enchantment to be applied.
     * @param metaEnchant Whether or not the enchant should be added to the ItemMeta.
     * @return this.
     */
    @Setter @Getter
    fun enchant(@Nullable enchantment: Enchantment?, level: Int, metaEnchant: Boolean): ItemBuilder {
        // Null checking.
        requireNotNull(enchantment, { "Enchantment cannot be null!" })

        // Handling.
        when (metaEnchant) {
            true -> itemMeta.addEnchant(enchantment, level, true)
            false -> itemStack.addUnsafeEnchantment(enchantment, level)
        }
        return this
    }

    /**
     * Add a map of enchantments to the itemstack/itemmeta.
     *
     * @param enchantmentMap Map of Enchantments to add.
     * @param metaEnchant    Whether or not the enchant should be added to the ItemMeta.
     * @return this.
     */
    @Setter @Getter
    fun enchant(@Nullable enchantmentMap: Map<Enchantment, Int>?, metaEnchant: Boolean): ItemBuilder {
        // Null checking.
        requireNotNull(enchantmentMap, { "Enchantments cannot be null!" })

        // Handling.
        when (metaEnchant) {
            true -> enchantmentMap.forEach { (enchantment, level) ->
                itemMeta.addEnchant(enchantment, level, true)
            }
            false -> enchantmentMap.forEach(itemStack::addUnsafeEnchantment)
        }
        return this
    }

    /**
     * Remove an enchantment from the itemstack/itemmeta.
     *
     * @param enchantment Enchantment to be removed.
     * @param metaEnchant Whether or not the enchant should be removed from the ItemMeta.
     * @return this.
     */
    @Setter @Getter
    fun removeEnchant(@Nullable enchantment: Enchantment?, metaEnchant: Boolean): ItemBuilder {
        // Null checking.
        requireNotNull(enchantment, { "Enchantment cannot be null!" })

        // Handling.
        when (metaEnchant) {
            true -> itemMeta.removeEnchant(enchantment)
            false -> itemStack.removeEnchantment(enchantment)
        }
        return this
    }

    /**
     * Add itemflags to the itemmeta.
     *
     * @param flags ItemFlags to be added.
     * @return this.
     */
    @Setter @Getter
    fun flags(@Nullable vararg flags: ItemFlag?): ItemBuilder {
        itemMeta.addItemFlags(*flags
            .filterNotNull()
            .filter { !itemMeta.hasItemFlag(it) }
            .toTypedArray()
        )
        return this
    }

    /**
     * Remove itemflags from the itemmeta.
     *
     * @param flags ItemFlags to be removed.
     * @return this.
     */
    @Setter @Getter
    fun removeFlags(@Nullable vararg flags: ItemFlag?): ItemBuilder {
        itemMeta.removeItemFlags(*flags
            .filterNotNull()
            .filter(itemMeta::hasItemFlag)
            .toTypedArray()
        )
        return this
    }

    /**
     * Enable or disable glowing effect on the itemstack.
     *
     * @param glow Whether the effect should be enabled or disabled.
     * @return this.
     */
    @Setter @Getter
    fun glow(glow: Boolean): ItemBuilder {
        if (glow) {
            flags(ItemFlag.HIDE_ENCHANTS)
            enchant(Enchantment.DURABILITY, 1, true)
        } else {
            removeFlags(ItemFlag.HIDE_ENCHANTS)
            removeEnchant(Enchantment.DURABILITY, true)
        }
        return this
    }

    /**
     * Build the itemstack from the builder.
     *
     * @return ItemStack that was built.
     */
    @NotNull @Getter
    fun make(): ItemStack {
        itemStack.itemMeta = this.itemMeta
        return this.itemStack
    }
}