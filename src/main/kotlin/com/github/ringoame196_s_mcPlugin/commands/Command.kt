package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.commands.subCommands.AddSubCommand
import com.github.ringoame196_s_mcPlugin.commands.subCommands.OpenSubCommand
import com.github.ringoame196_s_mcPlugin.commands.subCommands.RemoveSubCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class Command : CommandExecutor, TabCompleter {
    private val subCommands = mapOf(
        CommandConst.ADD_COMMAND to AddSubCommand(),
        CommandConst.REMOVE_COMMAND to RemoveSubCommand(),
        CommandConst.OPEN_COMMAND to OpenSubCommand()
    )

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val subCommandName = args.firstOrNull() ?: return false
        val subCommand = subCommands[subCommandName] ?: return false

        val subArgs = args.drop(1).toTypedArray()
        subCommand.execute(sender, subArgs)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.isEmpty()) return emptyList()

        val rawCandidates = if (args.size == 1) {
            subCommands.keys.toList()
        } else {
            val subCommand = subCommands[args[0]] ?: return emptyList()
            val subArgs = args.drop(1).toTypedArray()
            subCommand.tabComplete(sender, subArgs)
        }

        val currentInput = args.last()
        return rawCandidates.filter { it.startsWith(currentInput, ignoreCase = true) }
    }
}
