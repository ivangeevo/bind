package org.bind.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class PlacedToolBE extends BlockEntity {

    private ItemStack toolStack;

    public PlacedToolBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLACED_TOOL, pos, state);
        this.toolStack = ItemStack.EMPTY;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains("ToolStack", 10)) {
            toolStack = ItemStack.CODEC.parse(registryLookup.getOps(NbtOps.INSTANCE), nbt.getCompound("ToolStack"))
                    .result()
                    .orElse(ItemStack.EMPTY);
        }
    }


    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.put("ToolStack", toolStack.encode(registryLookup, new NbtCompound()));
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound nbtCompound = createNbt(registryLookup);
        nbtCompound.put("ToolStack", toolStack.encode(registryLookup, new NbtCompound()));
        return nbtCompound;
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public ItemStack getToolStack() {
        return toolStack;
    }

    public void setToolStack(ItemStack stack) {
        toolStack = stack;
        markDirty();
    }
}
