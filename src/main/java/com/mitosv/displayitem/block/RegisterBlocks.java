package com.mitosv.displayitem.block;

import com.mitosv.displayitem.DisplayItem;
import com.mitosv.displayitem.block.displayblock.MarkBlock;
import com.mitosv.displayitem.block.displayblock.MarkBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RegisterBlocks {


    public static BlockEntityType<MarkBlockEntity> DISPLAY_BLOCK_ENTITY;

    public static final Block MARK_BLOCK = registerBlock("mark_block",
            new MarkBlock(FabricBlockSettings.of(Material.METAL).collidable(false).noCollision()
                    .strength(-1.0F, 3600000.0F).dropsNothing()));


    private static Block registerBlock(String name, Block block){
        return registerBlockWithoutBlockItem(name,block);
    }

    private static Block registerBlock(String name, Block block, ItemGroup group, String tooltipKey) {
        registerBlockItem(name, block, group, tooltipKey);
        return Registry.register(Registry.BLOCK, new Identifier(DisplayItem.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block, ItemGroup group, String tooltipKey) {
        Registry.register(Registry.ITEM, new Identifier(DisplayItem.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)) {
                    @Override
                    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip,
                                              TooltipContext context) {
                        tooltip.add(Text.translatable(tooltipKey));
                    }
                });

    }

    private static Block registerBlockWithoutBlockItem(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(DisplayItem.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(DisplayItem.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(DisplayItem.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks() {
        DISPLAY_BLOCK_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new Identifier("testblock", "demo_block_entity"),
                FabricBlockEntityTypeBuilder.create(MarkBlockEntity::new, MARK_BLOCK).build()
        );
    }

}
