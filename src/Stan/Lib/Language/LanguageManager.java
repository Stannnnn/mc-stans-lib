package Stan.Lib.Language;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import Stan.Lib.IManager;
import Stan.Lib.Main;
import Stan.Lib.Conversion.ChatUtils;
import Stan.Lib.ReplaceWrappers.RW;
import Stan.Lib.Utils.Data;

public class LanguageManager implements IManager {

	private static String LANGUAGE_FOLDER = "Language\\";

	private Data defaultData;
	private Data playerData;

	private Map<String, Data> customDatas;

	public LanguageManager() {
		defaultData = new Data(Main.getInstance(), LANGUAGE_FOLDER + "defaults.yml");
		playerData = new Data(Main.getInstance(), LANGUAGE_FOLDER + "playerdata.yml");
		customDatas = new HashMap<>();
	}

	@Override
	public void onDisable() {
		defaultData = null;
		playerData = null;
		customDatas = null;
	}

	public void sendLanguage(Player receiver, String message, Object... replaceSources) {
		if (playerData != null) {
			if (playerData.getData().contains(receiver.getUniqueId().toString())) {
				String preferedLanguage = playerData.getData().getString(receiver.getUniqueId().toString());

				if (customDatas != null) {
					if (!customDatas.containsKey(preferedLanguage)) {
						String customLanguage = LANGUAGE_FOLDER + preferedLanguage + ".yml";

						if (Data.exists(Main.getInstance(), customLanguage)) {
							Data customData = new Data(Main.getInstance(), customLanguage);
							customDatas.put(preferedLanguage, customData);
						}
					}

					if (customDatas.containsKey(preferedLanguage)) {
						Data customData = customDatas.get(preferedLanguage);

						if (customData.getData().contains(message)) {
							sendLanguage(receiver, customData.getData().get(message), replaceSources);
							return;
						} else {
							Main.getInstance().printFailure("Couldn't find: '" + message + "' in '" + preferedLanguage + ".yml'.");
						}

					} else {
						playerData.getData().set(receiver.getUniqueId().toString(), null);
						playerData.saveData();
						sendLanguage(receiver, "CustomLanguageNotFound", new RW("{Language}", preferedLanguage));
					}
				}
			}
		}

		if (defaultData != null) {
			if (defaultData.getData().contains(message)) {
				sendLanguage(receiver, defaultData.getData().get(message), replaceSources);
			} else {
				Main.getInstance().printFailure("Couldn't find: '" + message + "' in 'defaults.yml'.");
			}
		}
	}

	private void sendLanguage(Player receiver, Object objMessage, Object... replaceSources) {
		if (Iterable.class.isAssignableFrom(objMessage.getClass())) {
			Iterable<?> it = (Iterable<?>) objMessage;
			for (Object s : it) {
				if (s != null) {
					receiver.sendMessage(ChatUtils.B(s.toString(), replaceSources));
				}
			}
		} else {
			receiver.sendMessage(ChatUtils.B((String) objMessage, replaceSources));
		}
	}

}
