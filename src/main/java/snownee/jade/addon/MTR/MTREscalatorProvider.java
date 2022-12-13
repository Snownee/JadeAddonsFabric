package snownee.jade.addon.MTR;

import mtr.block.IBlock;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.Objects;

import static mtr.block.BlockEscalatorStep.DIRECTION;
import static mtr.block.BlockEscalatorStep.FACING;
import static mtr.block.BlockEscalatorStep.ORIENTATION;


public enum MTREscalatorProvider implements IBlockComponentProvider {

	INSTANCE;


	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
		BlockState blockstate = accessor.getBlockState();
		boolean direction = IBlock.getStatePropertySafe(blockstate, DIRECTION);
		Direction facing = IBlock.getStatePropertySafe(blockstate, FACING);

		String ORIENTATIONToString = String.format("%s", IBlock.getStatePropertySafe(blockstate, ORIENTATION));

		//System.out.println(ORIENTATIONToString);

		if (Objects.equals(ORIENTATIONToString, "FLAT")){
			switch (facing){
				case NORTH:
					if (direction){
						tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_North"));
					}
					else{
						tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_South"));
					}
					break;
				case SOUTH:
					if (direction){
						tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_South"));
					}
					else{
						tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_North"));
					}
					break;
				case EAST:
					if (direction){
						tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_East"));
					}
					else{
						tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_West"));
					}
					break;
				case WEST:
					if (direction){
						tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_West"));
					}
					else{
						tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_East"));
					}
					break;
			}
		}

		if (Objects.equals(ORIENTATIONToString, "SLOPE")){
			if (direction){
				tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_Upward"));
			}
			else{
				tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_Downward"));
			}
		}

		if (Objects.equals(ORIENTATIONToString, "TRANSITION_BOTTOM")){
			if (direction){
				tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_Upward"));
			}
			else{
				tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_Downward"));
			}
		}

		if (Objects.equals(ORIENTATIONToString, "TRANSITION_TOP")){
			if (direction){
				tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_Upward"));
			}
			else{
				tooltip.add(Component.translatable("jadeaddons.mtr.escalator_step_Downward"));
			}
		}

		//tooltip.append(Component.translatable("(Add direction info here)" + direction + " " + facing + " " + IBlock.getStatePropertySafe(blockstate, ORIENTATION)));
	}

	@Override
	public ResourceLocation getUid() {
		return MTRPlugin.ESCALATOR_STEP;
	}
}
