package com.mitosv.displayitem.block.displayblock;

import com.mitosv.displayitem.block.RegisterBlocks;
import com.mitosv.displayitem.block.displayblock.item.RegisterItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MarkBlockEntity extends BlockEntity {

    private Item item;

    private double transX = 0.5, transY = 0.5, transZ = 0.5;

    private float xA = 0, yA = 90;

    private float scale = 0.5f;


    public MarkBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlocks.DISPLAY_BLOCK_ENTITY, pos, state);
        item = RegisterItems.MARK_ITEM;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putString("display", MarkBlock.getRealNameItemComplete(item));
        nbt.putFloat("scale",scale);
        nbt.putFloat("xa",xA);
        nbt.putFloat("ya",yA);
        nbt.putDouble("xt",transX);
        nbt.putDouble("yt",transY);
        nbt.putDouble("zt",transZ);


        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        MarkBlock.secureSetAllValues(this,nbt);
        setItem(MarkBlock.getItemFromName(nbt.getString("display")));
    }

    public void setAllValues(float s, double x, double y, double z, float xA, float yA){
        setScale(s);
        setTransX(x);
        setTransY(y);
        setTransZ(z);
        setxA(xA);
        setyA(yA);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public float getxA() {
        return xA;
    }

    public void setxA(float xA) {
        this.xA = xA;
    }

    public float getyA() {
        return yA;
    }

    public void setyA(float yA) {
        this.yA = yA;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getTransX() {
        return transX;
    }

    public void setTransX(double transX) {
        this.transX = transX;
    }

    public double getTransY() {
        return transY;
    }

    public void setTransY(double transY) {
        this.transY = transY;
    }

    public double getTransZ() {
        return transZ;
    }

    public void setTransZ(double transZ) {
        this.transZ = transZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
