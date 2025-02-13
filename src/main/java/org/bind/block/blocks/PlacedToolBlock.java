package org.bind.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.bind.block.entity.ModBlockEntities;
import org.bind.block.entity.PlacedToolBE;
import org.bind.util.PlaceableAsItem;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PlacedToolBlock extends BlockWithEntity {

    public static final MapCodec<PlacedToolBlock> CODEC = createCodec(PlacedToolBlock::new);

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    protected static final HashMap<Direction, VoxelShape> SHAPES = new HashMap<>();
    static {
        SHAPES.put(Direction.NORTH, Block.createCuboidShape(7.5, 0, 0, 8.5, 16, 16));
        SHAPES.put(Direction.EAST, Block.createCuboidShape(0, 0, 7.5, 16, 16, 8.5));
        SHAPES.put(Direction.SOUTH, Block.createCuboidShape(7.5, 0, 0, 8.5, 16, 16));
        SHAPES.put(Direction.WEST, Block.createCuboidShape(0, 0, 7.5, 16, 16, 8.5));
    }



    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING; // Changed to horizontal facing
    public static final EnumProperty<BlockFace> FACE = Properties.BLOCK_FACE;

    public PlacedToolBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(FACE, BlockFace.FLOOR));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        Direction horizontal = facing.getAxis().isHorizontal() ? facing : Direction.NORTH;
        return SHAPES.get(horizontal);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, FACE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = getDefaultState();
        Direction direction = ctx.getSide();
        if (direction.getAxis() == Direction.Axis.Y) {
            blockState = blockState
                    .with(FACE, direction == Direction.UP ? BlockFace.FLOOR : BlockFace.CEILING)
                    .with(FACING, direction == Direction.UP ? ctx.getHorizontalPlayerFacing() : ctx.getHorizontalPlayerFacing().getOpposite());
        } else {
            blockState = blockState.with(FACE, BlockFace.WALL).with(FACING, direction.getOpposite());
        }

        if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
            return blockState;
        }
        return null;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAt(world, pos, getDirection(state));
    }

    public static boolean canPlaceAt(WorldView world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        return world.getBlockState(blockPos).isSideSolid(world, pos, direction.getOpposite(), SideShapeType.CENTER);
    }

    protected static Direction getDirection(BlockState state) {
        return switch (state.get(FACE)) {
            case CEILING -> Direction.UP;
            case FLOOR -> Direction.DOWN;
            default -> state.get(FACING);
        };
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

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        if (world.getBlockEntity(pos) instanceof PlacedToolBE placedToolBlockEntity) {
            return placedToolBlockEntity.getToolStack().copy();
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (world.getBlockEntity(pos) instanceof PlacedToolBE placedToolBlockEntity) {
            ItemStack itemStack = placedToolBlockEntity.getToolStack();
            if (!itemStack.isEmpty()) {
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.getMainHandStack().isEmpty()) {
            ItemStack itemStack = ItemStack.EMPTY;
            if (world.getBlockEntity(pos) instanceof PlacedToolBE placedToolBlockEntity) {
                itemStack = placedToolBlockEntity.getToolStack().copy();
                placedToolBlockEntity.setToolStack(ItemStack.EMPTY);
            }
            player.setStackInHand(player.getActiveHand(), itemStack);
            world.removeBlock(pos, false);
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction != getDirection(state)) {
            return state;
        }
        if (!neighborState.isSideSolid(world, pos, direction, SideShapeType.CENTER)) {
            return Blocks.AIR.getDefaultState();
        }
        return state;
    }
}
