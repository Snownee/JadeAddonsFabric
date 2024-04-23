package snownee.jade.addon.mixin.lootr.v0730;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.nbt.CompoundTag;
import net.zestyblaze.lootr.api.blockentity.ILootBlockEntity;
import net.zestyblaze.lootr.data.DataStorage;
import snownee.jade.addon.lootr.LootrInfoProvider;
import snownee.jade.addon.lootr.LootrPlugin;
import snownee.jade.api.BlockAccessor;

@Restriction(
		require = @Condition(value = "lootr", versionPredicates = "<" + LootrPlugin.REFACTOR_VERSION)
)
@Mixin(LootrInfoProvider.class)
public class LootrInfoProviderMixin {

	/**
	 * @author SettingDust
	 * @reason Compat Lootr renamed package
	 */
	@Overwrite
	public void appendServerData(CompoundTag data, BlockAccessor accessor) {
		if (accessor.getBlockEntity() instanceof ILootBlockEntity tile) {
			appendServerData(data, tile.getTileId());
		}
	}

	/**
	 * @author SettingDust
	 * @reason Compat Lootr renamed package
	 */
	@Overwrite
	public static void appendServerData(CompoundTag data, UUID id) {
		if (!DataStorage.isDecayed(id)) {
			int decayValue = DataStorage.getDecayValue(id);
			if (decayValue > 0) {
				data.putInt("LootrDecay", decayValue);
			}
		}
		if (DataStorage.isRefreshed(id)) {
			data.putBoolean("LootrRefreshed", true);
		} else {
			int refreshValue = DataStorage.getRefreshValue(id);
			if (refreshValue > 0) {
				data.putInt("LootrRefresh", refreshValue);
			}
		}
	}
}
