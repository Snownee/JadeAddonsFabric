package snownee.jade.addon.mi;

import aztech.modern_industrialization.pipes.MIPipes;
import aztech.modern_industrialization.pipes.impl.PipeBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin(MIPlugin.ID)
public class MIPlugin implements IWailaPlugin {
	public static final String ID = "modern_industrialization";
	public static final ResourceLocation ENERGY = new ResourceLocation(ID, "energy");
	public static final ResourceLocation PIPE = new ResourceLocation(ID, "pipe");

	@Override
	public void register(IWailaCommonRegistration registration) {
		//		registration.registerBlockDataProvider(PipeProvider.INSTANCE, PipeBlockEntity.class);
		registration.registerEnergyStorage(MIEnergyProvider.INSTANCE, BlockEntity.class);
		//		registration.registerFluidStorage(PipeProvider.INSTANCE, PipeBlockEntity.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerEnergyStorageClient(MIEnergyProvider.INSTANCE);
		//		registration.registerFluidStorageClient(PipeProvider.INSTANCE);
		registration.usePickedResult(MIPipes.BLOCK_PIPE);
	}

}
