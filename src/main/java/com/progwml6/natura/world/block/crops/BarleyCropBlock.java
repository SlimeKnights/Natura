package com.progwml6.natura.world.block.crops;

import com.progwml6.natura.world.NaturaWorld;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BarleyCropBlock extends OverworldCropsBlock {

  public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);

  public BarleyCropBlock(Properties builder) {
    super(builder);
  }

  @Override
  protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
    return state.matchesBlock(Blocks.FARMLAND);
  }

  @Override
  protected IntegerProperty getAgeProperty() {
    return AGE;
  }

  @Override
  public int getMaxAge() {
    return 3;
  }

  @Override
  public int getBoneMealMinAge() {
    return 1;
  }

  @Override
  public int getBoneMealMaxAge() {
    return 2;
  }

  @Override
  protected IItemProvider getSeedsItem() {
    return NaturaWorld.barley_crop.asItem();
  }
}
