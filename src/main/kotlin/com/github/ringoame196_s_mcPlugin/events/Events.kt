package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.GachaponManager
import com.github.ringoame196_s_mcPlugin.GachaponViewGUI
import org.bukkit.block.Container
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class Events : Listener {
    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val block = e.clickedBlock?.state as? Container ?: return
        val player = e.player
        if (e.action != Action.RIGHT_CLICK_BLOCK) return
        if (e.hand != EquipmentSlot.HAND) return
        val gachaponData = GachaponManager.getGachaponData(block) ?: return
        e.isCancelled = true
        val gachaponGUI = GachaponViewGUI(gachaponData)
        gachaponGUI.openGUI(player)
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val clickInventory = e.clickedInventory ?: return
        val gachaponGUI = GachaponManager.gachaponGUI[clickInventory] ?: return
        gachaponGUI.handleClick(e)
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        val clickInventory = e.inventory
        val gachaponGUI = GachaponManager.gachaponGUI[clickInventory] ?: return
        gachaponGUI.handleClose(e)
    }
}
