package com.elytradev.movingworld.common.experiments;

import com.elytradev.movingworld.common.experiments.newassembly.WorldReader;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by darkevilmac on 2/10/2017.
 */
public class BlockDebug extends Block {
    public BlockDebug(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        setRegistryName("movingworld-experiments", "debug");
        setUnlocalizedName("debug");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn == null || worldIn.isRemote || RegionPool.generatedPoolForDimension(worldIn.provider.getDimension()))
            return false;

        WorldReader worldReader = new WorldReader(pos, worldIn);

        worldReader.readAll();
        worldReader.moveToSubWorld();

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
