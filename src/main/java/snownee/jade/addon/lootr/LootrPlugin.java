package snownee.jade.addon.lootr;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

/**
 * @see snownee.jade.addon.mixin.lootr.v0730
 * @see snownee.jade.addon.mixin.lootr.v0731
 */
@WailaPlugin(LootrPlugin.ID)
public class LootrPlugin implements IWailaPlugin {
	public static final String ID = "lootr";
	public static final ResourceLocation INFO = new ResourceLocation(ID, "info");
	public static final ResourceLocation INVENTORY = new ResourceLocation(ID, "inventory");
	public static final String REFACTOR_VERSION = "0.7.31.78";

	@Override
	public void register(IWailaCommonRegistration registration) {
		throw new IllegalStateException();
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void registerClient(IWailaClientRegistration registration) {
		throw new IllegalStateException();
	}
}
