package com.github.ringoame196_s_mcPlugin

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Container
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object GachaponManager {
    private const val MAX_TARGET_BLOCK_DISTANCE = 10
    private val gachaponMap = mutableMapOf<Location, GachaponData>()
    val gachaponGUI = mutableMapOf<Inventory, GachaponGUI>()

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

    fun play(player: Player, gachaponData: GachaponData, isRemove: Boolean) {
        val block = gachaponData.block
        val inventory = block.inventory

        val rewardItem = getRandomReward(inventory) ?: run {
            player.sendMessage("在庫がありません")
            return
        }

        val consumable = gachaponData.consumableItem
        if (consumable != null && !removeInventoryItem(player, consumable)) {
            player.sendMessage("必要アイテムが不足しています")
            return
        }

        if (isRemove) {
            removeStockItem(inventory, rewardItem)
        }

        player.inventory.addItem(rewardItem)
        player.sendMessage("ガチャを回しました！")
    }

    /** ランダムな報酬を1つ取得する */
    private fun getRandomReward(inventory: Inventory): ItemStack? {
        val validItems = inventory.contents
            .filterNotNull()
            .filter { it.type != Material.AIR }

        if (validItems.isEmpty()) return null

        return validItems.random().clone().apply { amount = 1 }
    }

    /** チェストの在庫から指定のアイテムを1つ減らすまたは削除する */
    private fun removeStockItem(inventory: Inventory, targetItem: ItemStack) {
        for ((index, item) in inventory.withIndex()) {
            if (item != null && item.isSimilar(targetItem)) {
                if (item.amount > 1) {
                    item.amount -= 1
                    inventory.setItem(index, item)
                } else {
                    inventory.setItem(index, null)
                }
                break
            }
        }
    }

    private fun removeInventoryItem(player: Player, consumableItem: ItemStack?): Boolean {
        consumableItem ?: return true
        val requiredAmount = consumableItem.amount
        var currentAmount = 0

        for (item in player.inventory) {
            if (item == null || item.type.isAir) continue

            if (item.isSimilar(consumableItem)) {
                currentAmount += item.amount
            }
        }

        if (currentAmount < requiredAmount) {
            return false
        }

        val removalItem = consumableItem.clone().apply { amount = requiredAmount }
        player.inventory.removeItem(removalItem)

        return true
    }
}
