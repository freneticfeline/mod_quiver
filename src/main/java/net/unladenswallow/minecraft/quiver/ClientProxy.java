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
    	registerItemModel(ModFFQuiver.torchArrow);
    	registerItemModel(ModFFQuiver.explodingArrow);
    	registerItemModel(ModFFQuiver.teleportArrow);
    	registerItemModel(ModFFQuiver.ironArrow);
    	registerItemModel(ModFFQuiver.poisonArrow);
    	registerItemModel(ModFFQuiver.waterArrow);
    	registerItemModel(ModFFQuiver.lavaArrow);
    	registerItemModel(ModFFQuiver.enderShard);

        registerItemModelWithVariants(ModFFQuiver.emptyBowAndQuiver, "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.vanillaArrowBowAndQuiver, "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.torchArrowBowAndQuiver, "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.explodingArrowBowAndQuiver, "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.teleportArrowBowAndQuiver, "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.ironArrowBowAndQuiver, "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.poisonArrowBowAndQuiver, "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.waterArrowBowAndQuiver, "minecraft:bow", 3);
        registerItemModelWithVariants(ModFFQuiver.lavaArrowBowAndQuiver, "minecraft:bow", 3);
        
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
    
    private void registerItemModelWithVariants(Item item, String modelBase, int numVariants) {
        registerItemModel(item);
    }
    
    private void registerItemModel(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
        .register(item, 0, new ModelResourceLocation(new ResourceLocation(ModFFQuiver.MODID, stripItemPrefix(item.getUnlocalizedName())), "inventory"));
    }
    
    public static String stripItemPrefix(String inString) {
        return inString.replaceAll("item.", "");
    }
}
