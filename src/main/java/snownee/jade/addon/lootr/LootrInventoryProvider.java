package snownee.jade.addon.lootr;

import java.util.List;
import java.util.UUID;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.zestyblaze.lootr.api.blockentity.ILootBlockEntity;
import net.zestyblaze.lootr.data.DataStorage;
import net.zestyblaze.lootr.data.SpecialChestInventory;
import net.zestyblaze.lootr.entity.LootrChestMinecartEntity;
import snownee.jade.addon.universal.ItemStorageProvider;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.ClientViewGroup;
import snownee.jade.api.view.IClientExtensionProvider;
import snownee.jade.api.view.IServerExtensionProvider;
import snownee.jade.api.view.ItemView;
import snownee.jade.api.view.ViewGroup;

public enum LootrInventoryProvider
		implements IServerExtensionProvider<Object, ItemStack>, IClientExtensionProvider<ItemStack, ItemView> {
	INSTANCE;

	@Override
	public ResourceLocation getUid() {
		return LootrPlugin.INVENTORY;
	}

	@Override
	public List<ClientViewGroup<ItemView>> getClientGroups(Accessor<?> accessor, List<ViewGroup<ItemStack>> groups) {
		return ClientViewGroup.map(groups, ItemView::new, null);
	}

	@Override
	public List<ViewGroup<ItemStack>> getGroups(ServerPlayer player, ServerLevel level, Object target, boolean showDetails) {
		if (target instanceof ILootBlockEntity tile) {
			if (tile.getOpeners().contains(player.getUUID())) {
				UUID id = tile.getTileId();
				SpecialChestInventory inv = DataStorage.getInventory(
						level,
						id,
						tile.getPosition(),
						player,
						(RandomizableContainerBlockEntity) tile,
						tile::unpackLootTable);
				if (inv != null) {
					return List.of(ItemView.fromContainer(inv, 54, 0));
				}
			}
		} else if (target instanceof LootrChestMinecartEntity cart) {
			if (cart.getOpeners().contains(player.getUUID())) {
				SpecialChestInventory inv = DataStorage.getInventory(level, cart, player, cart::addLoot);
				if (inv != null) {
					return List.of(ItemView.fromContainer(inv, 54, 0));
				}
			}
		}
		return ItemStorageProvider.INSTANCE.getGroups(player, level, target, showDetails);
	}

}
