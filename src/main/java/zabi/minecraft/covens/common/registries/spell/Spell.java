package zabi.minecraft.covens.common.registries.spell;

import zabi.minecraft.covens.common.lib.Reference;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

public abstract class Spell extends IForgeRegistryEntry.Impl<Spell> {
	
	public static final IForgeRegistry<Spell> REGISTRY = new RegistryBuilder<Spell>().setName(new ResourceLocation(Reference.MID, "witch_spells")).setType(Spell.class).setIDRange(0, 200).create();
	
	int color,cost;
	String name;
	EnumSpellType type;
	
	public Spell(int cost, int color, EnumSpellType type, String name, String mod_id) {
		this.color = color;
		this.name = name;
		this.type = type;
		this.cost = cost;
		this.setRegistryName(mod_id, name);
	}
	
	public String getName() {
		return name;
	}
	
	public int getColor() {
		return color;
	}
	
	public int getCost() {
		return cost;
	}
	
	public EnumSpellType getType() {
		return type;
	}
	
	public abstract void performEffect(RayTraceResult rtrace, EntityLivingBase caster, World world);
	public abstract boolean canBeUsed(World world, BlockPos pos, EntityLivingBase caster);
	
	public static enum EnumSpellType {
		INSTANT, PROJECTILE_BLOCK, PROJECTILE_ENTITY, PROJECTILE_ALL
	}
}
