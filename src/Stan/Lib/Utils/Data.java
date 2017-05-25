//Version 2.6 (21-8-2016)
package Stan.Lib.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;

public class Data {
	private final File f;
	private final String fileName;
	private YamlConfiguration data;

	public Data(JavaPlugin javaPlugin, String fileName) {
		if (!(this.f = new File(javaPlugin.getDataFolder().toString() + File.separatorChar + (this.fileName = fileName))).exists()) {
			try {
				Files.createParentDirs(this.f);
				this.f.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		this.reloadData();
	}

	public void saveData() {
		try {
			this.data.save(this.f);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void reloadData() {
		this.data = YamlConfiguration.loadConfiguration(this.f);
	}

	public YamlConfiguration getData() {
		return this.data;
	}

	public String getFileName() {
		return this.fileName;
	}
	
	public boolean delete(){
		return this.f.delete();
	}

	public static boolean exists(JavaPlugin javaPlugin, String fileName) {
		return new File(javaPlugin.getDataFolder().toString() + File.separatorChar + fileName).exists();
	}
}
