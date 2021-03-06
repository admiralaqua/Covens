package zabi.minecraft.covens.common.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zabi.minecraft.covens.common.block.BlockCircleGlyph;
import zabi.minecraft.covens.common.block.ModBlocks;
import zabi.minecraft.covens.common.lib.Reference;

public class ItemChalk extends Item {
	
	private static final int MAX_USES = 40;

	public ItemChalk() {
		this.setUnlocalizedName("chalk");
		this.setRegistryName(Reference.MID, "chalk");
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setHasSubtypes(true);
		this.setCreativeTab(ModCreativeTabs.products);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack)+"_"+BlockCircleGlyph.GlyphType.values()[stack.getMetadata()].name().toLowerCase();
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}
	
	@Override
	public boolean canItemEditBlocks() {
		return true;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("usesLeft", MAX_USES);
		}
		int usesLeft = stack.getTagCompound().getInteger("usesLeft");
		return 1d-((double)usesLeft/(double)MAX_USES);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack)>0;
	}
	
	@Override
	public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
		if (this.isInCreativeTab(itemIn)) {
			for (int i=0;i<4;i++) {
				tab.add(new ItemStack(this,1,i));
			}
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean isReplacing = worldIn.getBlockState(pos).getBlock().equals(ModBlocks.glyphs);
		if (!worldIn.isRemote && (facing == EnumFacing.UP && ModBlocks.glyphs.canPlaceBlockAt(worldIn, pos.up()) || isReplacing)) {
			ItemStack chalk = player.getHeldItem(hand);
			if (!chalk.hasTagCompound()) {
				chalk.setTagCompound(new NBTTagCompound());
				chalk.getTagCompound().setInteger("usesLeft", MAX_USES);
			}
			int type = chalk.getItemDamage();
			if (!player.isCreative()) {
				int usesLeft = chalk.getTagCompound().getInteger("usesLeft") - 1;
				chalk.getTagCompound().setInteger("usesLeft", usesLeft);
				if (usesLeft<1) chalk.setCount(0);
			}
			IBlockState state = ModBlocks.glyphs.getExtendedState(ModBlocks.glyphs.getDefaultState(), worldIn, pos);
			state = state.withProperty(BlockCircleGlyph.FACING, EnumFacing.HORIZONTALS[(int)(Math.random()*4)]);
			state = state.withProperty(BlockCircleGlyph.TYPE, BlockCircleGlyph.GlyphType.values()[type]);
			worldIn.setBlockState(isReplacing?pos:pos.up(), state, 2);
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
