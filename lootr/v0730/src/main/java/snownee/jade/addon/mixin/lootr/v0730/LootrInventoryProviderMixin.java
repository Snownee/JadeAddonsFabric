package snownee.jade.addon.mixin.lootr.v0730;

import java.util.List;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.zestyblaze.lootr.api.blockentity.ILootBlockEntity;
import net.zestyblaze.lootr.data.DataStorage;
import net.zestyblaze.lootr.data.SpecialChestInventory;
import net.zestyblaze.lootr.entity.LootrChestMinecartEntity;
import snownee.jade.addon.lootr.LootrInventoryProvider;
import snownee.jade.addon.lootr.LootrPlugin;
import snownee.jade.addon.universal.ItemStorageProvider;
import snownee.jade.api.view.IServerExtensionProvider;
import snownee.jade.api.view.ItemView;
import snownee.jade.api.view.ViewGroup;

@Restriction(
		require = @Condition(value = "lootr", versionPredicates = "<" + LootrPlugin.REFACTOR_VERSION)
)
@Mixin(value = LootrInventoryProvider.class, remap = false)
public abstract class LootrInventoryProviderMixin implements IServerExtensionProvider<Object, ItemStack> {
	/**
	 * @author SettingDust
	 * @reason Compat Lootr renamed package
	 */
	@Overwrite
	@Override
	public List<ViewGroup<ItemStack>> getGroups(
			ServerPlayer player,
			ServerLevel level,
			Object target,
			boolean showDetails
	) {
		if (target instanceof ILootBlockEntity tile) {
			if (tile.getOpeners().contains(player.getUUID())) {
				UUID id = tile.getTileId();
				SpecialChestInventory inv = DataStorage.getInventory(
						level,
						id,
						tile.getPosition(),
						player,
						(RandomizableContainerBlockEntity) tile,
						tile::unpackLootTable
				);
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
