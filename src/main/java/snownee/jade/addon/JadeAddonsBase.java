package snownee.jade.addon;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.fabricmc.loader.api.FabricLoader;
import snownee.jade.addon.create.CreatePlugin;
import snownee.jade.addon.lootr.LootrPlugin;
import snownee.jade.addon.mi.MIPlugin;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeAddonsBase implements IWailaPlugin {
	public static final Map<String, Supplier<Supplier<IWailaPlugin>>> PLUGIN_LOADERS = Maps.newHashMap();
	private final List<IWailaPlugin> plugins = Lists.newArrayList();

	static {
		PLUGIN_LOADERS.put("create", () -> CreatePlugin::new);
		PLUGIN_LOADERS.put("lootr", () -> LootrPlugin::new);
		PLUGIN_LOADERS.put("modern_industrialization", () -> MIPlugin::new);
	}

	public JadeAddonsBase() {
		PLUGIN_LOADERS.forEach((modid, loader) -> {
			if (FabricLoader.getInstance().isModLoaded(modid)) {
				plugins.add(loader.get().get());
			}
		});
	}

	@Override
	public void register(IWailaCommonRegistration registration) {
		plugins.forEach($ -> $.register(registration));
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		plugins.forEach($ -> $.registerClient(registration));
	}
}
