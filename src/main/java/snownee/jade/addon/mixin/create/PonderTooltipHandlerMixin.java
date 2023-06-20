package snownee.jade.addon.mixin.create;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.foundation.ponder.PonderTooltipHandler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import snownee.jade.overlay.OverlayRenderer;

@Mixin(PonderTooltipHandler.class)
public class PonderTooltipHandlerMixin {
	@Inject(method = "addToTooltip", at = @At("HEAD"), cancellable = true)
	private static void jadeaddons$addToTooltip(ItemStack stack, List<Component> tooltip, CallbackInfo ci) {
		if (OverlayRenderer.shown) {
			ci.cancel();
		}
	}
}
