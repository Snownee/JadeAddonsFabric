package snownee.jade.addon.secret_rooms;

import com.github.spaceman.SecretRooms;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.addon.JadeAddons;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.impl.config.PluginConfig;

@WailaPlugin(SecretRoomsPlugin.ID)
public class SecretRoomsPlugin implements IWailaPlugin {
	public static final String ID = "secretrooms";
	public static final ResourceLocation MAIN = new ResourceLocation(ID, JadeAddons.ID);

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.addConfig(MAIN, true);
		registration.hideTarget(SecretRooms.SOLID_AIR_BLOCK);
		registration.addRayTraceCallback((hit, accessor, original) -> {
			if (!PluginConfig.INSTANCE.get(MAIN))
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
