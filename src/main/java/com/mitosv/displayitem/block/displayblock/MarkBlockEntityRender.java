package com.mitosv.displayitem.block.displayblock;

import com.mitosv.displayitem.block.RegisterBlocks;
import com.mitosv.displayitem.block.displayblock.item.MarkItem;
import com.mitosv.displayitem.block.displayblock.item.RegisterItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class MarkBlockEntityRender implements BlockEntityRenderer<MarkBlockEntity> {


    private BlockEntityRendererFactory.Context context;

    public MarkBlockEntityRender(BlockEntityRendererFactory.Context context){
        this.context = context;
    }



    @Override
    public void render(MarkBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {


        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ClientPlayerEntity player = MinecraftClient.getInstance().player;


        assert player != null;
        ItemStack itemStack = entity.getItem().getDefaultStack();
        if (!isHolding(player, entity.getItem())) return;

        matrices.push();
        matrices.translate(entity.getTransX(), entity.getTransY(), entity.getTransZ());
        matrices.scale(entity.getScale(), entity.getScale(), entity.getScale());

        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.getyA()));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(entity.getxA()));

        itemRenderer.renderItem(itemStack, ModelTransformation.Mode.FIXED,
                LightmapTextureManager.MAX_LIGHT_COORDINATE,
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 1);

        matrices.pop();
    }

    private static boolean isHolding(ClientPlayerEntity player, Item item){
        ItemStack off = player.getOffHandStack();
        ItemStack main = player.getMainHandStack();
        if (off.isOf(RegisterItems.MARK_ITEM)) return sameItem(off,item);
        if (main.isOf(RegisterItems.MARK_ITEM)) return sameItem(main,item);
        return false;
    }


    private static boolean sameItem(ItemStack handItem, Item item){
        if (!handItem.hasNbt()) return handItem.isOf(RegisterItems.MARK_ITEM);
        if (!handItem.getNbt().contains("id"))return false;
        return MarkBlock.getItemFromName(handItem.getNbt().getString("id")).equals(item);
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
