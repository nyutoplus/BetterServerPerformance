package si.f5.invisiblerabbit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import si.f5.invisiblerabbit.extend.world.WorldExtends;
import si.f5.invisiblerabbit.util.FunctionHelper;

public class BetterServerPerformance extends JavaPlugin{

	CommandManager cm;

	FunctionHelper[] fh;



	@Override
	public void onLoad() {
		cm = new CommandManager();
		FunctionHelper[] fhtmp = {new WorldExtends()};
		fh = fhtmp;
	}


	@Override
	public void onEnable() {
		saveDefaultConfig();
		FileConfiguration config = getConfig();
		for(FunctionHelper fhl:fh) {
			fhl.load();
			fhl.setConfig(config);

			Listener[] lis = fhl.getListener();
			if(lis != null && lis.length != 0) {
				for(Listener l:lis) {
					getServer().getPluginManager().registerEvents(l, this);
				}
			}
		}
	}


	@Override
	public void onDisable() {
		for(FunctionHelper i:fh) {
			i.unload();
		}
	}

	@Override
	public void reloadConfig() {
		super.reloadConfig();
		for(FunctionHelper i:fh) {
			i.setConfig(getConfig());
		}
	}

	public void reload() {
		reloadConfig();
		for(FunctionHelper fhl:fh) {
			fhl.reload(getServer());
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,String[] args) {
		return cm.onCommand(sender, cmd, commandLabel, args,this);
	}
}
