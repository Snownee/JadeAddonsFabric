package snownee.jade.addon.mixin;

import java.util.List;
import java.util.Set;

import me.fallenbreath.conditionalmixin.api.mixin.RestrictiveMixinConfigPlugin;

public class JadeAddonsMixinPlugin extends RestrictiveMixinConfigPlugin {
	@Override
	public String getRefMapperConfig() {
		return "";
	}

	@Override
	public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) {

	}

	@Override
	public List<String> getMixins() {
		return List.of();
	}
}
