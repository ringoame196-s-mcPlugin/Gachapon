package com.github.ringoame196_s_mcPlugin

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Container
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import kotlin.collections.set

class GachaponSetGUI(val block: Container) : GachaponGUI {
    private val noClickItem: ItemStack = makeNoClickItem()

    override fun openGUI(player: Player) {
        val gui = Bukkit.createInventory(null, GachaponConstants.GACHAPON_SET_GUI_SIZE, GachaponConstants.GACHAPON_SET_GUI_TITLE)
        for (i in 0 until GachaponConstants.GACHAPON_SET_GUI_SIZE) {
            if (i == GachaponConstants.GACHAPON_SET_GUI_SIZE / 2) continue
            gui.setItem(i, noClickItem)
        }
        player.openInventory(gui)
        GachaponManager.gachaponGUI[gui] = this
    }

    private fun makeNoClickItem(): ItemStack {
        val item = ItemStack(Material.BARRIER)
        val meta = item.itemMeta
        meta.setDisplayName(" ")
        meta.gachapon.id = GachaponItemId.NO_CLICK_ITEM
        item.setItemMeta(meta)
        return item
    }

    override fun handleClick(e: InventoryClickEvent) {
        val item = e.currentItem ?: return
        val gachaponId = item.itemMeta.gachapon.id ?: return
        if (gachaponId == GachaponItemId.NO_CLICK_ITEM) {
            e.isCancelled = true
        }
    }

    override fun handleClose(e: InventoryCloseEvent) {
        val player = e.player as? Player ?: return
        val inv = e.inventory
        val item = inv.getItem(inv.size / 2)
        val gachaponGUI = GachaponManager.gachaponGUI[inv] ?: return
        if (gachaponGUI !is GachaponSetGUI) return
        val targetBlock = gachaponGUI.block
        GachaponManager.setGachapon(targetBlock, item)
        if (item != null) {
            player.inventory.addItem(item)
        }

        sendSetGachaponMessage(player, item)
        GachaponManager.gachaponGUI.remove(inv)
    }

    private fun sendSetGachaponMessage(player: Player, item: ItemStack?) {
        player.sendMessage("ガチャガチャ登録しました")
        val message = if (item == null) {
            "消費アイテム:未設定"
        } else {
            val itemMeta = item.itemMeta
            val itemInfo = if (itemMeta?.hasDisplayName() == true) {
                itemMeta.displayName
            } else {
                item.type.name
            }
            "消費アイテム:$itemInfo 個数:${item.amount}"
        }
        player.sendMessage(message)
    }
}
