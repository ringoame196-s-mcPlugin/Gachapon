package com.github.ringoame196_s_mcPlugin

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object GachaponItems {
    val NO_CLICK_ITEM: ItemStack by lazy { makeNoClickItem() }

    fun makeNoClickItem(): ItemStack {
        val item = ItemStack(Material.BARRIER)
        val meta = item.itemMeta
        meta.setDisplayName(" ")
        item.setItemMeta(meta)
        return item
    }
}
