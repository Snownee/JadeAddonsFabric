package snownee.jade.addon;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringUtil;
import snownee.jade.api.theme.IThemeHelper;

public class JadeAddons {
	public static final String ID = "jadeaddons";
	public static final String NAME = "Jade Addons";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static MutableComponent seconds(int sec) {
		return IThemeHelper.get().info(StringUtil.formatTickDuration(sec * 20)).withStyle(ChatFormatting.WHITE);
	}
}
