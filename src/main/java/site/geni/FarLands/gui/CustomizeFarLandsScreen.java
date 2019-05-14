package site.geni.FarLands.gui;

import io.github.cottonmc.cotton.config.ConfigManager;
import me.shedaniel.cloth.api.ConfigScreenBuilder;
import me.shedaniel.cloth.gui.ClothConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.resource.language.I18n;
import site.geni.FarLands.util.Categories;

public class CustomizeFarLandsScreen {
	/**
	 * Creates the mod's {@link ClothConfigScreen}
	 *
	 * @param parent The parent {@link Screen}
	 * @return The mod's {@link ClothConfigScreen}
	 * @author geni
	 */
	public static ClothConfigScreen createConfigScreen(Screen parent) {
		final ConfigScreenBuilder builder = ConfigScreenBuilder.create(parent, I18n.translate("config.farlands.title"), null);

		Categories.General.createCategory(builder.addCategory("config.farlands.category.general"));
		Categories.Fixes.createCategory(builder.addCategory("config.farlands.category.fixes"));
		Categories.World.createCategory(builder.addCategory("config.farlands.category.world"));

		builder.setOnSave(ConfigManager::saveConfig);

		return builder.build();
	}

	/**
	 * Creates and opens the mod's {@link ClothConfigScreen}
	 *
	 * @param parent The parent {@link Screen}
	 * @author geni
	 */
	public static void createAndOpenConfigScreen(Screen parent) {
		MinecraftClient.getInstance().openScreen(createConfigScreen(parent));
	}
}
