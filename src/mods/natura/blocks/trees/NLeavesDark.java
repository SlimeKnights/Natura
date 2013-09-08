package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NLeavesDark extends NLeaves
{
    public NLeavesDark(int id)
    {
        super(id);
        this.setCreativeTab(NaturaTab.tab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IconRegister iconRegister)
    {
        String[] textureNames = new String[] { "darkwood", "darkwood_flowering", "darkwood_fruit", "fusewood" };
        this.fastIcons = new Icon[textureNames.length];
        this.fancyIcons = new Icon[textureNames.length];

        for (int i = 0; i < this.fastIcons.length; i++)
        {
            this.fastIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_leaves_fast");
            this.fancyIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_leaves_fancy");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int metadata)
    {
        int meta = metadata % 4;

        if (graphicsLevel)
            return fancyIcons[meta];
        else
            return fastIcons[meta];
    }

    @SideOnly(Side.CLIENT)
    public int getBlockColor ()
    {
        return 16777215;
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor (int par1)
    {
        return 16777215;
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier (IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return 16777215;
    }

    public int getFlammability (IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return 0;
    }

    public int getFireSpreadSpeed (World world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return 0;
    }

    @Override
    public int idDropped (int metadata, Random random, int fortune)
    {
        if (metadata % 4 == 2)
            return NContent.potashApple.itemID;
        return NContent.floraSapling.blockID;
    }

    public int damageDropped (int par1)
    {
        if (par1 % 4 == 2)
            return 0;
        if (par1 % 4 == 3)
            return 7;
        return 6;
    }

    public int quantityDropped (int meta, int fortune, Random random)
    {
        if (meta % 4 == 2)
            return 1;
        return quantityDroppedWithBonus(fortune, random);
    }

    public void getSubBlocks (int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }
}
