package mods.natura.items.blocks;

import java.util.List;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NSaplingItem extends ItemBlock
{
    public static final String blockType[] = { "redwood", "eucalyptus", "bush", "sakura", "ghost", "blood", "darkwood", "fusewood", "", "", "", "", "", "", "", "", "" };
    private Block bID;

    public NSaplingItem(Block block)
    {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        this.bID = block;
    }

    @Override
    public int getMetadata (int md)
    {
        return md;
    }

    @Override
    public IIcon getIconFromDamage (int i)
    {
        return NContent.floraSapling.getIcon(0, i);
    }

    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return (new StringBuilder()).append("block.sapling.").append(blockType[itemstack.getItemDamage()]).toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.sapling1"));
            list.add(StatCollector.translateToLocal("tooltip.sapling2"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.tree1"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.tree6"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.tree2"));
            break;
        case 4:
            list.add(StatCollector.translateToLocal("tooltip.tree3"));
            break;
        case 5:
            list.add(StatCollector.translateToLocal("tooltip.sapling3"));
            list.add(StatCollector.translateToLocal("tooltip.sapling4"));
            break;
        case 6:
            list.add(StatCollector.translateToLocal("tooltip.sapling5"));
            break;
        case 7:
            list.add(StatCollector.translateToLocal("tooltip.fusewood.log"));
            break;
        }
    }

    @Override
    public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        Block blockID = world.getBlock(x, y, z);

        if (blockID == Blocks.snow && (world.getBlockMetadata(x, y, z) & 7) < 1)
        {
            side = 1;
        }
        else if (blockID != Blocks.vine && blockID != Blocks.tallgrass && blockID != Blocks.deadbush && (blockID == null || !blockID.isReplaceable(world, x, y, z)))
        {
            if (side == 0)
            {
                --y;
            }

            if (side == 1)
            {
                ++y;
            }

            if (side == 2)
            {
                --z;
            }

            if (side == 3)
            {
                ++z;
            }

            if (side == 4)
            {
                --x;
            }

            if (side == 5)
            {
                ++x;
            }
        }

        if (stack.getItemDamage() == 5)
        {
            Block block = world.getBlock(x, y + 1, z);
            if (block == null || world.isAirBlock(x, y + 1, z))
                return false;
        }
        else
        {
            Block block = world.getBlock(x, y - 1, z);
            if (block == null || world.isAirBlock(x, y - 1, z))
                return false;
        }

        if (stack.stackSize == 0)
        {
            return false;
        }
        else if (!player.canPlayerEdit(x, y, z, side, stack))
        {
            return false;
        }
        else if (y == 255 && this.bID.getMaterial().isSolid())
        {
            return false;
        }
        else if (world.canPlaceEntityOnSide(this.bID, x, y, z, false, side, player, stack))
        {
            Block block = this.bID;
            int j1 = this.getMetadata(stack.getItemDamage());
            int k1 = this.bID.onBlockPlaced(world, x, y, z, side, par8, par9, par10, j1);

            if (placeBlockAt(stack, player, world, x, y, z, side, par8, par9, par10, k1))
            {
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block.stepSound.getBreakSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                --stack.stackSize;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
