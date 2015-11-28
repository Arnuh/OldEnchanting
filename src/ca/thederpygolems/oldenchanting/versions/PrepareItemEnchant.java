package ca.thederpygolems.oldenchanting.versions;

import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

/**
 * @Author Borlea
 * @Github https://github.com/borlea/
 * @Website http://codingforcookies.com/
 * @since Nov 27, 2015
 */
public interface PrepareItemEnchant{

	public void randomizeSeed(PrepareItemEnchantEvent e);

	public void oldEnchantCosts(PrepareItemEnchantEvent e);

	public void hideEnchants(PrepareItemEnchantEvent e);
}
