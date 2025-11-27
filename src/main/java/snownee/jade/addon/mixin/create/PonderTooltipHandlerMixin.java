package snownee.jade.addon.mixin.create;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.createmod.ponder.foundation.PonderTooltipHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import snownee.jade.overlay.OverlayRenderer;

@Mixin(value = PonderTooltipHandler.class, remap = false)
public class PonderTooltipHandlerMixin {
	@Inject(method = "addToTooltip", at = @At("HEAD"), cancellable = true)
	private static void jadeaddons$addToTooltip(List<Component> tooltip, ItemStack stack, CallbackInfo ci) {
		if (OverlayRenderer.shown) {
			ci.cancel();
		}
	}
}
