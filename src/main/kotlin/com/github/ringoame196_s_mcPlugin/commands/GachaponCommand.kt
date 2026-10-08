package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.commands.subCommands.AddSubCommand
import com.github.ringoame196_s_mcPlugin.commands.subCommands.OpenSubCommand
import com.github.ringoame196_s_mcPlugin.commands.subCommands.RemoveSubCommand
import com.github.ringoame196_s_mcPlugin.commands.subCommands.SubCommand

class GachaponCommand : BaseCommand() {
    override val subCommands: Map<String, SubCommand> = mapOf(
        CommandConst.ADD_COMMAND to AddSubCommand(),
        CommandConst.REMOVE_COMMAND to RemoveSubCommand(),
        CommandConst.OPEN_COMMAND to OpenSubCommand()
    )
}
