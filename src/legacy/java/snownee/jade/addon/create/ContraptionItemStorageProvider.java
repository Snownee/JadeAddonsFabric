package snownee.jade.addon.create;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import snownee.jade.addon.universal.ItemCollector;
import snownee.jade.addon.universal.ItemStorageProvider;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.ClientViewGroup;
import snownee.jade.api.view.IClientExtensionProvider;
import snownee.jade.api.view.IServerExtensionProvider;
import snownee.jade.api.view.ItemView;
import snownee.jade.api.view.ViewGroup;
import snownee.jade.util.JadeFabricUtils;

public enum ContraptionItemStorageProvider implements IServerExtensionProvider<AbstractContraptionEntity, ItemStack>,
		IClientExtensionProvider<ItemStack, ItemView> {
	INSTANCE;

	@Override
	public ResourceLocation getUid() {
		return CreatePlugin.CONTRAPTION_INVENTORY;
	}

	@Override
	public List<ClientViewGroup<ItemView>> getClientGroups(Accessor<?> accessor, List<ViewGroup<ItemStack>> groups) {
		return ClientViewGroup.map(groups, ItemView::new, null);
	}

	@Override
	public List<ViewGroup<ItemStack>> getGroups(
			ServerPlayer player,
			ServerLevel level,
			AbstractContraptionEntity entity,
			boolean showDetails) {
		//TODO: simplify it in 1.20.2+
		Contraption contraption = ((AbstractContraptionEntity) entity).getContraption();
		Storage<ItemVariant> storage = contraption.getSharedInventory();
		try {
			return ItemStorageProvider.INSTANCE.containerCache.get(
					storage,
					() -> new ItemCollector<>(JadeFabricUtils.fromItemStorage(storage, 0, target -> (Storage<ItemVariant>) target))).update(
					storage,
					level.getGameTime());
		} catch (ExecutionException e) {
			return null;
		}
	}

}
