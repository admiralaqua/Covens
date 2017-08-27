package zabi.minecraft.covens.common.registries.ritual;

import zabi.minecraft.covens.common.lib.Log;
import zabi.minecraft.covens.common.lib.Reference;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;
import zabi.minecraft.covens.common.block.BlockCircleGlyph;
import zabi.minecraft.covens.common.block.BlockCircleGlyph.GlyphType;
import zabi.minecraft.covens.common.item.ModItems;
import zabi.minecraft.covens.common.registries.ritual.rituals.RitualEnderGate;
import zabi.minecraft.covens.common.registries.ritual.rituals.RitualEnderStream;
import zabi.minecraft.covens.common.registries.ritual.rituals.RitualHighMoon;
import zabi.minecraft.covens.common.registries.ritual.rituals.RitualIdentification;
import zabi.minecraft.covens.common.registries.ritual.rituals.RitualPerception;
import zabi.minecraft.covens.common.registries.ritual.rituals.RitualRedirection;
import zabi.minecraft.covens.common.registries.ritual.rituals.RitualSandsTime;

@Mod.EventBusSubscriber
public class ModRituals {
	
	private static RitualHighMoon high_moon_ritual = null;
	private static RitualSandsTime sand_time_ritual = null;
	private static RitualPerception perception_ritual = null;
	private static Ritual charging_ritual_cardinal_stone = null, charging_riutual_talisman = null, revealing_candle;
	private static RitualEnderGate ender_gate = null;
	private static RitualEnderStream ender_stream = null;
	private static RitualRedirection redirection_ritual = null;
	private static RitualIdentification identification_ritual = null;
	
	
	public static void registerAll() {
		Log.i("Creating rituals");
		
		MinecraftForge.EVENT_BUS.register(new ModRituals());
		
		high_moon_ritual = new RitualHighMoon(
				of(
						new OreIngredient("ingotGold"),
						Ingredient.fromItem(Items.NETHERBRICK)), 
				nops(), 100, circles(GlyphType.NORMAL, null, null), 2000, 0);
		sand_time_ritual = new RitualSandsTime(
				of(
						new OreIngredient("sand"), 
						new OreIngredient("oreDiamond")), 
				nops(), 24000, circles(GlyphType.NORMAL, GlyphType.NORMAL, GlyphType.ENDER), 4000, 4);
		perception_ritual = new RitualPerception(
				of(
						Ingredient.fromItem(Item.getItemFromBlock(Blocks.GLOWSTONE)), 
						Ingredient.fromItem(Items.GOLDEN_CARROT)), 
				nops(), -1, circles(GlyphType.ENDER, GlyphType.ENDER, null), 1500, 5);
		charging_ritual_cardinal_stone = new Ritual(
				of(
						Ingredient.fromStacks(new ItemStack(ModItems.cardinal_stone,1,0)), 
						new OreIngredient("dustRedstone"), 
						new OreIngredient("dustGlowstone")), 
				ofs(new ItemStack(ModItems.cardinal_stone,1,1)), 80, circles(GlyphType.NORMAL, null, null), 2000, 0);
		ender_gate = new RitualEnderGate(
				of(
						Ingredient.fromStacks(new ItemStack(ModItems.cardinal_stone,1,2)), 
						new OreIngredient("enderpearl")), 
				nops(), 100, circles(GlyphType.ENDER, null, null), 1000, 5);
		ender_stream = new RitualEnderStream(
				of(
						new OreIngredient("enderpearl"), 
						Ingredient.fromItem(Items.ENDER_EYE), 
						Ingredient.fromStacks(new ItemStack(ModItems.cardinal_stone,1,2)), 
						new OreIngredient("ingotGold")), 
				nops(), -1, circles(GlyphType.ENDER,GlyphType.ENDER,GlyphType.ENDER), 0, 15);
		charging_riutual_talisman = new Ritual(
				of(
						Ingredient.fromStacks(new ItemStack(ModItems.misc, 1, 8)), 
						new OreIngredient("dustRedstone"), 
						new OreIngredient("dustGlowstone"),
						Ingredient.fromStacks(new ItemStack(ModItems.misc,1,6))), 
				ofs(
						new ItemStack(ModItems.misc,1,9)), 
				80, circles(GlyphType.NORMAL, null, null), 3000, 0);
		redirection_ritual = new RitualRedirection(
				of(
						new OreIngredient("enderpearl"), 
						Ingredient.fromStacks(new ItemStack(ModItems.cardinal_stone,1,2))), 
				nops(), -1, circles(GlyphType.NORMAL, GlyphType.ENDER, null), 2000, 6);
		identification_ritual = new RitualIdentification(
				of(
						Ingredient.fromStacks(new ItemStack(ModItems.soulstring,1,1)), 
						new OreIngredient("nuggetGold"), 
						new OreIngredient("paper")), 
				nops(), 100, circles(GlyphType.NORMAL, null, null), 2000, 0);
		revealing_candle = new Ritual(
				of(
						Ingredient.fromStacks(new ItemStack(ModItems.candle,1,0)), 
						Ingredient.fromStacks(new ItemStack(ModItems.misc,1,9)), 
						Ingredient.fromStacks(new ItemStack(ModItems.misc,1,3))), 
				ofs(
						new ItemStack(ModItems.candle,1,1), 
						new ItemStack(ModItems.misc,1,8)), 
				100, circles(GlyphType.NORMAL, null, null), 2000, 0);
		
		high_moon_ritual.setRegistryName(Reference.MID, "high_moon");
		sand_time_ritual.setRegistryName(Reference.MID, "time_sands");
		perception_ritual.setRegistryName(Reference.MID, "perception");
		charging_ritual_cardinal_stone.setRegistryName(Reference.MID, "charging_1");
		charging_riutual_talisman.setRegistryName(Reference.MID, "charging_talisman");
		ender_gate.setRegistryName(Reference.MID, "ender_gate");
		ender_stream.setRegistryName(Reference.MID, "ender_stream");
		identification_ritual.setRegistryName(Reference.MID, "identification");
		revealing_candle.setRegistryName(Reference.MID, "revealing_candle");
	}
	
	public static NonNullList<Ingredient> of(Ingredient... list) {
		return NonNullList.<Ingredient>from(Ingredient.EMPTY, list);
	}
	
	public static NonNullList<ItemStack> ofs(ItemStack... list) {
		if (list==null||list.length==0) return nops();
		return NonNullList.<ItemStack>from(ItemStack.EMPTY, list);
	}
	
	public static NonNullList<ItemStack> nops() {
		return NonNullList.<ItemStack>create();
	}
	
	public static int circles(GlyphType small, BlockCircleGlyph.GlyphType medium, BlockCircleGlyph.GlyphType big) {
		if (small==null) throw new IllegalArgumentException("Cannot have the smaller circle missing");
		if (medium==null && big!=null) throw new IllegalArgumentException("Cannot have null middle circle when a big circle is present");
		if (small==GlyphType.GOLDEN || medium==GlyphType.GOLDEN || big==GlyphType.GOLDEN) throw new IllegalArgumentException("No golden circles allowed");
		int circleNum = 0;
		if (medium!=null) circleNum++;
		if (big!=null) circleNum++;
		return circleNum|small.ordinal()<<2|(medium==null?0:medium.ordinal()<<4)|(big==null?0:big.ordinal()<<6);
	}
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Ritual> evt) {
		Log.i("Registering rituals");
		IForgeRegistry<Ritual> ritualRegistry = evt.getRegistry();
		ritualRegistry.registerAll(high_moon_ritual, sand_time_ritual, perception_ritual, charging_ritual_cardinal_stone, ender_gate, ender_stream, 
				charging_riutual_talisman, redirection_ritual, identification_ritual, revealing_candle);
	}
}
