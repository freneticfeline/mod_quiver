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
import net.unladenswallow.minecraft.quiver.item.ItemPoisonArrow;
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
//	public static Item torchBow;
	public static Item torchArrowBowAndQuiver;

	public static ItemQuiverableArrow explodingArrow;
//	public static Item explosionBow;
	public static Item explodingArrowBowAndQuiver;

	public static Item enderShard;
	public static ItemQuiverableArrow teleportArrow;
//	public static Item teleportBow;
	public static Item teleportArrowBowAndQuiver;

	public static ItemQuiverableArrow ironArrow;
	public static Item ironArrowBowAndQuiver;
	
	public static ItemQuiverableArrow poisonArrow;
	public static Item poisonArrowBowAndQuiver;
	
	public static ItemQuiverableArrow waterArrow;
	public static Item waterArrowBowAndQuiver;
	
	public static ItemQuiverableArrow lavaArrow;
	public static Item lavaArrowBowAndQuiver;
	
	public static FFQEventHandler ffqEventHandler;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent preInitEvent) {
		ModFFQuiver.proxy.preInit(preInitEvent);
		
		emptyBowAndQuiver = new ItemBowAndQuiver("emptyBowAndQuiver", null);

		vanillaArrow = new ItemQuiverableArrow();
		vanillaArrowBowAndQuiver = new ItemBowAndQuiver("vanillaArrowBowAndQuiver", new ItemQuiverableArrow());
		
		torchArrow = new ItemTorchArrow();
//		torchBow = new ItemTorchBow();
		torchArrowBowAndQuiver = new ItemBowAndQuiver("torchArrowBowAndQuiver", torchArrow);
		
		explodingArrow = new ItemExplodingArrow();
//		explosionBow = new ItemExplosionBow();
		explodingArrowBowAndQuiver = new ItemBowAndQuiver("explodingArrowBowAndQuiver", explodingArrow);
		
		(enderShard = new Item()).setUnlocalizedName("enderShard").setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(enderShard, "ender_shard");
		teleportArrow = new ItemTeleportArrow();
//		teleportBow = new ItemTeleportBow();
		teleportArrowBowAndQuiver = new ItemBowAndQuiver("teleportArrowBowAndQuiver", teleportArrow);
		
		ironArrow = new ItemIronArrow();
		ironArrowBowAndQuiver = new ItemBowAndQuiver("ironArrowBowAndQuiver", ironArrow);
		
		poisonArrow = new ItemPoisonArrow();
		poisonArrowBowAndQuiver = new ItemBowAndQuiver("poisonArrowBowAndQuiver", poisonArrow);
		
		waterArrow = new ItemWaterArrow();
		waterArrowBowAndQuiver = new ItemBowAndQuiver("waterArrowBowAndQuiver", waterArrow);

		lavaArrow = new ItemLavaArrow();
		lavaArrowBowAndQuiver = new ItemBowAndQuiver("lavaArrowBowAndQuiver", lavaArrow);

		GameRegistry.registerItem(emptyBowAndQuiver, "emptybowandquiver");

		// Don't register vanillaArrow, as it is just a wrapper and doesn't represent a new item
		GameRegistry.registerItem(vanillaArrowBowAndQuiver, "vanillaarrowbowandquiver");

		GameRegistry.registerItem(torchArrow, "torch_arrow");
//		GameRegistry.registerItem(torchBow, "torch_bow");
		GameRegistry.registerItem(torchArrowBowAndQuiver, "torcharrowbowandquiver");
		
		GameRegistry.registerItem(explodingArrow, "exploding_arrow");
//		GameRegistry.registerItem(explosionBow, "explosion_bow");
		GameRegistry.registerItem(explodingArrowBowAndQuiver, "explodingarrowbowandquiver");

		GameRegistry.registerItem(teleportArrow, "teleport_arrow");
//		GameRegistry.registerItem(teleportBow, "teleport_bow");
		GameRegistry.registerItem(teleportArrowBowAndQuiver, "teleportarrowbowandquiver");

		GameRegistry.registerItem(ironArrow, "iron_arrow");
		GameRegistry.registerItem(ironArrowBowAndQuiver, "ironarrowbowandquiver");

		GameRegistry.registerItem(poisonArrow, "poison_arrow");
		GameRegistry.registerItem(poisonArrowBowAndQuiver, "poisonarrowbowandquiver");
		
		GameRegistry.registerItem(waterArrow, "water_arrow");
		GameRegistry.registerItem(waterArrowBowAndQuiver, "waterarrowbowandquiver");
		
		GameRegistry.registerItem(lavaArrow, "lava_arrow");
		GameRegistry.registerItem(lavaArrowBowAndQuiver, "lavaarrowbowandquiver");
		
		ffqEventHandler = new FFQEventHandler();
		MinecraftForge.EVENT_BUS.register(ffqEventHandler);
		FMLCommonHandler.instance().bus().register(ffqEventHandler);
	}
	
	@EventHandler
	public void init (FMLInitializationEvent event) {
		ModFFQuiver.proxy.init(event);
		FFQLogger.info("Initializing " + ModFFQuiver.MODID);
		addRecipes();
		addSmelting();
	}
	
	private void addRecipes() {
//		GameRegistry.addRecipe(new ItemStack(torchBow),
//				"IS ",
//				"I T",
//				"IS ",
//				'T', Blocks.torch,
//				'S', Items.stick,
//				'I', Items.string);
//		GameRegistry.addRecipe(new ItemStack(explosionBow),
//				"IS ",
//				"I G",
//				"IS ",
//				'G', Items.gunpowder,
//				'S', Items.stick,
//				'I', Items.string);
//		GameRegistry.addRecipe(new ItemStack(teleportBow),
//				"IS ",
//				"I E",
//				"IS ",
//				'E', Items.ender_pearl,
//				'S', Items.stick,
//				'I', Items.string);
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
		GameRegistry.addShapelessRecipe(new ItemStack(poisonArrow, 3),
				new Object[] {Items.arrow, Items.arrow, Items.arrow, new ItemStack(Items.potionitem, 1, 8196)});
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
		((ItemBowAndQuiver)poisonArrowBowAndQuiver).addRepairRecipes();
		((ItemBowAndQuiver)waterArrowBowAndQuiver).addRepairRecipes();
		((ItemBowAndQuiver)lavaArrowBowAndQuiver).addRepairRecipes();

	}
	
	private void addSmelting() {
	}

}
