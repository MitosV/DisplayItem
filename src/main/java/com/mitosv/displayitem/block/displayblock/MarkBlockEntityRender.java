package com.mitosv.displayitem.block.displayblock;

import com.mitosv.displayitem.block.RegisterBlocks;
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
import net.minecraft.item.ItemStack;
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

        /*if (!MinecraftClient.getInstance().player
                .getActiveItem().isItemEqual(RegisterBlocks.DISPLAY_BLOCK.asItem().getDefaultStack()))return;*/

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        if (!player.isHolding(RegisterItems.MARK_ITEM)) return;

        ItemStack itemStack = entity.getItem().getDefaultStack();

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



    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
