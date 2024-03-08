package net.jahcraft.jahpackpokeballs.main;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import net.jahcraft.jahpackpokeballs.commands.Pokeballs;
import net.jahcraft.jahpackpokeballs.listeners.PokeballListeners;
import net.jahcraft.jahpackpokeballs.util.RecipeUtil;

public class Main extends JavaPlugin {
	
	public static Main plugin;

	@Override
	public void onEnable() {
		
		plugin = this;
		
		Pokeballs.createList();
		
		getCommand("pokeballs").setExecutor((CommandExecutor) new Pokeballs());
		getServer().getPluginManager().registerEvents(new PokeballListeners(), this);
		
		RecipeUtil.registerRecipes();
		
	}
	
	@Override
	public void onDisable() {
		RecipeUtil.unregisterRecipes();
	}

}
