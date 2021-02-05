package si.f5.invisiblerabbit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandManager {

	public boolean onCommand(CommandSender sender,Command cmd, String commandLabel, String[] args,BetterServerPerformance bsp) {
		if(cmd.getName().equalsIgnoreCase("bsp") || cmd.getName().equalsIgnoreCase("BetterServerPerformance")) {
			if(args.length != 0) {
				if(args[0].equalsIgnoreCase("reload")) {
					if(sender.hasPermission("betterserverperformance.admin.reload")) {
						sender.sendMessage("reload");
						bsp.reload();
					}
				}
			}
		}
		return false;
	}
}
