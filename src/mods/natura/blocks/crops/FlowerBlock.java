package mods.natura.blocks.crops;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FlowerBlock extends BlockFlower
{

    public FlowerBlock(int par1)
    {
        super(par1);
        setCreativeTab(CreativeTabs.tabDecorations);
        this.setStepSound(Block.soundGrassFootstep);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon("natura:flower_bluebells");
    }
}
