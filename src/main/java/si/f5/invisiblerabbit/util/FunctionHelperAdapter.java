package si.f5.invisiblerabbit.util;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class FunctionHelperAdapter implements FunctionHelper {

	@Override
	public void load() {}

	@Override
	public void unload() {}

	@Override
	public void reload(Server server) {}

	@Override
	public void setConfig(FileConfiguration fileConfig,FileConfiguration perWorldConfig) {}

	@Override
	public Listener[] getListener() {
		return null;
	}

}
