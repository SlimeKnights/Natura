package mods.natura.items;

import java.util.List;

import mods.natura.Natura;
import mods.natura.common.NaturaTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherFoodItem extends ItemFood
{
    public Icon[] icons;
    public String[] textureNames = new String[] { "potashapple" };//, "haste"

    public NetherFoodItem(int id)
    {
        super(id, 4, 0.4F, false);
        setHasSubtypes(true);
        setMaxDamage(0);
        this.setCreativeTab(NaturaTab.tab);
        //this.setAlwaysEdible();
    }

    /*public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
        if (player.canEat(true) && player.foodStats.getSaturationLevel() < 20F)
        {
            player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }

        return par1ItemStack;
    }*/

    protected void onFoodEaten (ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            int duration = 0;
            PotionEffect potion;
            switch (stack.getItemDamage())
            {
            case 0:
                if (Natura.random.nextFloat() < 0.75f)
                {
                    potion = player.getActivePotionEffect(Potion.poison);
                    if (potion != null)
                        duration = potion.duration;
                    else
                        duration = 0;
                    player.addPotionEffect(new PotionEffect(Potion.poison.id, duration + 2 * 25, 0));
                }
                break;
            }
        }
    }

    /*@Override
    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 24;
    }*/

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage (int meta)
    {
        return icons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:fruit_" + textureNames[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("Tastes a bit like chalk");
        /*switch (stack.getItemDamage())
        {
        case 0: 
        	list.add("Killer healing");
        	break;
        case 1:
        	list.add("Visible night");
        	break;
        case 2:
        	list.add("Slow dive");
        	break;
        case 3:
        	list.add("Hit like a truck");
        	break;
        }*/
    }

    /* Name override */
    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return (new StringBuilder()).append("item.food.nether.").append(textureNames[itemstack.getItemDamage()]).toString();
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems (int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 1; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

    /*public boolean isPotionIngredient()
    {
        return true;
    }*/
}
