package com.github.ringoame196_s_mcPlugin

import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

class GachaponItemMeta(val rawMeta: ItemMeta) {
    private val idKey = NamespacedKey(Main.plugin, "gachapon_item_key")

    // 型を String? から GachaponItemId? に変更
    var id: GachaponItemId?
        get() {
            val rawId = rawMeta.persistentDataContainer.get(idKey, PersistentDataType.STRING)
            return GachaponItemId.fromId(rawId)
        }
        set(value) {
            if (value == null) {
                rawMeta.persistentDataContainer.remove(idKey)
            } else {
                // Enumの持っている文字列（value.id）をPDCに保存する
                rawMeta.persistentDataContainer.set(idKey, PersistentDataType.STRING, value.id)
            }
        }
}

val ItemMeta.gachapon: GachaponItemMeta
    get() = GachaponItemMeta(this)
