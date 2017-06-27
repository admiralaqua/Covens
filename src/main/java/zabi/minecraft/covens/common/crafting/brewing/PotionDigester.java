package zabi.minecraft.covens.common.crafting.brewing;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import zabi.minecraft.covens.common.item.ModItems;

public class PotionDigester {
	public static BrewData digestPotion(NonNullList<ItemStack> stacks) {
		ItemStack[] items = new ItemStack[stacks.size()];//Performance wise I prefer to have quick read time, since this list can get really long
		stacks.toArray(items);
		BrewData result = new BrewData();
		int effectSize = getEffectSize(items);
		int read = 0;
		
		int currentBaseDuration = 0;
		BrewIngredient currentPotion = null;
		float currentLengthModifier = 1;
		int currentPower = 0;
		int persistency = 0;
		boolean isCurable = true;
		boolean showParticles = true;
		boolean getOpposite = false;
		
		while (read<items.length) {
			if (items[read]!=null) {
				if (CovensBrewIngredientRegistry.isNewPotionInitializer(items[read])) {
					if (currentPotion!=null) {
						CovenPotionEffect pe = new CovenPotionEffect(getOpposite?currentPotion.getOpposite():currentPotion.getResult(), currentBaseDuration, currentPower);
						pe.setCurable(isCurable);
						pe.setMultiplier(currentLengthModifier);
						pe.setShowParticle(showParticles);
						pe.setPersistency(persistency);
						result.addEffectToBrew(pe);
						if (result.getEffects().size()>=effectSize) { //RuinedPotion
							return new BrewData();
						}
					}
					currentPotion = CovensBrewIngredientRegistry.getPotion(items[read]);
					currentBaseDuration = CovensBrewIngredientRegistry.getDuration(items[read]);
					currentLengthModifier = 1f;
					currentPower = 0;
					persistency = 0;
					getOpposite = false;
					isCurable = true;
					showParticles = true;
				} else if (items[read].getItem().equals(Items.REDSTONE) && currentPotion!=null) {
					currentLengthModifier+=0.4;
					if (currentLengthModifier>3) currentLengthModifier=3;
				} else if (items[read].getItem().equals(Items.GLOWSTONE_DUST) && currentPotion!=null) {
					currentPower++;
					if (currentPower>5) currentPower=5;
				} else if (items[read].getItem().equals(Items.GUNPOWDER) && currentPotion!=null) {
					persistency++;
					if (persistency>5) persistency=5;
				} else if (items[read].getItem().equals(Items.DIAMOND) && currentPotion!=null) {
					showParticles = false;
				} else if (items[read].getItem().equals(ModItems.flowers) && items[read].getMetadata()==3 && currentPotion!=null) { //Chrysanthemum
					isCurable = false;
				} else if (items[read].getItem().equals(Items.FERMENTED_SPIDER_EYE) && currentPotion!=null && !getOpposite) {
					if (currentPotion.getOpposite()==null) {
						return new BrewData(); //No opposite exists
					}
					getOpposite = true;
				} else {
					return new BrewData(); //Unrecognized Item, ruined potion
				}
			}
			read++;
		}
		if (currentPotion!=null) {
			CovenPotionEffect pe = new CovenPotionEffect(getOpposite?currentPotion.getOpposite():currentPotion.getResult(), currentBaseDuration, currentPower);
			pe.setCurable(isCurable);
			pe.setMultiplier(currentLengthModifier);
			pe.setShowParticle(showParticles);
			pe.setPersistency(persistency);
			result.addEffectToBrew(pe);
		}
		return result;
	}

	private static int getEffectSize(ItemStack[] items) { //wart = +1, mysterious seeds  = +2, nether star = +4, some op endgame item = +8
		boolean[] catalysts = new boolean[4];
		int effectSize = 0;
		for (int i = 0; i < Math.min(items.length, 4);i++) {
			if (items[i].getItem().equals(Items.NETHER_WART) && !catalysts[0]) {
				catalysts[0]=true;
				items[i]=null;
				effectSize+=1;
			} else if (items[i].getItem().equals(ModItems.eerie_seeds) && !catalysts[1]) {
				catalysts[1]=true;
				items[i]=null;
				effectSize+=2;
			} else if (items[i].getItem().equals(Items.NETHER_STAR) && !catalysts[2]) {
				catalysts[2]=true;
				items[i]=null;
				effectSize+=4;
			} else if (items[i].getItem().equals(Items.NETHER_STAR) && !catalysts[3]) { //For now, 2 nstars
				catalysts[3]=true;
				items[i]=null;
				effectSize+=8;
			} else {
				return effectSize; //first invalid length modifier: start recipe digestion
			}
		}
		return effectSize;
	}
}