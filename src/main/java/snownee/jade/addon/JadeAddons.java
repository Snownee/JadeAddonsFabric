package snownee.jade.addon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.StringUtil;

public class JadeAddons {
	public static final String ID = "jadeaddons";
	public static final String NAME = "Jade Addons";
	public static final Logger LOGGER = LogManager.getLogger(NAME);

	public static MutableComponent seconds(int sec) {
		return new TextComponent(StringUtil.formatTickDuration(sec * 20)).withStyle(ChatFormatting.WHITE);
	}
}
