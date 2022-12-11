package com.mitosv.displayitem.block.displayblock;

import com.mitosv.displayitem.block.RegisterBlocks;
import com.mitosv.displayitem.block.displayblock.item.RegisterItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class MarkBlock extends BlockWithEntity {

    public static HashMap<String, String> allTags = new HashMap<>() {{
       put("X","xt");put("Y","yt");put("Z","zt");
        put("Angule X","xa");put("Angule Y","ya");
        put("Scale", "scale");
    }};

    public MarkBlock(Settings settings) {
        super(settings);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return context.isHolding(RegisterItems.MARK_ITEM) ? VoxelShapes.fullCube() : VoxelShapes.empty();
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if (!stack.hasNbt())return;
        if (stack.getNbt().get("id") == null) return;

        Item item = getItemFromName(stack.getNbt().getString("id"));

        tooltip.add(item.getName());

        for (String tag : allTags.keySet()){
            if (stack.getNbt().get(allTags.get(tag)) != null)
                tooltip.add(Text.of(tag+": "+
                        stack.getNbt().getDouble(allTags.get(tag))));
        }

    }

    public static Item getItemFromName(String fullName){
        String[] mine = fullName.replace("\"","").split(":");
        Item item = Registry.ITEM.get(new Identifier(mine[0]));
        if (mine.length>1){
            item = Registry.ITEM.get(new Identifier(mine[0],mine[1]));
        }
        return item;
    }



    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!itemStack.hasNbt())return;
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (itemStack.getNbt().get("id") == null) return;
        if (blockEntity instanceof MarkBlockEntity displayBlock){
            NbtCompound nbt = itemStack.getNbt();
            Item item = getItemFromName(nbt.getString("id"));
            displayBlock.setItem(item);
            secureSetAllValues(displayBlock, nbt);
        }

    }
    public static void secureSetAllValues(MarkBlockEntity entity, NbtCompound nbt){
        if (nbt.get("scale")!=null) entity.setScale(nbt.getFloat("scale"));
        if (nbt.get("xt")!=null) entity.setTransX(nbt.getFloat("xt"));
        if (nbt.get("yt")!=null) entity.setTransY(nbt.getFloat("yt"));
        if (nbt.get("zt")!=null) entity.setTransZ(nbt.getFloat("zt"));
        if (nbt.get("xa")!=null) entity.setxA(nbt.getFloat("xa"));
        if (nbt.get("ya")!=null) entity.setyA(nbt.getFloat("ya"));
    }

    public static String[] getRealNameItem(Item item){
        String[] a = item.getTranslationKey().split("\\.");
        if (a[0].equals("block") || a[0].equals("item"))
            return new String[]{a[1],a[2]};
        return null;
    }
    public static String getRealNameItemComplete(Item item){
        String[] a = item.getTranslationKey().split("\\.");
        if (a[0].equals("block") || a[0].equals("item"))
            return a[1].concat(":").concat(a[2]);
        return null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MarkBlockEntity(pos,state);
    }
}


