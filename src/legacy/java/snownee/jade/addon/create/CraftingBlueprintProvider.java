package snownee.jade.addon.create;

import java.lang.reflect.Field;

import com.simibubi.create.content.equipment.blueprint.BlueprintOverlayRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.addon.JadeAddons;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IDisplayHelper;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum CraftingBlueprintProvider implements IEntityComponentProvider {
	INSTANCE;

	public static Field RESULT;

	static {
		try {
			RESULT = BlueprintOverlayRenderer.class.getDeclaredField("result");
			RESULT.setAccessible(true);
		} catch (Throwable e) {
			JadeAddons.LOGGER.trace("Error accessing blueprint result field", e);
			RESULT = null;
		}
	}

	@Override
	public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
		ItemStack result = getResult();
		if (!result.isEmpty()) {
			tooltip.add(IDisplayHelper.get().stripColor(result.getHoverName()));
		}
	}

	@Override
	public IElement getIcon(EntityAccessor accessor, IPluginConfig config, IElement currentIcon) {
		ItemStack result = getResult();
		if (!result.isEmpty()) {
			return IElementHelper.get().item(result);
		}
		return null;
	}

	public static ItemStack getResult() {
		if (RESULT != null) {
			try {
				return (ItemStack) RESULT.get(null);
			} catch (Throwable e) {
				JadeAddons.LOGGER.trace("Error getting blueprint result", e);
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ResourceLocation getUid() {
		return CreatePlugin.CRAFTING_BLUEPRINT;
	}

}
