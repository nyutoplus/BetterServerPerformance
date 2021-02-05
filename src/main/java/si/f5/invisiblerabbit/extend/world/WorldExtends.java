package si.f5.invisiblerabbit.extend.world;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import si.f5.invisiblerabbit.util.FunctionHelperAdapter;

public class WorldExtends extends FunctionHelperAdapter {

	WorldPropertieAccess wpa;

	public WorldExtends() {
		wpa = new  WorldPropertieAccess();

	}


	@Override
	public void load() {
	}


	@Override
	public void setConfig(FileConfiguration fileConfig) {
		wpa.setConfig(fileConfig);

	}

	@Override
	public Listener[] getListener() {
		Listener[] tmp =  {wpa};
		return tmp;
	}

	@Override
	public void reload(Server server) {
		wpa.reload(server);
	}
}
