package zabi.minecraft.covens.common.potion;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import zabi.minecraft.covens.common.lib.Log;
import zabi.minecraft.covens.common.potion.potions.PotionBlockProjectiles;
import zabi.minecraft.covens.common.potion.potions.PotionDisrobing;
import zabi.minecraft.covens.common.potion.potions.PotionExtinguishFire;
import zabi.minecraft.covens.common.potion.potions.PotionPlanting;
import zabi.minecraft.covens.common.potion.potions.PotionSkinRotting;
import zabi.minecraft.covens.common.potion.potions.PotionTinting;

@Mod.EventBusSubscriber
public class ModPotions {
	
	public static ModPotion disrobing, tinting, skin_rotting, extinguish_fire, planting, arrow_block;
	
	public static void registerAll() {
		Log.i("Creating potions");
		disrobing = new PotionDisrobing(0xb40eff, "disrobing");
		tinting = new PotionTinting(0xffff99, "tinting");
		skin_rotting = new PotionSkinRotting(0x766d1b, "skin_rotting");
		extinguish_fire = new PotionExtinguishFire(0x008080, "extinguish_fire");
		planting = new PotionPlanting(0x45c91c, "planting");
		arrow_block = new PotionBlockProjectiles(0x6f12bf, "block_arrows");
	}
	
	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> evt) {
		Log.i("Registering potions");
		IForgeRegistry<Potion> reg = evt.getRegistry();
		reg.registerAll(disrobing, tinting, skin_rotting, extinguish_fire, planting, arrow_block);
	}
}
