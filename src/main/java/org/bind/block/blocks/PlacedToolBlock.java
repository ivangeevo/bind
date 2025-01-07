package org.bind.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.bind.block.entity.ModBlockEntities;
import org.bind.block.entity.PlacedToolBE;
import org.bind.util.PlaceableAsItem;
import org.jetbrains.annotations.Nullable;

public class PlacedToolBlock extends BlockWithEntity {

    public static final MapCodec<PlacedToolBlock> CODEC = createCodec(PlacedToolBlock::new);

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    private static final double BOUNDING_THICKNESS = 1D / 8D;
    private static final double BOUNDING_HALF_THICKNESS = BOUNDING_THICKNESS / 2D;
    protected static final VoxelShape VANILLA_TOOL_SHAPE =
            Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 16, 8.0);

    public static final DirectionProperty FACING = Properties.FACING; // Changed to horizontal facing

    public PlacedToolBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        int iFacing = facing.getHorizontal(); // Corresponds to the original iFacing
        int iLevel = 0; // This would be determined by your block's state or TileEntity

        double fHeight = 0.75D;
        double fWidth = 0.75D;

        // Example of obtaining the tool's dimensions, adapt as necessary
        if (world.getBlockEntity(pos) instanceof PlacedToolBE tileEntity) {
            ItemStack toolStack = tileEntity.getToolStack();
            if (toolStack != null && toolStack.getItem() instanceof PlaceableAsItem item) {
                fHeight = item.getBlockBoundingBoxHeight();
                fWidth = item.getBlockBoundingBoxWidth();
            }
        }

        double fXMin = 0.5D - (fWidth / 2D);
        double fXMax = 0.5D + (fWidth / 2D);
        double fYMin = 0.5D - (fWidth / 2D);
        double fYMax = 0.5D + (fWidth / 2D);
        double fZMin = 0.5D - (fWidth / 2D);
        double fZMax = 0.5D + (fWidth / 2D);

        if (iFacing < 4) {
            fXMin = 0.5D - BOUNDING_HALF_THICKNESS;
            fXMax = 0.5D + BOUNDING_HALF_THICKNESS;
        } else {
            fZMin = 0.5D - BOUNDING_HALF_THICKNESS;
            fZMax = 0.5D + BOUNDING_HALF_THICKNESS;
        }

        fYMin = 0D;
        fYMax = fHeight;

        return VoxelShapes.cuboid(fXMin, fYMin, fZMin, fXMax, fYMax, fZMax);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // Determine the facing direction based on the player's placement
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlacedToolBE(pos, state);
    }
}
