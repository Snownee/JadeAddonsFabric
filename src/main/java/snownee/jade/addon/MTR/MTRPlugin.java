package snownee.jade.addon.MTR;

import mtr.EntityTypes;
import mtr.block.BlockEscalatorBase;
import mtr.block.BlockEscalatorStep;
import mtr.block.BlockTicketBarrier;
import mtr.block.BlockTicketMachine;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.addon.JadeAddons;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin(MTRPlugin.ID)
public class MTRPlugin implements IWailaPlugin {
	public static final String ID = "mtr";
	public static final ResourceLocation ENABLED = new ResourceLocation(ID, JadeAddons.ID);
	public static final ResourceLocation ESCALATOR_STEP = new ResourceLocation(ID, "escalator_step");

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.addConfig(ENABLED, true);
		registration.hideTarget(EntityTypes.SEAT.get());
		//registration.registerBlockComponent(MTREscalatorProvider.INSTANCE, BlockEscalatorStep.class);
		registration.registerBlockComponent(MTREscalatorProvider.INSTANCE, BlockEscalatorBase.class);
	}
}
