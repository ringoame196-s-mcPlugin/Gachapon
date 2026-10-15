package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.commands.subCommands.OpenSubCommand
import com.github.ringoame196_s_mcPlugin.commands.subCommands.RemoveSubCommand
import com.github.ringoame196_s_mcPlugin.commands.subCommands.SetSubCommand
import com.github.ringoame196_s_mcPlugin.commands.subCommands.SubCommand

class GachaponCommand : BaseCommand() {
    override val subCommands: Map<String, SubCommand> = listOf(
        SetSubCommand(),
        RemoveSubCommand(),
        OpenSubCommand()
    ).associateBy { it.name }
}
