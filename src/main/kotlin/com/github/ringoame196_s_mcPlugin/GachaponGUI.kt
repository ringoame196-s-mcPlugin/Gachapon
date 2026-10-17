package com.github.ringoame196_s_mcPlugin

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

interface GachaponGUI {
    fun openGUI(player: Player)
    fun handleClick(e: InventoryClickEvent)
    fun handleClose(e: InventoryCloseEvent) {}
}
