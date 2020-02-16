package ca.thederpygolems.oldenchanting.versions;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

/**
 * @author Arnah
 * @since Nov 27, 2015
 */
public interface PrepareItemEnchant{
	
	// 1.9 and higher has an array named 'i' which stores the levels instead of shifting. -1 should work for all versions.
	
	void randomizeSeed(PrepareItemEnchantEvent e);
	
	void oldEnchantCosts(PrepareItemEnchantEvent e);
	
	void hideEnchants(PrepareItemEnchantEvent e);
	
	default void generateNewCosts(int[] costs, Random rand, int books){
		// int base = (int) ((rand.nextInt(8) + 1) + Math.floor(books / 2) + rand.nextInt(books));//Before v1.3
		int base = ((rand.nextInt(8) + 1) + (books > 0 ? rand.nextInt((int) Math.floor(books / 2D)) + rand.nextInt(books) : 0));// Randomize the enchant costs
		costs[0] = Math.max(base / 3, 1);
		costs[1] = (base * 2) / 3 + 1;
		int first = Math.min(base, books * 2);
		int last = Math.max(base, books * 2);
		costs[2] = ThreadLocalRandom.current().nextInt(first, last) + 1;// Before v1.1
		// table.costs[2] = Math.max(base, books * 2);//Idk what version
	}
}