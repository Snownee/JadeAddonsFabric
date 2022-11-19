package snownee.jade.addon.mi;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import aztech.modern_industrialization.pipes.api.PipeNetworkNode;
import aztech.modern_industrialization.pipes.electricity.ElectricityNetworkNode;
import aztech.modern_industrialization.pipes.fluid.FluidNetworkNode;
import aztech.modern_industrialization.pipes.impl.PipeBlockEntity;
import aztech.modern_industrialization.pipes.impl.PipeVoxelShape;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import snownee.jade.api.Accessor;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.view.ClientViewGroup;
import snownee.jade.api.view.EnergyView;
import snownee.jade.api.view.FluidView;
import snownee.jade.api.view.IClientExtensionProvider;
import snownee.jade.api.view.IServerExtensionProvider;
import snownee.jade.api.view.ViewGroup;

public enum PipeProvider implements IServerDataProvider<BlockEntity>,
		IServerExtensionProvider<PipeBlockEntity, CompoundTag>, IClientExtensionProvider<CompoundTag, FluidView> {
	INSTANCE;

	public static @Nullable PipeVoxelShape getHitShape(BlockAccessor accessor) {
		PipeBlockEntity pipe = (PipeBlockEntity) accessor.getBlockEntity();
		Vec3 hitPos = accessor.getHitResult().getLocation();
		BlockPos blockPos = accessor.getPosition();
		for (PipeVoxelShape partShape : pipe.getPartShapes()) {
			Vec3 posInBlock = hitPos.subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ());
			for (AABB box : partShape.shape.toAabbs()) {
				// move slightly towards box center
				Vec3 dir = box.getCenter().subtract(posInBlock).normalize().scale(1e-4);
				if (box.contains(posInBlock.add(dir))) {
					return partShape;
				}
			}
		}
		return null;
	}

	@Override
	public void appendServerData(CompoundTag data, ServerPlayer player, Level level, BlockEntity blockEntity, boolean details) {
		for (PipeNetworkNode node : ((PipeBlockEntity) blockEntity).getNodes()) {
			CompoundTag pipeData = null;

			if (node instanceof FluidNetworkNode fluidNode) {
				var info = fluidNode.collectNetworkInfo();
				pipeData = FluidView.fromFluidVariant(info.fluid(), Math.max(info.transfer(), info.stored()), info.capacity());
			}

			if (node instanceof ElectricityNetworkNode electricityNode) {
				var info = electricityNode.collectNetworkInfo();
				pipeData = EnergyView.of(Math.max(info.transfer(), info.stored()), info.capacity());
			}

			//			if (node instanceof ItemNetworkNode itemNode) {
			//				var info = itemNode.collectNetworkInfo();
			//				pipeData.putLong("items", info.movedItems());
			//				pipeData.putInt("pulse", info.pulse());
			//			}

			if (pipeData != null) {
				data.put(node.getType().getIdentifier().toString(), pipeData);
			}
		}
	}

	@Override
	public ResourceLocation getUid() {
		return MIPlugin.PIPE;
	}

	@Override
	public List<ClientViewGroup<FluidView>> getClientGroups(Accessor<?> accessor, List<ViewGroup<CompoundTag>> groups) {
		if (accessor instanceof BlockAccessor blockAccessor && blockAccessor.getBlockEntity() instanceof PipeBlockEntity) {
			PipeVoxelShape shape = PipeProvider.getHitShape(blockAccessor);
			if (shape != null) {
				CompoundTag tag = accessor.getServerData().getCompound(shape.type.getIdentifier().toString());
				FluidView view = FluidView.read(tag);
				if (view != null) {
					view.overrideText = null;
					return List.of(new ClientViewGroup<>(List.of(view)));
				}
			}
		}
		return List.of();
	}

	@Override
	public @Nullable List<ViewGroup<CompoundTag>> getGroups(ServerPlayer player, ServerLevel level, PipeBlockEntity pipe, boolean details) {
		if (pipe.getNodes().stream().anyMatch($ -> $ instanceof FluidNetworkNode)) {
			return List.of(new ViewGroup<>(List.of(FluidView.fromFluidVariant(FluidVariant.blank(), 1, 1))));
		}
		return List.of();
	}

}
