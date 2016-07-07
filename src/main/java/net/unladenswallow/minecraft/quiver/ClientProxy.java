package net.unladenswallow.minecraft.quiver;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
//        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
//    		.register(ModFFQuiver.torchBow, 0, new ModelResourceLocation(new ResourceLocation(ModFFQuiver.MODID, "torch_bow"), "inventory"));
//        ModelBakery.addVariantName(ModFFQuiver.torchBow, ModFFQuiver.MODID + ":torch_bow");
//        ModelBakery.addVariantName(ModFFQuiver.torchBow, ModFFQuiver.MODID + ":torch_bow_pulling_0");
//        ModelBakery.addVariantName(ModFFQuiver.torchBow, ModFFQuiver.MODID + ":torch_bow_pulling_1");
//        ModelBakery.addVariantName(ModFFQuiver.torchBow, ModFFQuiver.MODID + ":torch_bow_pulling_2");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
    		.register(ModFFQuiver.torchArrow, 0, new ModelResourceLocation(new ResourceLocation(ModFFQuiver.MODID, "torch_arrow"), "inventory"));
//        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
//    		.register(ModFFQuiver.explosionBow, 0, new ModelResourceLocation(new ResourceLocation(ModFFQuiver.MODID, "explosion_bow"), "inventory"));
//        ModelBakery.addVariantName(ModFFQuiver.explosionBow, ModFFQuiver.MODID + ":explosion_bow");
//        ModelBakery.addVariantName(ModFFQuiver.explosionBow, ModFFQuiver.MODID + ":explosion_bow_pulling_0");
//        ModelBakery.addVariantName(ModFFQuiver.explosionBow, ModFFQuiver.MODID + ":explosion_bow_pulling_1");
//        ModelBakery.addVariantName(ModFFQuiver.explosionBow, ModFFQuiver.MODID + ":explosion_bow_pulling_2");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
    		.register(ModFFQuiver.explodingArrow, 0, new ModelResourceLocation(new ResourceLocation(ModFFQuiver.MODID, "exploding_arrow"), "inventory"));
//        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
//    		.register(ModFFQuiver.teleportBow, 0, new ModelResourceLocation(new ResourceLocation(ModFFQuiver.MODID, "teleport_bow"), "inventory"));
//        ModelBakery.addVariantName(ModFFQuiver.teleportBow, ModFFQuiver.MODID + ":teleport_bow");
//        ModelBakery.addVariantName(ModFFQuiver.teleportBow, ModFFQuiver.MODID + ":teleport_bow_pulling_0");
//        ModelBakery.addVariantName(ModFFQuiver.teleportBow, ModFFQuiver.MODID + ":teleport_bow_pulling_1");
//        ModelBakery.addVariantName(ModFFQuiver.teleportBow, ModFFQuiver.MODID + ":teleport_bow_pulling_2");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
    		.register(ModFFQuiver.teleportArrow, 0, new ModelResourceLocation(new ResourceLocation(ModFFQuiver.MODID, "teleport_arrow"), "inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
    		.register(ModFFQuiver.ironArrow, 0, new ModelResourceLocation(new ResourceLocation(ModFFQuiver.MODID, "iron_arrow"), "inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
    		.register(ModFFQuiver.poisonArrow, 0, new ModelResourceLocation(new ResourceLocation(ModFFQuiver.MODID, "poison_arrow"), "inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
    		.register(ModFFQuiver.waterArrow, 0, new ModelResourceLocation(new ResourceLocation(ModFFQuiver.MODID, "water_arrow"), "inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
    		.register(ModFFQuiver.lavaArrow, 0, new ModelResourceLocation(new ResourceLocation(ModFFQuiver.MODID, "lava_arrow"), "inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
    		.register(ModFFQuiver.enderShard, 0, new ModelResourceLocation(new ResourceLocation(ModFFQuiver.MODID, "ender_shard"), "inventory"));

        registerItemModelWithVariants(ModFFQuiver.emptyBowAndQuiver, ModFFQuiver.MODID + ":bowandquiver_empty", "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.vanillaArrowBowAndQuiver, ModFFQuiver.MODID + ":bowandquiver_vanillaarrow", "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.torchArrowBowAndQuiver, ModFFQuiver.MODID + ":bowandquiver_torcharrow", "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.explodingArrowBowAndQuiver, ModFFQuiver.MODID + ":bowandquiver_explodingarrow", "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.teleportArrowBowAndQuiver, ModFFQuiver.MODID + ":bowandquiver_teleportarrow", "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.ironArrowBowAndQuiver, ModFFQuiver.MODID + ":bowandquiver_ironarrow", "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.poisonArrowBowAndQuiver, ModFFQuiver.MODID + ":bowandquiver_poisonarrow", "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.waterArrowBowAndQuiver, ModFFQuiver.MODID + ":bowandquiver_waterarrow", "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.lavaArrowBowAndQuiver, ModFFQuiver.MODID + ":bowandquiver_lavaarrow", "minecraft:bow", 3);
        
//        MinecraftForge.EVENT_BUS.register(ModFFQuiver.torchBow);
//		MinecraftForge.EVENT_BUS.register(ModFFQuiver.explosionBow);
//		MinecraftForge.EVENT_BUS.register(ModFFQuiver.teleportBow);
		MinecraftForge.EVENT_BUS.register(ModFFQuiver.emptyBowAndQuiver);
		MinecraftForge.EVENT_BUS.register(ModFFQuiver.vanillaArrowBowAndQuiver);
		MinecraftForge.EVENT_BUS.register(ModFFQuiver.torchArrowBowAndQuiver);
		MinecraftForge.EVENT_BUS.register(ModFFQuiver.explodingArrowBowAndQuiver);
		MinecraftForge.EVENT_BUS.register(ModFFQuiver.teleportArrowBowAndQuiver);
		MinecraftForge.EVENT_BUS.register(ModFFQuiver.ironArrowBowAndQuiver);
		MinecraftForge.EVENT_BUS.register(ModFFQuiver.poisonArrowBowAndQuiver);
		MinecraftForge.EVENT_BUS.register(ModFFQuiver.waterArrowBowAndQuiver);
		MinecraftForge.EVENT_BUS.register(ModFFQuiver.lavaArrowBowAndQuiver);

    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
    
    private void registerItemModelWithVariants(Item bowAndQuiver, String itemModel, String modelBase, int numVariants) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
    		.register(bowAndQuiver, 0, new ModelResourceLocation(itemModel, "inventory"));
// TODO: FIX THIS        
//        ModelBakery.addVariantName(bowAndQuiver, itemModel);
//        ModelBakery.addVariantName(bowAndQuiver, modelBase);
//        for (int i = 0; i < numVariants; i++) {
//	        ModelBakery.addVariantName(bowAndQuiver, modelBase + "_pulling_" + i);
//        }
    }
}
