package net.flytre.flytre_lib.api.storage.inventory.filter.packet;

import net.flytre.flytre_lib.api.storage.inventory.filter.Filtered;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class BlockFilterModeC2SPacket implements Packet<ServerPlayPacketListener> {


    private final BlockPos pos;
    private final int mode;

    public BlockFilterModeC2SPacket(BlockPos pos, int mode) {
        this.pos = pos;
        this.mode = mode;
    }

    public BlockFilterModeC2SPacket(PacketByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.mode = buf.readInt();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(mode);
    }

    @Override
    public void apply(ServerPlayPacketListener listener) {
        ServerPlayerEntity player = ((ServerPlayNetworkHandler) listener).getPlayer();
        ServerWorld world = player.getServerWorld();
        world.getServer().execute(() -> {
            if (pos.getSquaredDistance(player.getX(), player.getY(), player.getZ(), false) > 36)
                return;

            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof Filtered)
                ((Filtered) entity).getFilter().setFilterType(mode);
            if (entity instanceof FilterEventHandler) {
                ((FilterEventHandler) entity).onPacketReceived();
            }
        });
    }
}
