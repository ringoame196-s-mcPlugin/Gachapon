package com.github.ringoame196_s_mcPlugin

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun CommandSender.asPlayerOrNull(): Player? {
    if (this is Player) return this
    this.sendMessage("このコマンドはプレイヤーのみ実行可能です")
    return null
}
