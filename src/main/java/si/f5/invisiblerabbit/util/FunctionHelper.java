package si.f5.invisiblerabbit.util;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public interface FunctionHelper {

	public void load();

	public void unload();

	public void reload(Server server);

	public void setConfig(FileConfiguration fileConfig,FileConfiguration perWorldConfig);

	public Listener[] getListener();


}
