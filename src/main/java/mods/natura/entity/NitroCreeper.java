package mods.natura.entity;

import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class NitroCreeper extends EntityCreeper
{
    protected int fuseTime = 12;
    protected int timeSinceIgnited;
    protected int lastActiveTime;

    public float explosionRadius = 1f;

    public NitroCreeper(World world)
    {
        super(world);
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0F, false));
        this.isImmuneToFire = true;
    }

    /*@Override
    public void initCreature ()
    {
        //if (this.rand.nextInt(100) == 0)
            this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
    }*/

    /*public int getMaxHealth ()
    {
        return 20;
    }*/

    @Override
    protected void fall (float distance)
    {
        if (!this.worldObj.isRemote)
        {
            if (distance > 5)
            {
                boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

                if (this.getPowered())
                {
                    this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 20f, flag);
                }
                else
                {
                    this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3f, false);
                }

                this.setDead();
            }
            else
                super.fall(distance);
        }
    }

    @Override
    public void writeEntityToNBT (NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Fuse", (short) this.fuseTime);
    }

    @Override
    public void readEntityFromNBT (NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("Fuse"))
        {
            this.fuseTime = par1NBTTagCompound.getShort("Fuse");
        }
    }

    @Override
    public void onUpdate ()
    {
        if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;
            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound("random.fuse", 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            int difficulty = worldObj.difficultySetting.getDifficultyId();
            int lengthBoost = 4 * (3 - difficulty);
            int powered = this.getPowered() ? 12 : 0;

            if (this.timeSinceIgnited >= this.fuseTime + difficulty + powered)
            {
                this.timeSinceIgnited = this.fuseTime;

                if (!this.worldObj.isRemote)
                {
                    boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

                    if (powered > 0)
                    {
                        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 20f, flag);
                    }
                    else
                    {
                        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3f, flag);
                    }

                    this.setDead();
                }
            }
        }

        super.onUpdate();
    }

    @Override
    public float getCreeperFlashIntensity (float par1)
    {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * par1) / (this.fuseTime - 2);
    }

    @Override
    protected void dropFewItems (boolean par1, int par2)
    {
        Item j = this.getDropItem();

        if (j != null)
        {
            int k = this.rand.nextInt(4) + 2;

            if (par2 > 0)
            {
                k += this.rand.nextInt(par2 + 1);
            }

            for (int l = 0; l < k; ++l)
            {
                this.dropItem(j, 1);
            }
        }

        if (this.getPowered())
        {
            if (j != null)
            {
                int k = this.rand.nextInt(40) + 20;

                if (par2 > 0)
                {
                    k += this.rand.nextInt(par2 * 6 + 1);
                }

                for (int l = 0; l < k; ++l)
                {
                    this.dropItem(j, 1);
                }
            }

            /*j = Block.tnt.blockID;
            int k = this.rand.nextInt(5) + 2;

            if (par2 > 0)
            {
                k += this.rand.nextInt(par2*3 + 1);
            }

            for (int l = 0; l < k; ++l)
            {
                this.dropItem(j, 1);
            }*/
        }
    }

    public boolean attackEntityFrom (DamageSource source, int damage)
    {
        if (source instanceof EntityDamageSource && ((EntityDamageSource) source).getEntity() instanceof EntityIronGolem)
        {
            damage = 1000;
        }
        return super.attackEntityFrom(source, damage);
    }

    /*public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox) && this.getBlockPathWeight(i, j, k) >= 0.0F;
    }*/
}
