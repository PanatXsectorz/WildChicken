package net.panat.wildchicken;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_16_R3.EntityChicken;


public class core extends JavaPlugin {
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new Evt(), this);
		//Registry nms = new Registry();
		//nms.registerEntity("Chicken", 93, EntityChicken.class, CustomChicken.class);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
		    @Override
		    public void run() {
		        for(Player p : Bukkit.getOnlinePlayers()) {
		        	List<Entity> nearbyEntites = (List<Entity>) p.getLocation().getWorld().getNearbyEntities(p.getLocation(), 2, 2, 2);
		        	
		        	for(Entity ent : nearbyEntites) {
		        		if(ent instanceof Chicken) {
		        			if(((Chicken) ent).getTarget() != null) {
		        				if(((Chicken) ent).getTarget() instanceof Player) {
		        					Player target = (Player) ((Chicken) ent).getTarget();
		        					target.damage(2.0D);
		        				}
		        			}
		        		}
		        	}
		        }
		    }
		}, 0L, 20L);
	}
	
	public void onDisable() {
		
	}

}
