package net.panat.wildchicken;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.minecraft.server.v1_16_R3.World;


public class Evt implements Listener{
	
	@EventHandler
	public void onSpawnMob(CreatureSpawnEvent e) {
		if(e.getEntity() instanceof Chicken) {
			if(e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
				spawn(e.getLocation());
				e.setCancelled(true);
			}
		}
	}
	
    public static Chicken spawn(Location loc){
        World mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
        final CustomChicken customEnt = new CustomChicken(mcWorld);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity) customEnt.getBukkitEntity()).setRemoveWhenFarAway(false); //Do we want to remove it when the NPC is far away? I won
        mcWorld.addEntity(customEnt, SpawnReason.CUSTOM);
        return (Chicken) customEnt.getBukkitEntity();
    }
    
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
    	if(e.getDamager() instanceof Player) {
    		if(e.getEntity() instanceof Chicken) {
    			Player p = (Player) e.getDamager();
    			List<Entity> nearbyEntites = (List<Entity>) p.getLocation().getWorld().getNearbyEntities(p.getLocation(), 10, 10, 10);
	        	for(Entity ent : nearbyEntites) {
	        		if(ent instanceof Chicken) {
	        			((Chicken) ent).setTarget(p);
	        		}
	        	}
    		}
    	}
    }
}
