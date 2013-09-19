package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class OverworldTreeBlock extends BlockLog
{
    public Icon[] icons;
    public String[] textureNames = new String[] { "maple_bark", "maple_heart", "silverbell_bark", "silverbell_heart", "purpleheart_bark", "purpleheart_heart", "tiger_bark", "tiger_heart" };

    public OverworldTreeBlock(int id)
    {
        super(id);
        this.setHardness(2.0F);
        this.setStepSound(Block.soundWoodFootstep);
        setBurnProperties(this.blockID, 5, 20);
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int metadata)
    {
        int tex = (metadata % 4) * 2;
        int orientation = metadata / 4;

        switch (orientation)
        //Ends of logs
        {
        case 0:
            if (side == 0 || side == 1)
                return icons[tex + 1];
            break;
        case 1:
            if (side == 4 || side == 5)
                return icons[tex + 1];
            break;
        case 2:
            if (side == 2 || side == 3)
                return icons[tex + 1];
            break;
        }

        return icons[tex];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i]);
        }
    }

    public int idDropped (int par1, Random par2Random, int par3)
    {
        return this.blockID;
    }

    /*@Override
    public int damageDropped(int meta)
    {
        return meta % 4;
    }

    public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return metadata % 4 != 2 ? blockFlammability[blockID] : 0;
    }

    public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return metadata % 4 != 2 ? blockFireSpreadSpeed[blockID] : 0;
    }*/

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < icons.length / 2; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }
}
