package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.commands.GachaponCommand
import com.github.ringoame196_s_mcPlugin.events.Events
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
	
    override fun onEnable() {
        super.onEnable()
		
        registerEvents()
        registerCommands()
    }
	
    private fun registerEvents() {
        server.pluginManager.registerEvents(Events(), plugin)
    }

    private fun registerCommands() {
        val command = getCommand("gachapon")
        command?.setExecutor(GachaponCommand())
    }
}
