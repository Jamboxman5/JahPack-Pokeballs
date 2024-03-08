package net.jahcraft.jahpackpokeballs.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import net.jahcraft.jahpackpokeballs.pokeballs.Pokeball;
import net.jahcraft.jahpackpokeballs.pokeballs.PokeballType;

public class PokeballUtil {

	public static List<ItemStack> itemGetter = getItems();
		
	private static List<ItemStack> getItems() {

		List<ItemStack> items = new ArrayList<>();
		
		items.add(new Pokeball(null, PokeballType.POKEBALL).getItem());
		items.add(new Pokeball(null, PokeballType.GREAT_BALL).getItem());
		items.add(new Pokeball(null, PokeballType.ULTRA_BALL).getItem());
		
		return items;
	}
	
	public static boolean isPokeball(ItemStack itemInMainHand) {
		if (itemInMainHand == null) return false;
		if (itemInMainHand.getType() != Material.HEART_OF_THE_SEA) return false;
		if (!itemInMainHand.hasItemMeta()) return false;
		if (!itemInMainHand.getItemMeta().hasCustomModelData()) return false;
		if (itemInMainHand.getItemMeta().getCustomModelData() <= 1000) return false;
		if (itemInMainHand.getItemMeta().getCustomModelData() >= 1100) return false;
		return true;
	}
	
	public static PokeballType getPokeballType(ItemStack pokeball) {
		int index = pokeball.getItemMeta().getCustomModelData()%1000;
		switch(index) {
		case 1: 
			return PokeballType.POKEBALL;
		case 2: 
			return PokeballType.GREAT_BALL;
		case 3: 
			return PokeballType.ULTRA_BALL;
		default:
			return null;
		}	
	}
	
	public static boolean isEmpty(ItemStack pokeball) {
		NBTItem NBTBall = new NBTItem(pokeball);
		
		return NBTBall.getString("caughtType").equals("");
	}
	
	public static double getCaptureBonus(ItemStack pokeball) {
		int index = pokeball.getItemMeta().getCustomModelData()%1000;
		switch(index) {
		case 1: 
			return 1.0;
		case 2: 
			return 1.2;
		case 3: 
			return 1.5;
		default:
			return 0;
		}
	}
	
	public static double getCaptureRate(EntityType type) {
		switch(type) {
		case CHICKEN:
			return 255;
		case COD:
			return 255;
		case SALMON:
			return 255;
		case PUFFERFISH:
			return 127;
		case TROPICAL_FISH:
			return 60;
		case SHEEP:
			return 200;
		case COW:
			return 200;
		case HORSE:
			return 200;
		case PIG:
			return 200;
		case RABBIT:
			return 200;
		case ZOMBIE:
			return 140;
		case HUSK:
			return 140;
		case SKELETON:
			return 140;
		case STRAY:
			return 140;
		case SPIDER:
			return 140;
		case CAVE_SPIDER:
			return 100;
		case SILVERFISH:
			return 255;
		case BLAZE:
			return 40;
		case WITHER_SKELETON:
			return 40;
		case GHAST:
			return 20;
		case WARDEN:
			return 3;
		case WITHER:
			return 10;
		default:
			return -1;
		}
	}

	public static boolean rollCatch (LivingEntity mob, ItemStack pokeball) throws NotCatchableException {
		double maxHealth = mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		double health = mob.getHealth();
		
		double numerator = ((3*maxHealth)-(2*health));
		double denominator = (3*maxHealth);
		
		double captureRate = getCaptureRate(mob.getType());
		double captureBonus = getCaptureBonus(pokeball);
		
		if (captureRate == -1) throw new NotCatchableException();
		
		double alpha = ((numerator / denominator) * captureRate * captureBonus);
		double beta = 1048560 / Math.sqrt(Math.sqrt(16711680/alpha));
		
		if ((Math.random()*65535) > beta) return false;
		if ((Math.random()*65535) > beta) return false;
		if ((Math.random()*65535) > beta) return false;
		if ((Math.random()*65535) > beta) return false;
		
		return true;
		
	}

	public static void releaseMob(ItemStack pokeball, Location loc) {
		NBTItem NBTBall = new NBTItem(pokeball);
		EntityType type = EntityType.valueOf(NBTBall.getString("caughtType"));
		String name = NBTBall.getString("caughtName");
		double health = NBTBall.getDouble("caughtHealth");
		
		LivingEntity spawned = (LivingEntity) loc.getWorld().spawnEntity(loc, type);
		if (!name.equals(spawned.getName())) spawned.setCustomName(name);
		spawned.setHealth(health);
		
	}
	
	public static String getFormattedName(EntityType type) {
		
		String newNameBase;
		String nameSpaced = "";
		
		newNameBase = type.toString().toLowerCase();
		
		for (String s : newNameBase.split("_")) {
			nameSpaced = nameSpaced + s.substring(0,1).toUpperCase() + s.substring(1) + " ";
		}
		
		return nameSpaced.substring(0, nameSpaced.length()-1);
	}
	
}