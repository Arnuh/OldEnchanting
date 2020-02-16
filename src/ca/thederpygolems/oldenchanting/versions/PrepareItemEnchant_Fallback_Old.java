package ca.thederpygolems.oldenchanting.versions;

import java.util.Arrays;
import ca.thederpygolems.oldenchanting.OldEnchanting;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

/**
 * Fallback for versions 1.8.8 > 1.10.
 * @author Arnah
 * @since Feb 15, 2020
 */
public class PrepareItemEnchant_Fallback_Old implements PrepareItemEnchant{
	
	private final OldEnchanting plugin;
	
	public PrepareItemEnchant_Fallback_Old(OldEnchanting plugin){
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
		generateNewCosts(e.getExpLevelCostsOffered(), plugin.getRand(), Math.min(e.getEnchantmentBonus(), 15));
	}
	
	@Override
	public void hideEnchants(PrepareItemEnchantEvent e){
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()->{// Remove the display of what enchantment you will get
			try{
				Object container = plugin.getCraftInventoryView().getMethod("getHandle").invoke(e.getView());
				int[] enchants = (int[]) container.getClass().getField("h").get(container);
				Arrays.fill(enchants, -1);//In old versions this is enchantid | level << 8
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}, 1);
	}
}
