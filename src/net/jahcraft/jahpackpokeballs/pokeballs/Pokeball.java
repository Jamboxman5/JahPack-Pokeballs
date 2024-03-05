package net.jahcraft.jahpackpokeballs.pokeballs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import net.jahcraft.jahpackpokeballs.util.PokeballUtil;
import net.md_5.bungee.api.ChatColor;

public class Pokeball extends NBTItem {
	
	public Pokeball(LivingEntity contains, PokeballType type) {
		
		super(new ItemStack(Material.HEART_OF_THE_SEA));
		
		ItemMeta meta = getItem().getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "Pokéball");
		
		switch(type) {
		case POKEBALL:
			meta.setCustomModelData(1001);
			break;
		case GREAT_BALL:
			meta.setCustomModelData(1002);
			break;
		case ULTRA_BALL:
			meta.setCustomModelData(1003);
			break;
		}
		
		
		if (contains != null) {
			
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "Type: " + ChatColor.YELLOW + PokeballUtil.getFormattedName(contains.getType()));
			if (!contains.getName().equals(PokeballUtil.getFormattedName(contains.getType()))) {
				lore.add(ChatColor.GRAY + "Name: " + ChatColor.YELLOW + contains.getCustomName());
			}
			lore.add(ChatColor.GRAY + "Health: " + ChatColor.YELLOW + contains.getHealth()+ ChatColor.GRAY + "/" + ChatColor.YELLOW + contains.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			meta.setLore(lore);
			
			getItem().setItemMeta(meta);
			
			setString("caughtType", contains.getType().toString());
			setString("caughtName", contains.getName());
			setDouble("caughtHealth", contains.getHealth());
			
			
		} else {
			meta.setLore(getDefaultLore());
			getItem().setItemMeta(meta);

		}
		

		
	}
	
	
	public static List<String> getDefaultLore() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Right-click to capture a mob!");
		lore.add(ChatColor.GRAY + "Shift right-click to release a mob!");
		return lore;
	}

}
