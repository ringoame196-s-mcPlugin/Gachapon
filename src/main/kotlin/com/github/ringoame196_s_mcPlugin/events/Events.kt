package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.GachaponItemId
import com.github.ringoame196_s_mcPlugin.GachaponManager
import com.github.ringoame196_s_mcPlugin.gachapon
import org.bukkit.block.Container
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
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
        val gachaponData = GachaponManager.getGachaponData(block) ?: return
        e.isCancelled = true
        GachaponManager.openGachaponInventory(player, gachaponData)
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val clickInventory = e.clickedInventory ?: return
        when (clickInventory) {
            in GachaponManager.setInventory -> onSetGachaponInventory(e)
            in GachaponManager.gachaponInventory -> onGachaponInventory(e)
        }
    }

    private fun onSetGachaponInventory(e: InventoryClickEvent) {
        val item = e.currentItem ?: return
        val gachaponId = item.itemMeta.gachapon.id ?: return
        if (gachaponId == GachaponItemId.NO_CLICK_ITEM) {
            e.isCancelled = true
        }
    }

    private fun onGachaponInventory(e: InventoryClickEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        val player = e.player as? Player ?: return
        val inv = e.inventory
        val item = inv.getItem(inv.size / 2)
        val targetBlock = GachaponManager.setInventory[inv] ?: return
        GachaponManager.setGachapon(targetBlock, item)
        if (item != null) {
            player.inventory.addItem(item)
        }

        sendSetGachaponMessage(player, item)
    }

    private fun sendSetGachaponMessage(player: Player, item: ItemStack?) {
        player.sendMessage("ガチャガチャ登録しました")
        val message: String
        if (item == null) {
            message = "消費アイテム:未設定"
        } else {
            val itemInfo: String = if (item.itemMeta.hasDisplayName()) {
                item.itemMeta.displayName
            } else {
                item.type.name
            }
            message = "消費アイテム:$itemInfo 個数:${item.amount}"
        }
        player.sendMessage(message)
    }
}
