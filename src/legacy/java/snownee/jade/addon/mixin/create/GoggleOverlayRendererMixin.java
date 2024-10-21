package snownee.jade.addon.mixin.create;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.Window;
import com.simibubi.create.content.equipment.goggles.GoggleOverlayRenderer;

import net.minecraft.client.gui.GuiGraphics;
import snownee.jade.addon.create.CreatePlugin;
import snownee.jade.api.config.IWailaConfig;

@Mixin(value = GoggleOverlayRenderer.class, remap = false)
public class GoggleOverlayRendererMixin {
	@Inject(at = @At("HEAD"), method = "renderOverlay", cancellable = true)
	private static void jadeaddons$renderOverlay(GuiGraphics guiGraphics, float partialTicks, Window window, CallbackInfo ci) {
		if (IWailaConfig.get().getPlugin().get(CreatePlugin.GOGGLES)) {
			ci.cancel();
		}
	}
}
