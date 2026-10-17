package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.GachaponManager
import com.github.ringoame196_s_mcPlugin.GachaponViewGUI
import com.github.ringoame196_s_mcPlugin.Main
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
        val block = checkInteractCondition(e) ?: return
        val player = e.player

        // 管理者がスニークしながら右クリックした場合は、チェストを普通に開かせるため処理を抜ける（イベントをキャンセルしない）
        if (player.isSneaking && player.hasPermission(Main.ADMIN_PERMISSION)) {
            return
        }

        val gachaponData = GachaponManager.getGachaponData(block) ?: return
        e.isCancelled = true

        if (block.inventory.isEmpty) {
            player.sendMessage("在庫がありません")
            return
        }

        val gachaponGUI = GachaponViewGUI(gachaponData)
        gachaponGUI.openGUI(player)
    }

    private fun checkInteractCondition(e: PlayerInteractEvent): Container? {
        if (e.action != Action.RIGHT_CLICK_BLOCK) return null
        if (e.hand != EquipmentSlot.HAND) return null
        return e.clickedBlock?.state as? Container
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
