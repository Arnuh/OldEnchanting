package ca.thederpygolems.oldenchanting.versions;

import java.util.Random;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryView;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

import ca.thederpygolems.oldenchanting.OldEnchanting;
import net.minecraft.server.v1_12_R1.ContainerEnchantTable;

/**
 * @Author Borlea
 * @Github https://github.com/borlea/
 * @Website http://codingforcookies.com/
 * @since May 24, 2017
 */
public class PrepareItemEnchant_1_12_R1 implements PrepareItemEnchant{

	private Random rand = new Random();
	private final OldEnchanting plugin;

	public PrepareItemEnchant_1_12_R1(OldEnchanting plugin){
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
		generateNewCosts(table.costs, rand, Math.min(e.getEnchantmentBonus(), 15));
	}

	@Override
	public void hideEnchants(PrepareItemEnchantEvent e){
		CraftInventoryView view = (CraftInventoryView) e.getView();
		ContainerEnchantTable table = (ContainerEnchantTable) view.getHandle();
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()-> {// Remove the display of what enchantment you will get
			clearArray(table.h);
		}, 1);
	}
}