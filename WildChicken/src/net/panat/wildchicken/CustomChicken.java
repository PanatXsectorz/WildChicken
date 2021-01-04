package net.panat.wildchicken;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.bukkit.attribute.Attribute;

import net.minecraft.server.v1_16_R3.AttributeModifiable;
import net.minecraft.server.v1_16_R3.EntityChicken;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_16_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_16_R3.World;


public class CustomChicken extends EntityChicken {
	private static Field attributeField;
	public CustomChicken(World world) {
		super(EntityTypes.CHICKEN, world);
        try {
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
	}
	
    public void registerGenericAttribute(org.bukkit.entity.Entity entity, Attribute attribute) throws IllegalAccessException {
        net.minecraft.server.v1_16_R3.AttributeMapBase attributeMapBase = ((org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity)entity).getHandle().getAttributeMap();
        Map<net.minecraft.server.v1_16_R3.AttributeBase, net.minecraft.server.v1_16_R3.AttributeModifiable> map = (Map<net.minecraft.server.v1_16_R3.AttributeBase, net.minecraft.server.v1_16_R3.AttributeModifiable>) attributeField.get(attributeMapBase);
        net.minecraft.server.v1_16_R3.AttributeBase attributeBase = org.bukkit.craftbukkit.v1_16_R3.attribute.CraftAttributeMap.toMinecraft(attribute);
        net.minecraft.server.v1_16_R3.AttributeModifiable attributeModifiable = new net.minecraft.server.v1_16_R3.AttributeModifiable(attributeBase, net.minecraft.server.v1_16_R3.AttributeModifiable::getAttribute);
        map.put(attributeBase, attributeModifiable);
    }
	
	public void initPathfinder() {
		
        this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ATTACK_DAMAGE, (a) -> {a.setValue(10.0);}));
        this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.FOLLOW_RANGE, (a) -> {a.setValue(1.0);}));
     
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(1, new PathfinderGoalRandomLookaround(this));
		this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 1.0, true));
		this.goalSelector.a(3, new PathfinderGoalRandomStroll(this, 2.0D));
		this.goalSelector.a(4, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		this.targetSelector.a(3,new PathfinderGoalHurtByTarget(this));
	}
	
    static {
        try {
            attributeField = net.minecraft.server.v1_16_R3.AttributeMapBase.class.getDeclaredField("b");
            attributeField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}