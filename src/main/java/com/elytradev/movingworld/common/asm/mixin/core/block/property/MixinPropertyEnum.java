package com.elytradev.movingworld.common.asm.mixin.core.block.property;

import com.elytradev.movingworld.api.rotation.IRotationProperty;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLog;

import net.minecraft.block.state.IBlockState;
import net.minecraft.state.IProperty;
import net.minecraft.util.EnumFacing;

public class MixinPropertyEnum implements IRotationProperty {

    @Override
    public IBlockState rotate(IBlockState blockState, boolean ccw) {
        IProperty propertyEnum = (IProperty) this;
        Object propertyValue = blockState.get(propertyEnum);

        if (propertyValue instanceof EnumFacing) {
            EnumFacing facing = (EnumFacing) propertyValue;

            if (facing.getHorizontalIndex() != -1) {
                if (!ccw)
                    blockState = blockState.with(propertyEnum, facing.rotateY());
                else
                    blockState = blockState.with(propertyEnum, facing.rotateYCCW());
            }
        } else if (propertyValue instanceof EnumFacing.Axis) {
            EnumFacing.Axis axis = (EnumFacing.Axis) propertyValue;

            axis = axis == EnumFacing.Axis.X ? EnumFacing.Axis.Z : axis == EnumFacing.Axis.Z ? EnumFacing.Axis.X : axis;

            blockState = blockState.with(propertyEnum, axis);
        } else if (propertyValue instanceof BlockLog.EnumAxis) {
            BlockLog.EnumAxis axis = (BlockLog.EnumAxis) blockState.getValue(propertyEnum);

            if (axis == BlockLog.EnumAxis.X)
                axis = BlockLog.EnumAxis.Z;
            else if (axis == BlockLog.EnumAxis.Z)
                axis = BlockLog.EnumAxis.X;

            blockState = blockState.withProperty(propertyEnum, axis);
        } else if (propertyValue instanceof BlockLever.EnumOrientation) {
            BlockLever.EnumOrientation orientation = (BlockLever.EnumOrientation) blockState.getValue(propertyEnum);
            EnumFacing facing = orientation.getFacing();

            if (facing.getHorizontalIndex() != -1) {
                // Not on the vertical axis.

                if (!ccw)
                    facing = facing.rotateY();
                else
                    facing = facing.rotateYCCW();

                for (BlockLever.EnumOrientation enumOrientation : BlockLever.EnumOrientation.values()) {
                    if (enumOrientation.getFacing() == facing) {
                        orientation = enumOrientation;
                        break;
                    }
                }
            } else {
                // On the vertical axis.

                if (orientation == BlockLever.EnumOrientation.DOWN_X)
                    orientation = BlockLever.EnumOrientation.DOWN_Z;
                else if (orientation == BlockLever.EnumOrientation.DOWN_Z)
                    orientation = BlockLever.EnumOrientation.DOWN_X;
                else if (orientation == BlockLever.EnumOrientation.UP_X)
                    orientation = BlockLever.EnumOrientation.UP_Z;
                else if (orientation == BlockLever.EnumOrientation.UP_Z)
                    orientation = BlockLever.EnumOrientation.UP_X;
            }

            blockState = blockState.withProperty(propertyEnum, orientation);
        }

        return blockState;
    }

}
