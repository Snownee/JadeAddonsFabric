package snownee.jade.addon.mi;

import java.util.List;
import java.util.Objects;

import com.google.common.math.LongMath;

import aztech.modern_industrialization.compat.megane.holder.CrafterComponentHolder;
import aztech.modern_industrialization.compat.megane.holder.EnergyComponentHolder;
import aztech.modern_industrialization.compat.megane.holder.EnergyListComponentHolder;
import aztech.modern_industrialization.machines.components.CrafterComponent;
import aztech.modern_industrialization.machines.components.EnergyComponent;
import aztech.modern_industrialization.pipes.electricity.ElectricityNetworkNode;
import aztech.modern_industrialization.pipes.impl.PipeBlockEntity;
import aztech.modern_industrialization.pipes.impl.PipeVoxelShape;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import snownee.jade.api.Accessor;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ui.IDisplayHelper;
import snownee.jade.api.view.ClientViewGroup;
import snownee.jade.api.view.EnergyView;
import snownee.jade.api.view.IClientExtensionProvider;
import snownee.jade.api.view.IServerExtensionProvider;
import snownee.jade.api.view.ViewGroup;

public enum MIEnergyProvider
		implements IServerExtensionProvider<Object, CompoundTag>, IClientExtensionProvider<CompoundTag, EnergyView> {
	INSTANCE;

	@Override
	public ResourceLocation getUid() {
		return MIPlugin.ENERGY;
	}

	@Override
	public List<ClientViewGroup<EnergyView>> getClientGroups(Accessor<?> accessor, List<ViewGroup<CompoundTag>> groups) {
//		if (accessor instanceof BlockAccessor blockAccessor && blockAccessor.getBlockEntity() instanceof PipeBlockEntity) {
//			PipeVoxelShape shape = PipeProvider.getHitShape(blockAccessor);
//			if (shape != null) {
//				CompoundTag tag = accessor.getServerData().getCompound(shape.type.getIdentifier().toString());
//				EnergyView view = EnergyView.read(tag, "EU");
//				if (view != null) {
//					return List.of(new ClientViewGroup<>(List.of(view)));
//				}
//			}
//			return List.of();
//		} else {
			return groups.stream().map($ -> {
				return new ClientViewGroup<>($.views.stream().map(tag -> {
					var view = EnergyView.read(tag, "EU");
					if (view != null && tag.contains("RecipeEu")) {
						view.overrideText = new TextComponent(view.current).withStyle(ChatFormatting.WHITE).append(" (%s/t)".formatted(IDisplayHelper.get().humanReadableNumber(tag.getLong("RecipeEu"), "EU", false)));
					}
					return view;
				}).filter(Objects::nonNull).toList());
			}).toList();
//		}
	}

	@Override
	public List<ViewGroup<CompoundTag>> getGroups(ServerPlayer player, ServerLevel level, Object target, boolean showDetails) {
		var groups = getGroups(target);
		if (groups != null && target instanceof CrafterComponentHolder) {
			CrafterComponent component = ((CrafterComponentHolder) target).getCrafterComponent();
			if (component.hasActiveRecipe()) {
				CompoundTag tag = groups.get(0).views.get(0);
				tag.putLong("RecipeEu", component.getCurrentRecipeEu());
			}
		}
		return groups;
	}

	private List<ViewGroup<CompoundTag>> getGroups(Object target) {
		if (target instanceof EnergyListComponentHolder) {
			long amount = 0, capacity = 0;
			for (EnergyComponent component : ((EnergyListComponentHolder) target).getEnergyComponents()) {
				amount = LongMath.saturatedAdd(amount, component.getEu());
				capacity = LongMath.saturatedAdd(capacity, component.getCapacity());
			}
			if (capacity > 0) {
				return List.of(new ViewGroup<>(List.of(EnergyView.of(amount, capacity))));
			}
		}
		if (target instanceof EnergyComponentHolder) {
			EnergyComponent component = ((EnergyComponentHolder) target).getEnergyComponent();
			if (component != null && component.getCapacity() > 0) {
				return List.of(new ViewGroup<>(List.of(EnergyView.of(component.getEu(), component.getCapacity()))));
			}
		}
		//		var storage = PlatformProxy.lookupBlock(EnergyApi.SIDED, target);
		//		if (storage != null) {
		//			return List.of(new ViewGroup<>(List.of(EnergyView.of(storage.getAmount(), storage.getCapacity()))));
		//		}
		if (target instanceof PipeBlockEntity cable) {
			if (cable.getNodes().stream().anyMatch($ -> $ instanceof ElectricityNetworkNode)) {
				// add a placeholder energy view
				return List.of(new ViewGroup<>(List.of(EnergyView.of(1, 1))));
			}
		}
		return null;
	}

}
