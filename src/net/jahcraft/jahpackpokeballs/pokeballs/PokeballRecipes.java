package net.jahcraft.jahpackpokeballs.pokeballs;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import net.jahcraft.jahpackpokeballs.main.Main;

public class PokeballRecipes {

	public static HashSet<Recipe> recipes = new HashSet<>();
	public static HashSet<NamespacedKey> keys = new HashSet<>();
	
	public static void registerRecipes() {
		
		Recipe recipe = generatePokeballRecipe();
		recipes.add(recipe);
		recipe = generateGreatballRecipe();
		recipes.add(recipe);
		recipe = generateUltraballRecipe();
		recipes.add(recipe);
		
		for (Recipe r : recipes) {
			Bukkit.addRecipe(r);
		}
	}
	public static ShapedRecipe generatePokeballRecipe() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "pokeball_recipe");
		ShapedRecipe recipe = new ShapedRecipe(key, new Pokeball(null, PokeballType.POKEBALL).getItem());
		recipe.shape("III","IBI", "III");
		recipe.setIngredient('B', Material.STONE_BUTTON);
		recipe.setIngredient('I', Material.IRON_INGOT);
		keys.add(key);
		return recipe;
	}
	public static ShapedRecipe generateGreatballRecipe() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "greatball_recipe");
		ShapedRecipe recipe = new ShapedRecipe(key, new Pokeball(null, PokeballType.GREAT_BALL).getItem());
		recipe.shape("RDR","LPL", "III");
		recipe.setIngredient('R', Material.REDSTONE);
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('L', Material.LAPIS_LAZULI);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('P', Material.HEART_OF_THE_SEA);
		keys.add(key);
		return recipe;
	}
	public static ShapedRecipe generateUltraballRecipe() {
		NamespacedKey key = new NamespacedKey(Main.plugin, "ultraball_recipe");
		ShapedRecipe recipe = new ShapedRecipe(key, new Pokeball(null, PokeballType.ULTRA_BALL).getItem());
		recipe.shape("GSG","GPG", "III");
		recipe.setIngredient('G', Material.GOLD_INGOT);
		recipe.setIngredient('S', Material.NETHERITE_SCRAP);
		recipe.setIngredient('P', Material.HEART_OF_THE_SEA);
		recipe.setIngredient('I', Material.IRON_INGOT);
		keys.add(key);
		return recipe;
	}

}
