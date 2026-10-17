package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.commands.GachaponCommand
import com.github.ringoame196_s_mcPlugin.events.Events
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        // 外部クラスから Main.plugin でアクセスできるようにする
        lateinit var plugin: Main
            private set
        const val ADMIN_PERMISSION = "gachapon.admin"
    }

    override fun onEnable() {
        plugin = this
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
