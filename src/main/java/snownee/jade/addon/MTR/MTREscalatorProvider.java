package snownee.jade.addon.MTR;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum MTREscalatorProvider implements IBlockComponentProvider {

	INSTANCE;


	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
		tooltip.append(Component.translatable("(Add direction info here)"));
	}

	@Override
	public ResourceLocation getUid() {
		return MTRPlugin.Escalator;
	}
}
