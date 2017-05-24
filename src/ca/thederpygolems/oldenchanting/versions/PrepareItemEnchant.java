package ca.thederpygolems.oldenchanting.versions;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

/**
 * @Author Borlea
 * @Github https://github.com/borlea/
 * @Website http://codingforcookies.com/
 * @since Nov 27, 2015
 */
public interface PrepareItemEnchant{

	// 1.9 and higher has an array named 'i' which stores the levels instead of shifting. -1 should work for all versions.

	public void randomizeSeed(PrepareItemEnchantEvent e);

	public void oldEnchantCosts(PrepareItemEnchantEvent e);

	public void hideEnchants(PrepareItemEnchantEvent e);

	public default void generateNewCosts(int[] costs, Random rand, int books){
		// int base = (int) ((rand.nextInt(8) + 1) + Math.floor(books / 2) + rand.nextInt(books));//Before v1.3
		int base = (int) ((rand.nextInt(8) + 1)
				+ (books > 0 ? rand.nextInt((int) Math.floor(books / 2))
						+ rand.nextInt(books)
						: 0));// Randomize the enchant costs
		costs[0] = Math.max(base / 3, 1);
		costs[1] = (base * 2) / 3 + 1;
		int first = Math.min(base, books * 2);
		int last = Math.max(base, books * 2);
		costs[2] = ThreadLocalRandom.current().nextInt(first, last) + 1;// Before v1.1
		// table.costs[2] = Math.max(base, books * 2);//Idk what version
	}

	public default void clearArray(int[] array){// Since we can't use a mutable array and I want shit clean
		for(int in = 0; in < array.length; in++)
			array[in] = -1;
	}
}
