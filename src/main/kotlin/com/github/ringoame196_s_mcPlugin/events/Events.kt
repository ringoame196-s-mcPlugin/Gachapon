package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.GachaponManager
import org.bukkit.block.Container
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class Events : Listener {
    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val block = e.clickedBlock?.state as? Container ?: return
        val player = e.player
        if (e.action != Action.RIGHT_CLICK_BLOCK) return
        GachaponManager.getGachaponData(block) ?: return
        e.isCancelled = true
        GachaponManager.openGachaponInventory(player)
    }
}
