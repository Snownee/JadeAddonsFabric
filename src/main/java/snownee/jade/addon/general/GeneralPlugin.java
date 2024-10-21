package snownee.jade.addon.general;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.HitResult;
import snownee.jade.addon.JadeAddons;
import snownee.jade.api.Accessor;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IWailaConfig;

@WailaPlugin(GeneralPlugin.ID)
public class GeneralPlugin implements IWailaPlugin {
	public static final String ID = JadeAddons.ID;
	public static final ResourceLocation EQUIPMENT_REQUIREMENT = ResourceLocation.fromNamespaceAndPath(ID, "equipment_requirement");
	public static BiPredicate<Player, TagKey<Item>> EQUIPMENT_CHECK_PREDICATE = (player, tag) -> player.getMainHandItem().is(tag)
			|| player.getOffhandItem().is(tag)
			|| player.getItemBySlot(EquipmentSlot.HEAD).is(tag);

	public TagKey<Item> requirementTag;

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.addConfig(EQUIPMENT_REQUIREMENT, "", $ -> ResourceLocation.read($).isSuccess());
		registration.addConfigListener(EQUIPMENT_REQUIREMENT, id -> refreshTag(id, $ -> requirementTag = $));
		registration.addRayTraceCallback(
				10000,
				(HitResult hitResult, @Nullable Accessor<?> accessor, @Nullable Accessor<?> originalAccessor) -> {
					if (accessor != null) {
						Player player = accessor.getPlayer();
						if (requirementTag != null && !EQUIPMENT_CHECK_PREDICATE.test(player, requirementTag)) {
							return null;
						}
					}
					return accessor;
				});

		if (FabricLoader.getInstance().isModLoaded("trinkets")) {
			EQUIPMENT_CHECK_PREDICATE = EQUIPMENT_CHECK_PREDICATE.or((player, tag) -> TrinketsApi.getTrinketComponent(player)
					.flatMap($ -> Optional.ofNullable($.getInventory()))
					.map($ -> $.getOrDefault("head", Map.of()).values().stream())
					.map($ -> $.anyMatch($$ -> $$.hasAnyMatching(item -> item.is(requirementTag))))
					.orElse(false));
		}

		TargetModifierLoader loader = new TargetModifierLoader();
		CommonLifecycleEvents.TAGS_LOADED.register((registryAccess, client) -> loader.reload());
		registration.addRayTraceCallback(loader);
		registration.addTooltipCollectedCallback(loader);

		registration.markAsClientFeature(EQUIPMENT_REQUIREMENT);
	}

	private void refreshTag(ResourceLocation id, Consumer<TagKey<Item>> setter) {
		String s = IWailaConfig.get().getPlugin().getString(id);
		if (s.isBlank()) {
			setter.accept(null);
		} else {
			setter.accept(TagKey.create(Registries.ITEM, ResourceLocation.parse(s)));
		}
	}

}
