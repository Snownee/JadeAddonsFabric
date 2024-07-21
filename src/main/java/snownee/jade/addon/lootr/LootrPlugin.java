package snownee.jade.addon.lootr;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.zestyblaze.lootr.block.LootrBarrelBlock;
import net.zestyblaze.lootr.block.LootrChestBlock;
import net.zestyblaze.lootr.block.LootrInventoryBlock;
import net.zestyblaze.lootr.block.LootrShulkerBlock;
import net.zestyblaze.lootr.block.LootrTrappedChestBlock;
import net.zestyblaze.lootr.entity.LootrChestMinecartEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin(LootrPlugin.ID)
public class LootrPlugin implements IWailaPlugin {
	public static final String ID = "lootr";
	public static final ResourceLocation INFO = new ResourceLocation(ID, "info");
	public static final ResourceLocation INVENTORY = new ResourceLocation(ID, "inventory");

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerBlockDataProvider(LootrInfoProvider.INSTANCE, RandomizableContainerBlockEntity.class);
		registration.registerEntityDataProvider(LootrEntityInfoProvider.INSTANCE, LootrChestMinecartEntity.class);

		registration.registerItemStorage(LootrInventoryProvider.INSTANCE, RandomizableContainerBlockEntity.class);
		registration.registerItemStorage(LootrInventoryProvider.INSTANCE, LootrChestMinecartEntity.class);
	}

	@Override
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
