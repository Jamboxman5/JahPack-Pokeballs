package net.jahcraft.jahpackpokeballs.listeners;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import net.jahcraft.jahpackpokeballs.pokeballs.Pokeball;
import net.jahcraft.jahpackpokeballs.pokeballs.PokeballType;
import net.jahcraft.jahpackpokeballs.util.NotCatchableException;
import net.jahcraft.jahpackpokeballs.util.PokeballUtil;
import net.md_5.bungee.api.ChatColor;

public class PokeballListeners implements Listener {
	
	HashMap<Player, Long> cooldown = new HashMap<>();
	
	@EventHandler
	public void catcher(PlayerInteractEntityEvent e) {
		if (e.getPlayer() == null) return;
		if (e.getRightClicked() == null) return;
		if (!(e.getRightClicked() instanceof LivingEntity)) return;
		if (e.getPlayer().getInventory().getItemInMainHand() == null) return;
		ItemStack mainHand = e.getPlayer().getInventory().getItemInMainHand();
		if (!PokeballUtil.isPokeball(mainHand)) return;
		if (!PokeballUtil.isEmpty(mainHand)) return;
		LivingEntity caught = (LivingEntity) e.getRightClicked();

		
		if (cooldown.containsKey(e.getPlayer())) {
			if (System.currentTimeMillis() - cooldown.get(e.getPlayer()) < 500) return;
		}
		cooldown.put(e.getPlayer(), System.currentTimeMillis());

		// DO THE THING
		try {
			if (PokeballUtil.rollCatch((LivingEntity) e.getRightClicked(), mainHand)) {
				//SUCCESSFUL CATCH
				Location ballLocation = caught.getLocation();
				mainHand.setAmount(mainHand.getAmount()-1);
				e.getPlayer().sendMessage(caught.getName() + " was captured!");
				ItemStack newBall = new Pokeball(caught, PokeballUtil.getPokeballType(mainHand)).getItem();
				ballLocation.getWorld().dropItemNaturally(ballLocation, newBall);
				caught.remove();

			} else {
				//FAILED CATCH
				e.getPlayer().sendMessage(caught.getName() + " escaped capture!");
				mainHand.setAmount(mainHand.getAmount()-1);

			}
		} catch(NotCatchableException ex) {
			e.getPlayer().sendMessage(ChatColor.RED + "You can't capture that!");

		}
		
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if (e.getRecipe() == null) return;
		if (!PokeballUtil.isPokeball(e.getCurrentItem())) return;
		
		InventoryView iview = e.getView();
		
		if (e.getCurrentItem().getItemMeta().getCustomModelData() == 1002) {
			ItemStack heart = iview.getItem(5);
			if (!PokeballUtil.isPokeball(heart)) {
				e.setCancelled(true);
				e.getWhoClicked().sendMessage(ChatColor.RED + "Get a real pokéball!");
				return;
			}
			if (!PokeballUtil.isEmpty(heart)) {
				e.setCancelled(true);
				e.getWhoClicked().sendMessage(ChatColor.RED + "You can't upgrade a full pokéball!");
				return;
			}
			if (heart.getItemMeta().getCustomModelData() != 1001) {
				e.setCancelled(true);
				e.getWhoClicked().sendMessage(ChatColor.RED + "You're using the wrong type of ball!");
				return;
			}
		}
		
		if (e.getCurrentItem().getItemMeta().getCustomModelData() == 1003) {
			ItemStack heart = iview.getItem(5);
			if (!PokeballUtil.isPokeball(heart)) {
				e.setCancelled(true);
				e.getWhoClicked().sendMessage(ChatColor.RED + "Get a real pokéball!");
				return;
			}
			if (!PokeballUtil.isEmpty(heart)) {
				e.setCancelled(true);
				e.getWhoClicked().sendMessage(ChatColor.RED + "You can't upgrade a full pokéball!");
				return;
			}
			if (heart.getItemMeta().getCustomModelData() != 1002) {
				e.setCancelled(true);
				e.getWhoClicked().sendMessage(ChatColor.RED + "You're using the wrong type of ball!");
				return;
			}
		}
		
		
//		if (e.getClickedInventory().getItem(0).getItemMeta().getCustomModelData() == 1002) {
//			ItemStack pball = e.getClickedInventory().getItem(5);
//			if (!PokeballUtil.isPokeball(pball)) e.setCancelled(true);
//			if (pball.getItemMeta().getCustomModelData() != 1001) e.setCancelled(true);
//			e.getWhoClicked().sendMessage(ChatColor.RED + "Get a real pokéball!");
//		}
//		if (e.getClickedInventory().getItem(0).getItemMeta().getCustomModelData() == 1003) {
//			ItemStack pball = e.getClickedInventory().getItem(5);
//			if (!PokeballUtil.isPokeball(pball)) e.setCancelled(true);
//			if (pball.getItemMeta().getCustomModelData() != 1002) e.setCancelled(true);
//			e.getWhoClicked().sendMessage(ChatColor.RED + "Get a real pokéball!");
//		}
	}
	
	@EventHandler
	public void release(PlayerInteractEvent e) {
		if (e.getPlayer() == null) return;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (!e.getPlayer().isSneaking()) return;
		if (e.getPlayer().getInventory().getItemInMainHand() == null) return;
		ItemStack mainHand = e.getPlayer().getInventory().getItemInMainHand();
		if (!PokeballUtil.isPokeball(mainHand)) return;
		if (PokeballUtil.isEmpty(mainHand)) return;
		
		if (cooldown.containsKey(e.getPlayer())) {
			if (System.currentTimeMillis() - cooldown.get(e.getPlayer()) < 500) return;
		}
		cooldown.put(e.getPlayer(), System.currentTimeMillis());
		
		Location loc = e.getClickedBlock().getRelative(e.getBlockFace()).getLocation();
		loc.add(.5, 0, .5);
		
		PokeballUtil.releaseMob(mainHand, loc);
		e.getPlayer().getInventory().remove(mainHand);
		
		int modData = mainHand.getItemMeta().getCustomModelData();
		if (modData == 1001) loc.getWorld().dropItemNaturally(loc, new Pokeball(null, PokeballType.POKEBALL).getItem());
		else if (modData == 1002) loc.getWorld().dropItemNaturally(loc, new Pokeball(null, PokeballType.GREAT_BALL).getItem());
		else if (modData == 1003) loc.getWorld().dropItemNaturally(loc, new Pokeball(null, PokeballType.ULTRA_BALL).getItem());

		
	}

}
