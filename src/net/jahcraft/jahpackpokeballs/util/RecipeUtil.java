package net.jahcraft.jahpackpokeballs.util;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import net.jahcraft.jahpackpokeballs.pokeballs.PokeballRecipes;

public class RecipeUtil {

	public static HashSet<Recipe> registeredRecipes;
	public static HashSet<NamespacedKey> recipeKeys;
	
	public static void registerRecipes() {
		PokeballRecipes.registerRecipes();
		
		registeredRecipes = collectRecipes();
		recipeKeys = collectRecipeKeys();
	}
	
	public static void unregisterRecipes() {
		for (Recipe r : registeredRecipes) {
			if (r instanceof ShapedRecipe) {
				Bukkit.removeRecipe(((ShapedRecipe) r).getKey());
			}
			if (r instanceof ShapelessRecipe) {
				Bukkit.removeRecipe(((ShapelessRecipe) r).getKey());
			}
		}
	}
	
	private static HashSet<Recipe> collectRecipes() {
		HashSet<Recipe> recipes = new HashSet<>();
		
		recipes.addAll(PokeballRecipes.recipes);
		
		return recipes;
	}
	
	private static HashSet<NamespacedKey> collectRecipeKeys() {
		HashSet<NamespacedKey> keys = new HashSet<>();
		
		for (Recipe rep : registeredRecipes) {
			if (rep instanceof ShapedRecipe) {
				keys.add(((ShapedRecipe)rep).getKey());
			}
			if (rep instanceof ShapelessRecipe) {
				keys.add(((ShapelessRecipe)rep).getKey());
			}
		}
		
		return keys;
	}
	
}
