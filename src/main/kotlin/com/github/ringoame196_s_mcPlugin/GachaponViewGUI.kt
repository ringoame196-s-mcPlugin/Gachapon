package com.github.ringoame196_s_mcPlugin

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class GachaponViewGUI(val gachaponData: GachaponData) : GachaponGUI {
    private val spinItem = makeSpinItem()

    override fun openGUI(player: Player) {
        val gui = makeGachaponInventory(gachaponData)
        player.openInventory(gui)
        GachaponManager.gachaponGUI[gui] = this
    }

    private fun makeGachaponInventory(gachaponData: GachaponData): Inventory {
        val gui = Bukkit.createInventory(null, GachaponConstants.GACHAPON_VIEW_SIZE, GachaponConstants.GACHAPON_VIEW_TITLE)
        val consumableItem = gachaponData.consumableItem
        gui.setItem(GachaponConstants.CONSUMABLE_ITEM_SLOT, consumableItem)
        gui.setItem(GachaponConstants.SPIN_SLOT, spinItem)
        return gui
    }

    private fun makeSpinItem(): ItemStack {
        val item = ItemStack(Material.SLIME_BALL)
        val meta = item.itemMeta
        meta.setDisplayName(GachaponConstants.SPIN_ITEM_NAME)
        meta.gachapon.id = GachaponItemId.SPIN_ITEM
        item.setItemMeta(meta)
        return item
    }

    override fun handleClick(e: InventoryClickEvent) {
        e.isCancelled = true
    }
}
