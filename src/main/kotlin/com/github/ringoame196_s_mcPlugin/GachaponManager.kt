package com.github.ringoame196_s_mcPlugin

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Container
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object GachaponManager {
    private const val MAX_TARGET_BLOCK_DISTANCE = 10
    private val gachaponMap = mutableMapOf<Location, GachaponData>()
    val setInventory = mutableMapOf<Inventory, Container>()
    val gachaponInventory = mutableMapOf<Inventory, GachaponPlayer>()
    private const val GUI_SIZE = 9
    private const val GUI_TITLE = "ガチャガチャ画面"
    private const val SET_GUI_TITLE = "消費アイテムセット画面"
    private const val SET_GUI_SIZE = 9
    private val NO_CLICK_ITEM: ItemStack by lazy { makeNoClickItem() }
    private const val CONSUMABLE_ITEM_SLOT = 3
    private const val SPIN_SLOT = 5
    private val SPIN_ITEM: ItemStack by lazy { makeSpinItem() }
    private const val SPIN_ITEM_NAME = "回す"

    fun getTargetBlock(player: Player): Container? {
        val targetBlock = player.getTargetBlockExact(MAX_TARGET_BLOCK_DISTANCE) ?: return null
        return targetBlock.state as? Container
    }

    fun setGachapon(block: Container, consumableItem: ItemStack?) {
        gachaponMap[block.location] = GachaponData(
            block,
            consumableItem
        )
    }

    fun removeGachapon(block: Container) {
        gachaponMap.remove(block.location)
    }

    fun isGachapon(block: Container): Boolean {
        return block.location in gachaponMap
    }

    fun getGachaponData(block: Container): GachaponData? {
        return gachaponMap[block.location]
    }

    fun openGachaponInventory(player: Player, gachaponData: GachaponData) {
        val gui = makeGachaponInventory(gachaponData)
        player.openInventory(gui)
        gachaponInventory[gui] = GachaponPlayer(player, gachaponData)
    }

    private fun makeGachaponInventory(gachaponData: GachaponData): Inventory {
        val gui = Bukkit.createInventory(null, GUI_SIZE, GUI_TITLE)
        val consumableItem = gachaponData.consumableItem
        gui.setItem(CONSUMABLE_ITEM_SLOT, consumableItem)
        gui.setItem(SPIN_SLOT, SPIN_ITEM)
        return gui
    }

    fun openGachaponSetInventory(player: Player, block: Container) {
        val gui = Bukkit.createInventory(null, SET_GUI_SIZE, SET_GUI_TITLE)
        for (i in 0 until SET_GUI_SIZE) {
            if (i == SET_GUI_SIZE / 2) continue
            gui.setItem(i, NO_CLICK_ITEM)
        }
        player.openInventory(gui)
        setInventory[gui] = block
    }

    private fun makeNoClickItem(): ItemStack {
        val item = ItemStack(Material.BARRIER)
        val meta = item.itemMeta
        meta.setDisplayName(" ")
        meta.gachapon.id = GachaponItemId.NO_CLICK_ITEM
        item.setItemMeta(meta)
        return item
    }

    private fun makeSpinItem(): ItemStack {
        val item = ItemStack(Material.SLIME_BALL)
        val meta = item.itemMeta
        meta.setDisplayName(SPIN_ITEM_NAME)
        meta.gachapon.id = GachaponItemId.SPIN_ITEM
        item.setItemMeta(meta)
        return item
    }
}
