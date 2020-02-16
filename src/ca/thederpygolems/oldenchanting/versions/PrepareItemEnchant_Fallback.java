package ca.thederpygolems.oldenchanting.versions;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import ca.thederpygolems.oldenchanting.OldEnchanting;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

/**
 * Fallback for versions 1.11 > ??
 * @author Arnah
 * @since Feb 15, 2020
 */
public class PrepareItemEnchant_Fallback implements PrepareItemEnchant{
	
	private final OldEnchanting plugin;
	
	public PrepareItemEnchant_Fallback(OldEnchanting plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void randomizeSeed(PrepareItemEnchantEvent e){
		try{
			Object container = plugin.getCraftInventoryView().getMethod("getHandle").invoke(e.getView());
			container.getClass().getField("f").set(container, plugin.getRand().nextInt());// Set the enchantment seed
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public void oldEnchantCosts(PrepareItemEnchantEvent e){
		generateNewCosts(e.getOffers(), plugin.getRand(), Math.min(e.getEnchantmentBonus(), 15));
	}
	
	@Override
	public void hideEnchants(PrepareItemEnchantEvent e){
		//mojang does offer.getEnchangement.getId.. need to make that return -1
	}
	
	private void generateNewCosts(EnchantmentOffer[] offers, Random rand, int books){
		// int base = (int) ((rand.nextInt(8) + 1) + Math.floor(books / 2) + rand.nextInt(books));//Before v1.3
		int base = ((rand.nextInt(8) + 1) + (books > 0 ? rand.nextInt((int) Math.floor(books / 2D)) + rand.nextInt(books) : 0));// Randomize the enchant costs
		offers[0].setCost(Math.max(base / 3, 1));
		offers[1].setCost((base * 2) / 3 + 1);
		int first = Math.min(base, books * 2);
		int last = Math.max(base, books * 2);
		offers[2].setCost(ThreadLocalRandom.current().nextInt(first, last) + 1);// Before v1.1
		// table.costs[2] = Math.max(base, books * 2);//Idk what version
	}
}