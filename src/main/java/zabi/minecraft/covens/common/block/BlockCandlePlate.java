package zabi.minecraft.covens.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zabi.minecraft.covens.client.particle.ParticleSmallFlame;
import zabi.minecraft.covens.common.Covens;
import zabi.minecraft.covens.common.item.ModCreativeTabs;

import java.util.Random;

import zabi.minecraft.covens.common.lib.Reference;

public class BlockCandlePlate extends Block {

	private static final AxisAlignedBB bounding_box = new AxisAlignedBB(0.125, 0, 0.125, 0.875, 0.21875, 0.875);
	
	public BlockCandlePlate() {
		super(Material.IRON);
		this.setUnlocalizedName("candle_plate");
		this.setCreativeTab(ModCreativeTabs.machines);
		this.setRegistryName(Reference.MID, "candle_plate");
		this.setHarvestLevel("pickaxe", 0);
		this.setLightOpacity(0);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return bounding_box;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return bounding_box;
	}
	
	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
	
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextBoolean()) {
			ParticleSmallFlame p = new ParticleSmallFlame(worldIn, pos.getX()+0.35, pos.getY()+0.22, pos.getZ()+0.35, 0, 0, 0, 0.03f);
			Covens.proxy.spawnParticle(p);
		}
		if (rand.nextBoolean()) {
			ParticleSmallFlame p1 = new ParticleSmallFlame(worldIn, pos.getX()+0.65, pos.getY()+0.273, pos.getZ()+0.37, 0, 0, 0, 0.03f);
			Covens.proxy.spawnParticle(p1);
		}
		if (rand.nextBoolean()) {
			ParticleSmallFlame p2 = new ParticleSmallFlame(worldIn, pos.getX()+0.65, pos.getY()+0.15, pos.getZ()+0.65, 0, 0, 0, 0.03f);
			Covens.proxy.spawnParticle(p2);
		}
		if (rand.nextBoolean()) {
			ParticleSmallFlame p3 = new ParticleSmallFlame(worldIn, pos.getX()+0.35, pos.getY()+0.22, pos.getZ()+0.65, 0, 0, 0, 0.03f);
			Covens.proxy.spawnParticle(p3);
		}
	}
}
