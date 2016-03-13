package ca.thederpygolems.oldenchanting.versions;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftInventoryView;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

import ca.thederpygolems.oldenchanting.OldEnchanting;
import net.minecraft.server.v1_9_R1.ContainerEnchantTable;

/**
 * @Author Borlea
 * @Github https://github.com/borlea/
 * @Website http://codingforcookies.com/
 * @since Mar 1, 2016
 */
public class PrepareItemEnchant_1_9_R1 implements PrepareItemEnchant{

	private Random rand = new Random();

	OldEnchanting plugin;

	public PrepareItemEnchant_1_9_R1(OldEnchanting plugin){
		this.plugin = plugin;
	}

	@Override
	public void randomizeSeed(PrepareItemEnchantEvent e){
		CraftInventoryView view = (CraftInventoryView) e.getView();
		ContainerEnchantTable table = (ContainerEnchantTable) view.getHandle();
		table.f = rand.nextInt();// Set the enchantment seed
	}

	@Override
	public void oldEnchantCosts(PrepareItemEnchantEvent e){
		CraftInventoryView view = (CraftInventoryView) e.getView();
		ContainerEnchantTable table = (ContainerEnchantTable) view.getHandle();
		int books = e.getEnchantmentBonus() > 15 ? 15 : e.getEnchantmentBonus();// Books
		// int base = (int) ((rand.nextInt(8) + 1) + Math.floor(books / 2) + rand.nextInt(books));//Before v1.3
		int base = (int) ((rand.nextInt(8) + 1) + (books > 0 ? rand.nextInt((int) Math.floor(books / 2)) + rand.nextInt(books) : 0));// Randomize the enchant costs
		table.costs[0] = Math.max(base / 3, 1);
		table.costs[1] = (base * 2) / 3 + 1;
		table.costs[2] = ThreadLocalRandom.current().nextInt(base, books * 2) + 1;// Before v1.1
		// table.costs[2] = Math.max(base, books * 2);//Idk what version
	}

	@Override
	public void hideEnchants(PrepareItemEnchantEvent e){
		CraftInventoryView view = (CraftInventoryView) e.getView();
		ContainerEnchantTable table = (ContainerEnchantTable) view.getHandle();
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()-> {// Remove the display of what enchantment you will get
			table.h[0] = (-1 | 0 << 8);
			table.h[1] = (-1 | 0 << 8);
			table.h[2] = (-1 | 0 << 8);
		}, 1);
	}
}
