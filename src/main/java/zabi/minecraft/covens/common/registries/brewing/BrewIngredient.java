package zabi.minecraft.covens.common.registries.brewing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import zabi.minecraft.covens.common.lib.Reference;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

public class BrewIngredient extends IForgeRegistryEntry.Impl<BrewIngredient> {
	
	public static final IForgeRegistry<BrewIngredient> REGISTRY = new RegistryBuilder<BrewIngredient>().setName(new ResourceLocation(Reference.MID, "z_brew_ingredients")).setType(BrewIngredient.class).setIDRange(0, 200).create();

	private Ingredient ingredient;
	private Potion result = null, opposite = null;
	private int baseDuration = 0, oppositeDuration=0;
	
	public BrewIngredient(@Nonnull Ingredient in, @Nonnull Potion out, @Nullable Potion opposite, int duration, int durationOpposite) {
		ingredient = in;
		result = out;
		baseDuration = duration;
		this.opposite=opposite;
		oppositeDuration = durationOpposite;
		this.setRegistryName(out.getRegistryName());
	}
	
	public BrewIngredient(Ingredient in, Potion out, int duration) {
		this(in, out, null, duration, 0);
	}
	
	public void setOpposite(Potion potion, int duration) {
		this.opposite=potion;
		this.oppositeDuration=duration;
	}
	
	public boolean isValid(ItemStack test) {
		return ingredient.apply(test);
	}
	
	public Potion getResult() {
		return result;
	}
	
	public int getDuration() {
		return baseDuration;
	}
	
	public int getDurationOpposite() {
		return oppositeDuration;
	}
	
	@Nullable
	public Potion getOpposite() {
		return opposite;
	}
	
	public ItemStack[] getInput() {
		return ingredient.getMatchingStacks();
	}
}
