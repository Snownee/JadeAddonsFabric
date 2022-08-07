package snownee.jade.addon.create;

import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.Contraption;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import snownee.jade.JadeCommonConfig;
import snownee.jade.addon.fabric.BlockInventoryProvider;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum ContraptionInventoryProvider implements IEntityComponentProvider, IServerDataProvider<Entity> {
	INSTANCE;

	@Override
	public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
		BlockInventoryProvider.append(tooltip, accessor);
	}

	@Override
	public void appendServerData(CompoundTag data, ServerPlayer player, Level level, Entity entity, boolean details) {
		Contraption contraption = ((AbstractContraptionEntity) entity).getContraption();
		int size = details ? JadeCommonConfig.inventoryDetailedShowAmount : JadeCommonConfig.inventoryNormalShowAmount;
		BlockInventoryProvider.putInvData(data, new SimpleContainer(TransferUtil.getItems(contraption.getSharedInventory(), size).toArray(ItemStack[]::new)), size, 0);
	}

	@Override
	public ResourceLocation getUid() {
		return CreatePlugin.CONTRAPTION_INVENTORY;
	}

}
