package net.unladenswallow.minecraft.quiver;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.unladenswallow.minecraft.quiver.item.ItemBowAndQuiver;
import net.unladenswallow.minecraft.quiver.item.ItemExplodingArrow;
import net.unladenswallow.minecraft.quiver.item.ItemIronArrow;
import net.unladenswallow.minecraft.quiver.item.ItemLavaArrow;
import net.unladenswallow.minecraft.quiver.item.ItemQuiverableArrow;
import net.unladenswallow.minecraft.quiver.item.ItemTeleportArrow;
import net.unladenswallow.minecraft.quiver.item.ItemTorchArrow;
import net.unladenswallow.minecraft.quiver.item.ItemWaterArrow;


@Mod(modid = ModFFQuiver.MODID, useMetadata = true)
public class ModFFQuiver {

	public static final String MODID = "mod_quiver";
	
	@SidedProxy(clientSide="net.unladenswallow.minecraft.quiver.ClientProxy", serverSide="net.unladenswallow.minecraft.quiver.ServerProxy")
	public static CommonProxy proxy;
	
	public static Item emptyBowAndQuiver;
	public static ItemQuiverableArrow vanillaArrow;
	public static Item vanillaArrowBowAndQuiver;

	public static ItemQuiverableArrow torchArrow;
	public static Item torchArrowBowAndQuiver;

	public static ItemQuiverableArrow explodingArrow;
	public static Item explodingArrowBowAndQuiver;

	public static Item enderShard;
	public static ItemQuiverableArrow teleportArrow;
	public static Item teleportArrowBowAndQuiver;

	public static ItemQuiverableArrow ironArrow;
	public static Item ironArrowBowAndQuiver;
	
	public static ItemQuiverableArrow waterArrow;
	public static Item waterArrowBowAndQuiver;
	
	public static ItemQuiverableArrow lavaArrow;
	public static Item lavaArrowBowAndQuiver;
	
	public static FFQEventHandler ffqEventHandler;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent preInitEvent) {
		ModFFQuiver.proxy.preInit(preInitEvent);
		
		emptyBowAndQuiver = new ItemBowAndQuiver("bowandquiver_empty", null);

		vanillaArrow = new ItemQuiverableArrow();
		vanillaArrowBowAndQuiver = new ItemBowAndQuiver("bowandquiver_vanillaarrow", new ItemQuiverableArrow());
		
		torchArrow = new ItemTorchArrow();
		torchArrowBowAndQuiver = new ItemBowAndQuiver("bowandquiver_torcharrow", torchArrow);
		
		explodingArrow = new ItemExplodingArrow();
		explodingArrowBowAndQuiver = new ItemBowAndQuiver("bowandquiver_explodingarrow", explodingArrow);
		
		(enderShard = new Item())
		    .setUnlocalizedName("ender_shard")
		    .setCreativeTab(CreativeTabs.tabMisc)
		    .setRegistryName(MODID, "ender_shard");
		GameRegistry.register(enderShard);
		teleportArrow = new ItemTeleportArrow();
		teleportArrowBowAndQuiver = new ItemBowAndQuiver("bowandquiver_teleportarrow", teleportArrow);
		
		ironArrow = new ItemIronArrow();
		ironArrowBowAndQuiver = new ItemBowAndQuiver("bowandquiver_ironarrow", ironArrow);
		
		waterArrow = new ItemWaterArrow();
		waterArrowBowAndQuiver = new ItemBowAndQuiver("bowandquiver_waterarrow", waterArrow);

		lavaArrow = new ItemLavaArrow();
		lavaArrowBowAndQuiver = new ItemBowAndQuiver("bowandquiver_lavaarrow", lavaArrow);

        GameRegistry.register(emptyBowAndQuiver);

		// Don't register vanillaArrow, as it is just a wrapper and doesn't represent a new item
		GameRegistry.register(vanillaArrowBowAndQuiver);

        GameRegistry.register(torchArrow);
		GameRegistry.register(torchArrowBowAndQuiver);
		
		GameRegistry.register(explodingArrow);
		GameRegistry.register(explodingArrowBowAndQuiver);

		GameRegistry.register(teleportArrow);
		GameRegistry.register(teleportArrowBowAndQuiver);

		GameRegistry.register(ironArrow);
		GameRegistry.register(ironArrowBowAndQuiver);

		GameRegistry.register(waterArrow);
		GameRegistry.register(waterArrowBowAndQuiver);
		
		GameRegistry.register(lavaArrow);
		GameRegistry.register(lavaArrowBowAndQuiver);
		
		ffqEventHandler = new FFQEventHandler();
		MinecraftForge.EVENT_BUS.register(ffqEventHandler);
	}
	
	@EventHandler
	public void init (FMLInitializationEvent event) {
		ModFFQuiver.proxy.init(event);
		FFQLogger.info("Initializing " + ModFFQuiver.MODID);
		addRecipes();
		addSmelting();
	}
	
	private void addRecipes() {
		GameRegistry.addRecipe(new ItemStack(Items.saddle),
				"LLL",
				"LIL",
				"I I",
				'L', Items.leather,
				'I', Items.iron_ingot);
		GameRegistry.addRecipe(new ItemStack(ironArrow, 4),
				"L",
				"I",
				"F",
				'L', Items.flint,
				'I', Items.iron_ingot,
				'F', Items.feather);

		GameRegistry.addRecipe(new ItemStack(emptyBowAndQuiver, 1),
				"LTS",
				"TLS",
				"LTS",
				'L', Items.leather,
				'T', Items.stick,
				'S', Items.string);
		GameRegistry.addRecipe(new ItemStack(emptyBowAndQuiver, 1),
				"STL",
				"SLT",
				"STL",
				'L', Items.leather,
				'T', Items.stick,
				'S', Items.string);

		GameRegistry.addShapelessRecipe(new ItemStack(torchArrow),
				new Object[] {Blocks.torch, Items.arrow});
		GameRegistry.addShapelessRecipe(new ItemStack(explodingArrow), 
				new Object[] {Items.arrow, Items.gunpowder});
		GameRegistry.addShapelessRecipe(new ItemStack(teleportArrow), 
				new Object[] {Items.arrow, ModFFQuiver.enderShard});
		GameRegistry.addShapelessRecipe(new ItemStack(enderShard, 4), 
				new Object[] {Items.ender_pearl});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.ender_pearl), 
				new Object[] {enderShard, enderShard, enderShard, enderShard});
		GameRegistry.addShapelessRecipe(new ItemStack(waterArrow, 3), 
				new Object[] {Items.arrow, Items.arrow, Items.arrow, Items.water_bucket});
		// Only allow 1 lava arrow from lava bucket.  Otherwise it would turn lava into a renewable resource
		GameRegistry.addShapelessRecipe(new ItemStack(lavaArrow), new Object[] {Items.arrow, Items.lava_bucket});

		((ItemBowAndQuiver)vanillaArrowBowAndQuiver).addRepairRecipes();
		((ItemBowAndQuiver)torchArrowBowAndQuiver).addRepairRecipes();
		((ItemBowAndQuiver)explodingArrowBowAndQuiver).addRepairRecipes();
		((ItemBowAndQuiver)teleportArrowBowAndQuiver).addRepairRecipes();
		((ItemBowAndQuiver)ironArrowBowAndQuiver).addRepairRecipes();
		((ItemBowAndQuiver)waterArrowBowAndQuiver).addRepairRecipes();
		((ItemBowAndQuiver)lavaArrowBowAndQuiver).addRepairRecipes();

	}
	
	private void addSmelting() {
	}

}
