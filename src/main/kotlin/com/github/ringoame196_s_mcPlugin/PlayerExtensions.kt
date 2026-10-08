package com.github.ringoame196_s_mcPlugin

import org.bukkit.block.Container
import org.bukkit.entity.Player

fun Player.getTargetBlockOrNull(): Container? {
    val targetBlock = GachaponManager.getTargetBlock(this)
    if (targetBlock == null) {
        this.sendMessage("ブロックが指定されていません")
    }
    return targetBlock
}
