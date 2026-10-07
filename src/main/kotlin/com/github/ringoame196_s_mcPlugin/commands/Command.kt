package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.GachaponManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class Command : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false
        val subCommand = args[0]

        when (subCommand) {
            CommandConst.ADD_COMMAND -> addCommand(sender)
        }

        return true
    }

    private fun addCommand(sender: CommandSender) {
        if (sender !is Player) {
            val message = "このコマンドはプレイヤーのみ実行可能です"
            sender.sendMessage(message)
            return
        }

        val targetBlock = GachaponManager.getTargetBlock(sender)

        if (targetBlock == null) {
            val message = "ブロックが指定されていません"
            sender.sendMessage(message)
            return
        }

        if (GachaponManager.isGachapon(targetBlock)) {
            val message = "既に登録されています"
            sender.sendMessage(message)
            return
        }
        GachaponManager.addGachapon(targetBlock)

        val message = "登録しました"
        sender.sendMessage(message)
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> mutableListOf(
                CommandConst.ADD_COMMAND,
                CommandConst.REMOVE_COMMAND
            )
            else -> mutableListOf()
        }
    }
}
