package snownee.jade.addon.MTR;

import mtr.EntityTypes;
import mtr.block.BlockEscalatorStep;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.addon.JadeAddons;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin(MTRPlugin.ID)
public class MTRPlugin implements IWailaPlugin {
	public static final String ID = "mtr";
	public static final ResourceLocation ENABLED = new ResourceLocation(ID, JadeAddons.ID);
	public static final ResourceLocation Escalator = new ResourceLocation(ID, "escalator_step");

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.addConfig(ENABLED, true);
		registration.hideTarget(EntityTypes.SEAT.get());
		registration.registerBlockComponent(MTRProvider.INSTANCE, BlockEscalatorStep.class);
		//registration.hideTarget();
	}
}
