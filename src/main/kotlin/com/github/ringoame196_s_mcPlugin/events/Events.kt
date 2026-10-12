package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.GachaponData
import com.github.ringoame196_s_mcPlugin.GachaponManager
import org.bukkit.block.Container
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

class Events : Listener {
    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val block = e.clickedBlock?.state as? Container ?: return
        val player = e.player
        if (e.action != Action.RIGHT_CLICK_BLOCK) return
        if (e.hand != EquipmentSlot.HAND) return
        val item = player.inventory.itemInMainHand
        val gachaponData = GachaponManager.getGachaponData(block) ?: return
        e.isCancelled = true

        if (canExecute(item, gachaponData)) {
            GachaponManager.openGachaponInventory(player)
        } else {
            player.sendMessage("必要なアイテムがありません")
        }
    }

    private fun canExecute(item: ItemStack?, gachaponData: GachaponData): Boolean {
        val requiredItem = gachaponData.consumableItem ?: return true
        if (item == null) return false
        return item.isSimilar(requiredItem) && item.amount >= requiredItem.amount
    }
}
