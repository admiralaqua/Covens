package zabi.minecraft.covens.common.registries.fermenting.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeSwamp;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import zabi.minecraft.covens.common.registries.fermenting.BarrelRecipe;

public class SlimeRecipe extends BarrelRecipe {
	
	public SlimeRecipe(ItemStack output, int ticks, int power) {
		super(output, ticks, power);
	}
	
	@Override
	public boolean isValidRecipe(World world, List<ItemStack> stacks, BlockPos pos, FluidStack fluid) {
		return fluid!=null && fluid.getFluid()!=null && fluid.amount == Fluid.BUCKET_VOLUME && fluid.getFluid().equals(FluidRegistry.WATER) && checkEmpty(stacks) && world.getBiome(pos) instanceof BiomeSwamp;
	}

	private boolean checkEmpty(List<ItemStack> stacks) {
		for (ItemStack is:stacks) if (!is.isEmpty()) return false;
		return true;
	}

}
