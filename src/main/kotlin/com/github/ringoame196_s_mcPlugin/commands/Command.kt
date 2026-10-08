package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.GachaponManager
import com.github.ringoame196_s_mcPlugin.asPlayerOrNull
import com.github.ringoame196_s_mcPlugin.getTargetBlockOrNull
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class Command : CommandExecutor, TabCompleter {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val subCommand = args.firstOrNull() ?: return false

        when (subCommand) {
            CommandConst.ADD_COMMAND -> addCommand(sender)
            CommandConst.REMOVE_COMMAND -> removeCommand(sender)
            CommandConst.OPEN_COMMAND -> openCommand(sender)
            else -> return false
        }

        return true
    }

    private fun addCommand(sender: CommandSender) {
        val player = sender.asPlayerOrNull() ?: return
        val targetBlock = player.getTargetBlockOrNull() ?: return

        if (GachaponManager.isGachapon(targetBlock)) {
            player.sendMessage("既に登録されています")
            return
        }

        GachaponManager.addGachapon(targetBlock)
        player.sendMessage("登録しました")
    }

    private fun removeCommand(sender: CommandSender) {
        val player = sender.asPlayerOrNull() ?: return
        val targetBlock = player.getTargetBlockOrNull() ?: return

        if (!GachaponManager.isGachapon(targetBlock)) {
            player.sendMessage("登録されていません")
            return
        }

        GachaponManager.removeGachapon(targetBlock)
        player.sendMessage("削除しました")
    }

    private fun openCommand(sender: CommandSender) {
        val player = sender.asPlayerOrNull() ?: return
        val targetBlock = player.getTargetBlockOrNull() ?: return

        if (!GachaponManager.isGachapon(targetBlock)) {
            player.sendMessage("ガチャのブロックのみ閲覧可能です")
            return
        }

        player.openInventory(targetBlock.inventory)
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) {
            val subCommands = listOf(
                CommandConst.ADD_COMMAND,
                CommandConst.REMOVE_COMMAND,
                CommandConst.OPEN_COMMAND
            )
            return subCommands.filter { it.startsWith(args[0], ignoreCase = true) }
        }
        return emptyList()
    }
}
