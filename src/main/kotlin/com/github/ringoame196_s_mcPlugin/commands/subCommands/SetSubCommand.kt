package com.github.ringoame196_s_mcPlugin.commands.subCommands

import com.github.ringoame196_s_mcPlugin.GachaponManager
import com.github.ringoame196_s_mcPlugin.asPlayerOrNull
import com.github.ringoame196_s_mcPlugin.commands.CommandConst
import com.github.ringoame196_s_mcPlugin.getTargetBlockOrNull
import org.bukkit.command.CommandSender

class SetSubCommand : SubCommand {
    override val name: String = CommandConst.SET_COMMAND
    override fun execute(sender: CommandSender, args: Array<out String>) {
        val player = sender.asPlayerOrNull() ?: return
        val targetBlock = player.getTargetBlockOrNull() ?: return
        GachaponManager.openGachaponSetInventory(player, targetBlock)
    }
}
