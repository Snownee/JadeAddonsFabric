package snownee.jade.addon.mi;

import java.util.List;
import java.util.Objects;

import com.google.common.math.LongMath;

import aztech.modern_industrialization.api.machine.component.CrafterAccess;
import aztech.modern_industrialization.api.machine.component.EnergyAccess;
import aztech.modern_industrialization.api.machine.holder.CrafterComponentHolder;
import aztech.modern_industrialization.api.machine.holder.EnergyComponentHolder;
import aztech.modern_industrialization.api.machine.holder.EnergyListComponentHolder;
import aztech.modern_industrialization.pipes.electricity.ElectricityNetworkNode;
import aztech.modern_industrialization.pipes.impl.PipeBlockEntity;
import aztech.modern_industrialization.pipes.impl.PipeVoxelShape;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.Accessor;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ui.IDisplayHelper;
import snownee.jade.api.view.ClientViewGroup;
import snownee.jade.api.view.EnergyView;
import snownee.jade.api.view.IClientExtensionProvider;
import snownee.jade.api.view.IServerExtensionProvider;
import snownee.jade.api.view.ViewGroup;

public enum MIEnergyProvider
		implements IServerExtensionProvider<BlockEntity, CompoundTag>, IClientExtensionProvider<CompoundTag, EnergyView> {
	INSTANCE;

	@Override
	public ResourceLocation getUid() {
		return MIPlugin.ENERGY;
	}

	@Override
	public List<ClientViewGroup<EnergyView>> getClientGroups(Accessor<?> accessor, List<ViewGroup<CompoundTag>> groups) {
		if (accessor instanceof BlockAccessor blockAccessor && blockAccessor.getBlockEntity() instanceof PipeBlockEntity) {
			PipeVoxelShape shape = PipeProvider.getHitShape(blockAccessor);
			if (shape != null) {
				CompoundTag tag = accessor.getServerData().getCompound(shape.type.getIdentifier().toString());
				EnergyView view = EnergyView.read(tag, "EU");
				if (view != null) {
					return List.of(new ClientViewGroup<>(List.of(view)));
				}
			}
			return List.of();
		} else {
			return groups.stream().map($ -> {
				return new ClientViewGroup<>($.views.stream().map(tag -> {
					var view = EnergyView.read(tag, "EU");
					if (view != null && tag.contains("RecipeEu")) {
						view.overrideText = Component.literal(view.current).withStyle(ChatFormatting.WHITE).append(" (%s/t)".formatted(
								IDisplayHelper.get().humanReadableNumber(tag.getLong("RecipeEu"), "EU", false)));
					}
					return view;
				}).filter(Objects::nonNull).toList());
			}).toList();
		}
	}

	@Override
	public List<ViewGroup<CompoundTag>> getGroups(ServerPlayer player, ServerLevel level, BlockEntity target, boolean showDetails) {
		var groups = getGroups(target);
		if (groups != null && target instanceof CrafterComponentHolder) {
			CrafterAccess component = ((CrafterComponentHolder) target).getCrafterComponent();
			if (component.hasActiveRecipe()) {
				CompoundTag tag = groups.get(0).views.get(0);
				tag.putLong("RecipeEu", component.getCurrentRecipeEu());
			}
		}
		return groups;
	}

	private List<ViewGroup<CompoundTag>> getGroups(BlockEntity target) {
		if (target instanceof EnergyListComponentHolder) {
			long amount = 0, capacity = 0;
			for (EnergyAccess component : ((EnergyListComponentHolder) target).getEnergyComponents()) {
				amount = LongMath.saturatedAdd(amount, component.getEu());
				capacity = LongMath.saturatedAdd(capacity, component.getCapacity());
			}
			if (capacity > 0) {
				return List.of(new ViewGroup<>(List.of(EnergyView.of(amount, capacity))));
			}
		}
		if (target instanceof EnergyComponentHolder) {
			EnergyAccess component = ((EnergyComponentHolder) target).getEnergyComponent();
			if (component != null && component.getCapacity() > 0) {
				return List.of(new ViewGroup<>(List.of(EnergyView.of(component.getEu(), component.getCapacity()))));
			}
		}
		if (target instanceof PipeBlockEntity cable) {
			if (cable.getNodes().stream().anyMatch($ -> $ instanceof ElectricityNetworkNode)) {
				// add a placeholder energy view
				return List.of(new ViewGroup<>(List.of(EnergyView.of(0, 0))));
			}
		}
		return null;
	}

}
