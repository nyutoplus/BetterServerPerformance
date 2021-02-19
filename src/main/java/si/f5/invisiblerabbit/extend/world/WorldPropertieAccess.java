package si.f5.invisiblerabbit.extend.world;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldPropertieAccess implements Listener{

	private boolean unloadSpawnChunk;
	private Map<String, Boolean> disableUnloadSpawnChunks;

	private boolean isTicksPerAmbientSpawn;
	private int ticksPerAmbientSpawn;
	private Map<String, Boolean> isPerWorldTicksPerAmbientSpawn;
	private Map<String, Integer> perWorldTicksPerAmbientSpawn;

	private boolean isTicksPerAnimalSpawn;
	private int ticksPerAnimalSpawn;
	private Map<String, Boolean> isPerWorldTicksPerAnimalSpawn;
	private Map<String, Integer> perWorldTicksPerAnimalSpawn;

	private boolean isTicksPerMonsterSpawn;
	private int ticksPerMonsterSpawn;
	private Map<String, Boolean> isPerWorldTicksPerMonsterSpawn;
	private Map<String, Integer> perWorldTicksPerMonsterSpawn;

	private boolean isTicksPerWaterAmbientSpawn;
	private int ticksPerWaterAmbientSpawn;
	private Map<String, Boolean> isPerWorldTicksPerWaterAmbientSpawn;
	private Map<String, Integer> perWorldTicksPerWaterAmbientSpawn;

	private boolean isTicksPerWaterAnimalSpawn;
	private int ticksPerWaterAnimalSpawn;
	private Map<String, Boolean> isPerWorldTicksPerWaterAnimalSpawn;
	private Map<String, Integer> perWorldTicksPerWaterAnimalSpawn;

	public WorldPropertieAccess() {
		disableUnloadSpawnChunks = new HashMap<String, Boolean>();

		isPerWorldTicksPerAmbientSpawn = new HashMap<String, Boolean>();
		perWorldTicksPerAmbientSpawn = new HashMap<String, Integer>();

		isPerWorldTicksPerAnimalSpawn = new HashMap<String, Boolean>();
		perWorldTicksPerAnimalSpawn = new HashMap<String, Integer>();

		isPerWorldTicksPerMonsterSpawn = new HashMap<String, Boolean>();
		perWorldTicksPerMonsterSpawn = new HashMap<String, Integer>();

		isPerWorldTicksPerWaterAmbientSpawn = new HashMap<String, Boolean>();
		perWorldTicksPerWaterAmbientSpawn = new HashMap<String, Integer>();

		isPerWorldTicksPerWaterAnimalSpawn = new HashMap<String, Boolean>();
		perWorldTicksPerWaterAnimalSpawn = new HashMap<String, Integer>();

	}


	public void setConfig(FileConfiguration config,FileConfiguration perWorldConfig) {
		unloadSpawnChunk = config.getBoolean("world.unload-spawn-chunk",false);

		isTicksPerAmbientSpawn = config.getBoolean("world.ticks-per-ambient-spawn.enable",false);
		ticksPerAmbientSpawn = config.getInt("world.ticks-per-ambient-spawn.spawns",1);

		isTicksPerAnimalSpawn = config.getBoolean("world.ticks-per-animal-spawn.enable",false);
		ticksPerAnimalSpawn = config.getInt("world.ticks-per-animal-spawn.spawns",400);

		isTicksPerMonsterSpawn = config.getBoolean("world.ticks-per-monster-spawn.enable",false);
		ticksPerMonsterSpawn = config.getInt("world.ticks-per-monster-spawn.spawns",1);

		isTicksPerWaterAmbientSpawn = config.getBoolean("world.ticks-per-water-ambient-spawn.enable",false);
		ticksPerWaterAmbientSpawn = config.getInt("world.ticks-per-water-ambient-spawn.spawns",1);

		isTicksPerWaterAnimalSpawn = config.getBoolean("world.ticks-per-water-animal-spawn.enable",false);
		ticksPerWaterAnimalSpawn = config.getInt("world.ticks-per-water-animal-spawn.spawns",1);

		for(String i:perWorldConfig.getConfigurationSection("").getKeys(false)) {
			if(i.equalsIgnoreCase("configversion"))continue;
			disableUnloadSpawnChunks.put(i, perWorldConfig.getBoolean(i + ".unload-spawn-chunk", false));

			isPerWorldTicksPerAmbientSpawn.put(i, perWorldConfig.getBoolean(i + ".ticks-per-ambient-spawn.enable",false));
			perWorldTicksPerAmbientSpawn.put(i, perWorldConfig.getInt(i + ".ticks-per-ambient-spawn.spawns",1));

			isPerWorldTicksPerAnimalSpawn.put(i, perWorldConfig.getBoolean(i + ".ticks-per-animal-spawn.enable",false));
			perWorldTicksPerAnimalSpawn.put(i, perWorldConfig.getInt(i + ".ticks-per-animal-spawn.spawns",400));

			isPerWorldTicksPerMonsterSpawn.put(i, perWorldConfig.getBoolean(i + ".ticks-per-monster-spawn.enable",false));
			perWorldTicksPerMonsterSpawn.put(i, perWorldConfig.getInt(i + ".ticks-per-monster-spawn.spawns",1));

			isPerWorldTicksPerWaterAmbientSpawn.put(i, perWorldConfig.getBoolean(i + ".ticks-per-water-ambient-spawn.enable",false));
			perWorldTicksPerWaterAmbientSpawn.put(i, perWorldConfig.getInt(i + ".ticks-per-water-ambient-spawn.spawns",1));

			isPerWorldTicksPerWaterAnimalSpawn.put(i, perWorldConfig.getBoolean(i + ".ticks-per-water-animal-spawn.enable",false));
			perWorldTicksPerWaterAnimalSpawn.put(i, perWorldConfig.getInt(i + ".ticks-per-water-animal-spawn.spawns",1));
		}
	}


	public void reload(Server server) {
		World[] worlds = server.getWorlds().toArray(new World[0]);
		if(worlds != null && worlds.length != 0) {
			for(World i:worlds) {
				setWorldPropertie(i);
			}
		}
	}

	@EventHandler
	public void loadWorld(WorldLoadEvent e) {
		System.out.println("==================BSP==================");
		System.out.println("World is initialised");
		World world = e.getWorld();
		String name = world.getName();
		System.out.println(name);
		setWorldPropertie(world);

		System.out.println("=======================================");

	}

	public void setWorldPropertie(World world) {
		String name = world.getName();
		world.setKeepSpawnInMemory(disableUnloadSpawnChunks.getOrDefault(name, unloadSpawnChunk));

		world.setTicksPerAmbientSpawns(isPerWorldTicksPerAmbientSpawn.getOrDefault(name, isTicksPerAmbientSpawn) ? perWorldTicksPerAmbientSpawn.getOrDefault(name, ticksPerAmbientSpawn) : 1);
		world.setTicksPerAnimalSpawns(isPerWorldTicksPerAnimalSpawn.getOrDefault(name, isTicksPerAnimalSpawn) ? perWorldTicksPerAnimalSpawn.getOrDefault(name, ticksPerAnimalSpawn) : 400);
		world.setTicksPerMonsterSpawns(isPerWorldTicksPerMonsterSpawn.getOrDefault(name, isTicksPerMonsterSpawn) ? perWorldTicksPerMonsterSpawn.getOrDefault(name, ticksPerMonsterSpawn) : 1);
		world.setTicksPerWaterAmbientSpawns(isPerWorldTicksPerWaterAmbientSpawn.getOrDefault(name, isTicksPerWaterAmbientSpawn) ? perWorldTicksPerWaterAmbientSpawn.getOrDefault(name, ticksPerWaterAmbientSpawn) : 1);
		world.setTicksPerWaterSpawns(isPerWorldTicksPerWaterAnimalSpawn.getOrDefault(name, isTicksPerWaterAnimalSpawn) ? perWorldTicksPerWaterAnimalSpawn.getOrDefault(name, ticksPerWaterAnimalSpawn) : 1);
	}

}
