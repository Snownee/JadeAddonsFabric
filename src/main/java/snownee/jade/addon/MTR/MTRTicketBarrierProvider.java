package snownee.jade.addon.MTR;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum MTRTicketBarrierProvider implements IBlockComponentProvider {
	INSTANCE;


	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
		CompoundTag data = accessor.getServerData();
		boolean IsOpen = data.getBoolean("");
		tooltip.append(Component.translatable(""));
	}

	@Override
	public ResourceLocation getUid() {
		return MTRPlugin.TICKET_BARRIER;
	}
}
