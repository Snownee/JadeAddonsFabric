package snownee.jade.addon.team_reborn_energy;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.addon.JadeAddons;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin(TeamRebornEnergyPlugin.ID)
public class TeamRebornEnergyPlugin implements IWailaPlugin {
	public static final String ID = "team_reborn_energy";
	public static final ResourceLocation ENABLED = new ResourceLocation(ID, JadeAddons.ID);

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerEnergyStorage(TeamRebornEnergyProvider.INSTANCE, BlockEntity.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerEnergyStorageClient(TeamRebornEnergyProvider.INSTANCE);
	}

}
