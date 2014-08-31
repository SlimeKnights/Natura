package mods.natura.plugins.fmp;

import net.minecraft.block.Block;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.MicroMaterialRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import mods.natura.Natura;
import mods.natura.common.NContent;

@Pulse(id = "Natura FMP Compatibility", modsRequired = ForgeMultiPart.modId)
public class ForgeMultiPart
{

    public static final String modId = "ForgeMultipart";

    @Handler
    public void init (FMLInitializationEvent evt)
    {
        try
        {
            Natura.logger.debug("[FMP] Registering Natura decorative blocks with FMP.");
            registerBlock(NContent.bloodwood);
            registerBlock(NContent.willow);
            registerBlock(NContent.planks, 0, 12);
            registerBlock(NContent.tree, 0, 3);
            registerBlock(NContent.rareTree, 0, 3);
            registerBlock(NContent.rareLeaves, 0, 3);
            registerBlock(NContent.darkLeaves, 0, 3);
            registerBlock(NContent.redwood, 0, 2);
            registerBlock(NContent.floraLeaves, 0, 2);
            registerBlock(NContent.floraLeavesNoColor, 0, 2);
        }
        catch (Exception e)
        {
            Natura.logger.catching(e);
        }
    }

    //For blocks with metadata values only
    public static void registerBlock (Block block, int metastart, int metaend)
    {
        for (int meta = metastart; meta <= metaend; meta++)
        {
            String identifier = new String(block.getUnlocalizedName());
            MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(block, meta), identifier + meta);
        }
    }

    //For blocks without metadata values only.
    public static void registerBlock (Block block)
    {
        BlockMicroMaterial.createAndRegister(block, 0);
    }

    //For blocks with metadata values and special MicroMaterial only
    public static void registerBlock (Block block, int metastart, int metaend, MicroMaterialRegistry.IMicroMaterial material)
    {
        for (int meta = metastart; meta <= metaend; meta++)
        {
            String identifier = new String(block.getUnlocalizedName());
            MicroMaterialRegistry.registerMaterial(material, identifier + meta);
        }
    }

    //For blocks without metadata values and special MicroMaterial only.
    public static void registerBlock (Block block, MicroMaterialRegistry.IMicroMaterial material)
    {
        MicroMaterialRegistry.registerMaterial(material, new String(block.getUnlocalizedName()));
    }

}
