package ca.thederpygolems.oldenchanting;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.event.enchantment.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import ca.thederpygolems.oldenchanting.versions.*;

public class OldEnchanting extends JavaPlugin implements Listener, CommandExecutor{

	private boolean lapis, hideEnchant, oldEnchantCosts, randomizeEnchants;
	private PrepareItemEnchant event;
	String version;

	public void onEnable(){
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("oldenchanting").setExecutor(this);
		load(false);
        try {
            version = getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
		}catch(ArrayIndexOutOfBoundsException ex){}
		if(!setupVersionInterface()){
			getLogger().severe("Failed to setup OldEnchanting for version: " + version + ". Please report this asap");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}

	public boolean setupVersionInterface(){
		if(version == null) return false;
		if(version.equals("v1_8_R3")){
			event = new PrepareItemEnchant_1_8_R3(this);
        }
		return event != null;
	}

	@EventHandler
	public void prepareItemEnchant(PrepareItemEnchantEvent e){
		if(randomizeEnchants) event.randomizeSeed(e);
		if(oldEnchantCosts) event.oldEnchantCosts(e);
		if(hideEnchant) event.hideEnchants(e);
	}

	@EventHandler
	public void enchantItem(EnchantItemEvent e){
		if(lapis || oldEnchantCosts){
			getServer().getScheduler().scheduleSyncDelayedTask(this, ()-> {// Fix up removing 1,2,3 levels depending on tier, and restock.
				if(oldEnchantCosts) e.getEnchanter().setLevel(e.getEnchanter().getLevel() - (e.getExpLevelCost() - (64 - e.getInventory().getItem(1).getAmount())));
				if(lapis) e.getInventory().setItem(1, new ItemStack(Material.INK_SACK, 64, (short) 4));
			}, 1);
		}
	}

	@EventHandler
	public void lapisClickEvent(InventoryClickEvent e){// Prevent them from stealing lapis
		if(!lapis) return;
		if(e.getClickedInventory() != null && e.getClickedInventory().getType().equals(InventoryType.ENCHANTING)) {
			if(e.getRawSlot() == 1) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void inventoryOpen(InventoryOpenEvent e){// Give lapis
		if(!lapis) return;
		if(e.getInventory() != null && e.getInventory().getType().equals(InventoryType.ENCHANTING)) {
			e.getInventory().setItem(1, new ItemStack(Material.INK_SACK, 64, (short) 4));
		}
	}

	@EventHandler
	public void inventoryClose(InventoryCloseEvent e){// Remove lapis so it doesn't drop on the ground.
		if(!lapis) return;
		if(e.getInventory() != null && e.getInventory().getType().equals(InventoryType.ENCHANTING)) {
			e.getInventory().setItem(1, null);
		}
	}

	public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args){
		if(args.length > 0){
			if(args[0].equalsIgnoreCase("reload")){
				load(true);
				sender.sendMessage(ChatColor.GREEN + "OldEnchanting config reloaded.");
			}
		}
		return false;
	}

	public void load(boolean reload){
		if(reload) reloadConfig();
		lapis = getConfig().getBoolean("lapis");
		hideEnchant = getConfig().getBoolean("hideEnchant");
		oldEnchantCosts = getConfig().getBoolean("oldEnchantCosts");
		randomizeEnchants = getConfig().getBoolean("randomizeEnchants");
	}
}