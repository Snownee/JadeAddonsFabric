package snownee.jade.addon.MTR;

import com.github.spaceman.SecretRooms;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.addon.JadeAddons;
import snownee.jade.addon.MTR.MTRPlugin;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.impl.config.PluginConfig;

@WailaPlugin(MTRPlugin.ID)
public class MTRPlugin implements IWailaPlugin {
	public static final String ID = "mtr";
	public static final ResourceLocation ENABLED = new ResourceLocation(ID, JadeAddons.ID);

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.addConfig(ENABLED, true);
		registration.hideTarget(SecretRooms.SOLID_AIR_BLOCK);
		registration.addRayTraceCallback((hit, accessor, original) -> {
			if (!PluginConfig.INSTANCE.get(ENABLED))
				return accessor;
			if (accessor instanceof BlockAccessor blockAccessor) {
				Block block = blockAccessor.getBlock();
				BlockState camo = SecretRooms.wailaOverrides.get(block);
				if (camo != null) {
					return registration.blockAccessor().from(blockAccessor).blockState(camo).build();
				}
			}
			return accessor;
		});
	}
}
