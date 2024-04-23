package snownee.jade.addon.mixin.lootr.v0731;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import noobanidus.mods.lootr.block.LootrBarrelBlock;
import noobanidus.mods.lootr.block.LootrChestBlock;
import noobanidus.mods.lootr.block.LootrInventoryBlock;
import noobanidus.mods.lootr.block.LootrShulkerBlock;
import noobanidus.mods.lootr.block.LootrTrappedChestBlock;
import noobanidus.mods.lootr.entity.LootrChestMinecartEntity;
import snownee.jade.addon.lootr.LootrEntityInfoProvider;
import snownee.jade.addon.lootr.LootrInfoProvider;
import snownee.jade.addon.lootr.LootrInventoryProvider;
import snownee.jade.addon.lootr.LootrPlugin;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;


@Restriction(
		require = @Condition(value = "lootr", versionPredicates = ">=" + LootrPlugin.REFACTOR_VERSION)
)
@Mixin(value = LootrPlugin.class, remap = false)
public class LootrPluginMixin implements IWailaPlugin {

	/**
	 * @author SettingDust
	 * @reason Compat Lootr renamed package
	 */
	@Override
	@Overwrite
	public void register(IWailaCommonRegistration registration) {
		registration.registerBlockDataProvider(LootrInfoProvider.INSTANCE, RandomizableContainerBlockEntity.class);
		registration.registerEntityDataProvider(LootrEntityInfoProvider.INSTANCE, LootrChestMinecartEntity.class);

		registration.registerItemStorage(LootrInventoryProvider.INSTANCE, RandomizableContainerBlockEntity.class);
		registration.registerItemStorage(LootrInventoryProvider.INSTANCE, LootrChestMinecartEntity.class);
	}

	/**
	 * @author SettingDust
	 * @reason Compat Lootr renamed package
	 */
	@Override
	@Overwrite
	@Environment(EnvType.CLIENT)
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerBlockComponent(LootrInfoProvider.INSTANCE, LootrBarrelBlock.class);
		registration.registerBlockComponent(LootrInfoProvider.INSTANCE, LootrChestBlock.class);
		registration.registerBlockComponent(LootrInfoProvider.INSTANCE, LootrInventoryBlock.class);
		registration.registerBlockComponent(LootrInfoProvider.INSTANCE, LootrShulkerBlock.class);
		registration.registerBlockComponent(LootrInfoProvider.INSTANCE, LootrTrappedChestBlock.class);
		registration.registerEntityComponent(LootrEntityInfoProvider.INSTANCE, LootrChestMinecartEntity.class);

		registration.registerItemStorageClient(LootrInventoryProvider.INSTANCE);
	}
}
