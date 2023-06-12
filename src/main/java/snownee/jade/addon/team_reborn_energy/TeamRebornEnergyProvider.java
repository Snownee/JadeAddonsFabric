package snownee.jade.addon.team_reborn_energy;

import java.util.List;
import java.util.Objects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.ClientViewGroup;
import snownee.jade.api.view.EnergyView;
import snownee.jade.api.view.IClientExtensionProvider;
import snownee.jade.api.view.IServerExtensionProvider;
import snownee.jade.api.view.ViewGroup;
import snownee.jade.util.PlatformProxy;
import team.reborn.energy.api.EnergyStorage;

public enum TeamRebornEnergyProvider
		implements IServerExtensionProvider<Object, CompoundTag>, IClientExtensionProvider<CompoundTag, EnergyView> {
	INSTANCE;

	@Override
	public ResourceLocation getUid() {
		return TeamRebornEnergyPlugin.ENABLED;
	}

	@Override
	public List<ClientViewGroup<EnergyView>> getClientGroups(Accessor<?> accessor, List<ViewGroup<CompoundTag>> groups) {
		return groups.stream().map($ -> {
			return new ClientViewGroup<>($.views.stream().map(tag -> EnergyView.read(tag, "E")).filter(Objects::nonNull).toList());
		}).toList();
	}

	@Override
	public List<ViewGroup<CompoundTag>> getGroups(ServerPlayer player, ServerLevel level, Object target, boolean showDetails) {
		var storage = PlatformProxy.lookupBlock(EnergyStorage.SIDED, target);
		if (storage != null) {
			return List.of(new ViewGroup<>(List.of(EnergyView.of(storage.getAmount(), storage.getCapacity()))));
		}
		return null;
	}

}
