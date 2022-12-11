package com.mitosv.displayitem.block.displayblock.item;

import com.mitosv.displayitem.DisplayItem;
import com.mitosv.displayitem.block.RegisterBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterItems {


    public static final Item MARK_ITEM = registerItem("mark_block",
            new MarkItem(new FabricItemSettings(), RegisterBlocks.MARK_BLOCK));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(DisplayItem.MOD_ID, name), item);
    }

    public static void registerModItems() {

    }
}
