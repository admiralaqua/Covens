package zabi.minecraft.covens.common.registries.ritual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import zabi.minecraft.covens.common.lib.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import zabi.minecraft.covens.common.capability.IRitualHandler;

public class Ritual extends IForgeRegistryEntry.Impl<Ritual> {
	
	private int time, circles, altarStartingPower, tickPower;
	private NonNullList<ItemStack> output;
	private NonNullList<Ingredient> input;
	private List<List<ItemStack>> jei_cache;
	public static final IForgeRegistry<Ritual> REGISTRY = new RegistryBuilder<Ritual>().setName(new ResourceLocation(Reference.MID, "rituals")).setType(Ritual.class).setIDRange(0, 200).create();
	
	/**
	 * Constructs a new ritual. To be registered within the registry
	 * 
	 * @param input a NonNullList<ItemStack> with all and every itemstack required to be a valid ritual
	 * @param output a NonNullList<ItemStack> with all and every itemstack that should get dropped on the ground when the ritual stops
	 * @param timeInTicks the time in ticks that the ritual takes to stop. Negative values will have the ritual going on indefinitely. Zero means that the effect/crafting is applied immediately
	 * @param circles is the byte annotation to define what circles are needed. It follows this pattern 332211TT where 33, 22, 11 are the glyph type of the nth circle, and TT the number of required circles, 0 being 1, 2 being 3. 3 (11) will always return a failed circle
	 * 
	 */
	public Ritual(@Nonnull NonNullList<Ingredient> input, @Nonnull NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		this.time = timeInTicks;
		this.input = input;
		this.output = output;
		this.circles = circles;
		this.altarStartingPower = altarStartingPower;
		this.tickPower = powerPerTick;
		if (input.size()==0) throw new IllegalArgumentException("Cannot have an empty input in a ritual");
	}
	
	public boolean isValid(EntityPlayer player, World world, BlockPos pos, List<ItemStack> recipe) {
		return true;
	}
	
	public void onUpdate(@Nullable EntityPlayer player, IRitualHandler tile, World world, BlockPos pos, NBTTagCompound data, int ticks) {}
	public void onFinish(@Nullable EntityPlayer player, IRitualHandler tile, World world, BlockPos pos, NBTTagCompound data) {}
	public void onStopped(@Nullable EntityPlayer player, IRitualHandler tile, World world, BlockPos pos, NBTTagCompound data) {}
	public void onStarted(@Nullable EntityPlayer player, IRitualHandler tile, World world, BlockPos pos, NBTTagCompound data) {}
	public void onLowPower(@Nullable EntityPlayer player, IRitualHandler tile, World world, BlockPos pos, NBTTagCompound data, int ticks) {}
	
	public int getTime() {
		return time;
	}
	
	public NonNullList<ItemStack> getOutput() {
		NonNullList<ItemStack> copy = NonNullList.<ItemStack>create();
		for (ItemStack i:output) copy.add(i);
		return copy;
	}
	
	public boolean isValidInput(List<ItemStack> ground, boolean circles) {
//		Log.d("Computing recipe validity for "+this.getRegistryName());
		ArrayList<ItemStack> checklist = new ArrayList<ItemStack>(ground.size());
		for (ItemStack item:ground) for (int j=0;j<item.getCount();j++) {
			ItemStack copy = item.copy();
			copy.setCount(1);
			checklist.add(copy);
		}
		
		if (checklist.size()!=input.size()) {
//			Log.d("Wrong input size for "+this.getRegistryName()+": found "+checklist.size()+", expected "+input.size());
			return false;
		}
		ArrayList<Ingredient> removalList = new ArrayList<Ingredient>(input);
		
		for (ItemStack stack_on_ground:checklist) {
			Ingredient found = null;
			for (Ingredient ingredient:removalList) {
				if (ingredient.apply(stack_on_ground)) {
					found = ingredient;
					break;
				}
			}
			if (found==null) {
				return false; //The stack on the ground doesn't belong to the rite 
			}
			removalList.remove(found);
		}
		if (!removalList.isEmpty()) {
//			for (Ingredient ing:removalList) Log.d("Missing ingredient: "+ing.getMatchingStacks()[0]);
			return false;
		}
		return circles;
	}
	
	public int getCircles() {
		return circles;
	}
	
	public int getRequiredStartingPower() {
		return altarStartingPower;
	}
	
	public int getRunningPower() {
		return tickPower;
	}

	public List<Ingredient> getInput() {
		ArrayList<Ingredient> stacks = new ArrayList<Ingredient>(input.size());
		stacks.addAll(input);
		return stacks;
	}
	
	public List<List<ItemStack>> getJeiInput() {
		if (jei_cache==null) generateCache();
		return jei_cache;
	}

	private void generateCache() {
		jei_cache = new ArrayList<List<ItemStack>>();
		for (Ingredient i:input) jei_cache.add(Arrays.asList(i.getMatchingStacks()));
	}
	
	public boolean canBeUsedFromCandle() {
		return true;
	}
	
}
