package zabi.minecraft.covens.common.registries.spell;

import zabi.minecraft.covens.common.lib.Reference;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zabi.minecraft.covens.common.registries.spell.Spell.EnumSpellType;
import zabi.minecraft.covens.common.registries.spell.spells.SpellActivation;
import zabi.minecraft.covens.common.registries.spell.spells.SpellBlink;
import zabi.minecraft.covens.common.registries.spell.spells.SpellDestabilization;
import zabi.minecraft.covens.common.registries.spell.spells.SpellDisarming;
import zabi.minecraft.covens.common.registries.spell.spells.SpellInfuseLife;
import zabi.minecraft.covens.common.registries.spell.spells.SpellLesserBlinking;
import zabi.minecraft.covens.common.registries.spell.spells.SpellMagnet;
import zabi.minecraft.covens.common.registries.spell.spells.SpellPoke;
import zabi.minecraft.covens.common.registries.spell.spells.SpellSelfHeal;
import zabi.minecraft.covens.common.registries.spell.spells.SpellSlowness;
import zabi.minecraft.covens.common.registries.spell.spells.SpellWater;

public class ModSpells {
	
	public static Spell magnet, poke, water, activation, slowness, lesser_blink, blink, explosion, disarming, infuse_life, self_heal;
	
	public static void registerAll() {
		MinecraftForge.EVENT_BUS.register(new ModSpells());
		magnet = new SpellMagnet(1, 0xa5cec9, EnumSpellType.PROJECTILE_BLOCK, "magnet", Reference.MID);
		poke = new SpellPoke(1, 0x6d1e10, EnumSpellType.PROJECTILE_ALL, "poke", Reference.MID);
		water = new SpellWater(2, 0x1644a7, EnumSpellType.PROJECTILE_BLOCK, "water", Reference.MID);
		activation = new SpellActivation(1, 0x42b3bd, EnumSpellType.PROJECTILE_BLOCK, "activation", Reference.MID);
		slowness = new SpellSlowness(10, 0x4d6910, EnumSpellType.PROJECTILE_ENTITY, "slowness", Reference.MID);
		lesser_blink = new SpellLesserBlinking(5, 0x9042bd, EnumSpellType.INSTANT, "lesser_blink", Reference.MID);
		blink = new SpellBlink(10, 0xcb33e7, EnumSpellType.PROJECTILE_BLOCK, "blink", Reference.MID);
		explosion = new SpellDestabilization(12, 0x5e0505, EnumSpellType.PROJECTILE_ALL, "explosion", Reference.MID);
		disarming = new SpellDisarming(15, 0xffbb7c, EnumSpellType.PROJECTILE_ALL, "disarming", Reference.MID);
		infuse_life = new SpellInfuseLife(5, 0xf6546a, EnumSpellType.PROJECTILE_ALL, "infuse_life", Reference.MID);
		self_heal = new SpellSelfHeal(4, 0xd20057, EnumSpellType.INSTANT, "self_heal", Reference.MID);
	}
	
	@SubscribeEvent
	public void onSpellRegistration(RegistryEvent.Register<Spell> evt) {
		evt.getRegistry().registerAll(
				magnet, poke, water, activation, slowness, lesser_blink, blink, explosion, disarming, infuse_life, self_heal
		);
	}
	
}
