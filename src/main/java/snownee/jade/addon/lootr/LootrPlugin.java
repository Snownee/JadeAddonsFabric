package snownee.jade.addon.lootr;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.zestyblaze.lootr.blocks.LootrBarrelBlock;
import net.zestyblaze.lootr.blocks.LootrChestBlock;
import net.zestyblaze.lootr.blocks.LootrInventoryBlock;
import net.zestyblaze.lootr.blocks.LootrShulkerBlock;
import net.zestyblaze.lootr.blocks.LootrTrappedChestBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin(LootrPlugin.ID)
public class LootrPlugin implements IWailaPlugin {
	public static final String ID = "lootr";
	public static final ResourceLocation INFO = new ResourceLocation(ID, "info");
	public static final ResourceLocation INV = new ResourceLocation(ID, "inv");

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerBlockDataProvider(LootrInventoryProvider.INSTANCE, RandomizableContainerBlockEntity.class);
		registration.registerBlockDataProvider(LootrInfoProvider.INSTANCE, RandomizableContainerBlockEntity.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerBlockComponent(LootrInfoProvider.INSTANCE, LootrBarrelBlock.class);
		registration.registerBlockComponent(LootrInfoProvider.INSTANCE, LootrChestBlock.class);
		registration.registerBlockComponent(LootrInfoProvider.INSTANCE, LootrInventoryBlock.class);
		registration.registerBlockComponent(LootrInfoProvider.INSTANCE, LootrShulkerBlock.class);
		registration.registerBlockComponent(LootrInfoProvider.INSTANCE, LootrTrappedChestBlock.class);
	}

}
