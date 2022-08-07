package snownee.jade.addon.lootr;

import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.zestyblaze.lootr.api.blockentity.ILootBlockEntity;
import net.zestyblaze.lootr.data.DataStorage;
import net.zestyblaze.lootr.data.SpecialChestInventory;
import snownee.jade.JadeCommonConfig;
import snownee.jade.addon.fabric.BlockInventoryProvider;
import snownee.jade.api.IServerDataProvider;

public enum LootrInventoryProvider implements IServerDataProvider<BlockEntity> {
	INSTANCE;

	@Override
	public void appendServerData(CompoundTag data, ServerPlayer player, Level level, BlockEntity blockEntity, boolean details) {
		if (blockEntity instanceof ILootBlockEntity tile) {
			if (tile.getOpeners().contains(player.getUUID())) {
				data.remove("Loot");
				int size = details ? JadeCommonConfig.inventoryDetailedShowAmount : JadeCommonConfig.inventoryNormalShowAmount;
				if (size == 0) {
					return;
				}
				UUID id = tile.getTileId();
				SpecialChestInventory inv = DataStorage.getInventory(level, id, blockEntity.getBlockPos(), player, (RandomizableContainerBlockEntity) blockEntity, tile::unpackLootTable);
				if (inv != null) {
					BlockInventoryProvider.putInvData(data, inv, size, 0);
				}
			}
		}
	}

	@Override
	public ResourceLocation getUid() {
		return LootrPlugin.INV;
	}

	@Override
	public int getDefaultPriority() {
		return BlockInventoryProvider.INSTANCE.getDefaultPriority() + 1;
	}

}
