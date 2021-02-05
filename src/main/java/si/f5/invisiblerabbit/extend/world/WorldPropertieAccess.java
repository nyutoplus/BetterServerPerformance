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
	private String[] disableUnloadSpawnChunks;

	private boolean isChunksPerAmbientSpawnLimit;
	private int chunksPerAmbientSpawnLimit;
	private Map<String, Integer> perWorldChunksPerAmbientSpawnLimit;

	private boolean isChunksPerAnimalSpawnLimit;
	private int chunksPerAnimalSpawnLimit;
	private Map<String, Integer> perWorldChunksPerAnimalSpawnLimit;

	private boolean isChunksPerMonsterSpawnLimit;
	private int chunksPerMonsterSpawnLimit;
	private Map<String, Integer> perWorldChunksPerMonsterSpawnLimit;

	private boolean isChunksPerWaterAmbientSpawnLimit;
	private int chunksPerWaterAmbientSpawnLimit;
	private Map<String, Integer> perWorldChunksPerWaterAmbientSpawnLimit;

	private boolean isChunksPerWaterAnimalSpawnLimit;
	private int chunksPerWaterAnimalSpawnLimit;
	private Map<String, Integer> perWorldChunksPerWaterAnimalSpawnLimit;

	private boolean isTicksPerAmbientSpawn;
	private int ticksPerAmbientSpawn;
	private Map<String, Integer> perWorldTicksPerAmbientSpawn;

	private boolean isTicksPerAnimalSpawn;
	private int ticksPerAnimalSpawn;
	private Map<String, Integer> perWorldTicksPerAnimalSpawn;

	private boolean isTicksPerMonsterSpawn;
	private int ticksPerMonsterSpawn;
	private Map<String, Integer> perWorldTicksPerMonsterSpawn;

	private boolean isTicksPerWaterAmbientSpawn;
	private int ticksPerWaterAmbientSpawn;
	private Map<String, Integer> perWorldTicksPerWaterAmbientSpawn;

	private boolean isTicksPerWaterAnimalSpawn;
	private int ticksPerWaterAnimalSpawn;
	private Map<String, Integer> perWorldTicksPerWaterAnimalSpawn;


	public void setConfig(FileConfiguration config) {
		unloadSpawnChunk = config.getBoolean("world.unload-spawn-chunk",false);
		disableUnloadSpawnChunks = config.getStringList("world.disable-unload-spawn-chunk").toArray(new String[0]);

		isChunksPerAmbientSpawnLimit = config.getBoolean("world.chunks-per-ambient-spawn-limit.enable",false);
		chunksPerAmbientSpawnLimit = config.getInt("world.chunks-per-ambient-spawn-limit.global",8);
		perWorldChunksPerAmbientSpawnLimit = getSection(config,"world.chunks-per-ambient-spawn-limit.per-world",chunksPerAmbientSpawnLimit);

		isChunksPerAnimalSpawnLimit = config.getBoolean("world.chunks-per-animal-spawn-limit.enable",false);
		chunksPerAnimalSpawnLimit = config.getInt("world.chunks-per-animal-spawn-limit.global",64);
		perWorldChunksPerAnimalSpawnLimit = getSection(config,"world.chunks-per-animal-spawn-limit.per-world",chunksPerAnimalSpawnLimit);

		isChunksPerMonsterSpawnLimit = config.getBoolean("world.chunks-per-monster-spawn-limit.enable",false);
		chunksPerMonsterSpawnLimit = config.getInt("world.chunks-per-monster-spawn-limit.global",64);
		perWorldChunksPerMonsterSpawnLimit = getSection(config,"world.chunks-per-monster-spawn-limit.per-world",chunksPerMonsterSpawnLimit);

		isChunksPerWaterAmbientSpawnLimit = config.getBoolean("world.chunks-per-water-ambient-spawn-limit.enable",false);
		chunksPerWaterAmbientSpawnLimit = config.getInt("world.chunks-per-water-ambient-spawn-limit.global",8);
		perWorldChunksPerWaterAmbientSpawnLimit = getSection(config,"world.chunks-per-water-ambient-spawn-limit.per-world",chunksPerWaterAmbientSpawnLimit);

		isChunksPerWaterAnimalSpawnLimit = config.getBoolean("world.chunks-per-water-animal-spawn-limit.enable",false);
		chunksPerWaterAnimalSpawnLimit = config.getInt("world.chunks-per-water-animal-spawn-limit.global",8);
		perWorldChunksPerWaterAnimalSpawnLimit = getSection(config,"world.chunks-per-water-animal-spawn-limit.per-world",chunksPerWaterAnimalSpawnLimit);

		isTicksPerAmbientSpawn = config.getBoolean("world.ticks-per-ambient-spawn.enable",false);
		ticksPerAmbientSpawn = config.getInt("world.ticks-per-ambient-spawn.global",1);
		perWorldTicksPerAmbientSpawn = getSection(config,"world.ticks-per-ambient-spawn.per-world",ticksPerAmbientSpawn);

		isTicksPerAnimalSpawn = config.getBoolean("world.ticks-per-animal-spawn.enable",false);
		ticksPerAnimalSpawn = config.getInt("world.ticks-per-animal-spawn.global",400);
		perWorldTicksPerAnimalSpawn = getSection(config,"world.ticks-per-animal-spawn.per-world",ticksPerAnimalSpawn);

		isTicksPerMonsterSpawn = config.getBoolean("world.ticks-per-monster-spawn.enable",false);
		ticksPerMonsterSpawn = config.getInt("world.ticks-per-monster-spawn.global",1);
		perWorldTicksPerMonsterSpawn = getSection(config,"world.ticks-per-monster-spawn.per-world",ticksPerMonsterSpawn);

		isTicksPerWaterAmbientSpawn = config.getBoolean("world.ticks-per-water-ambient-spawn.enable",false);
		ticksPerWaterAmbientSpawn = config.getInt("world.ticks-per-water-ambient-spawn.global",1);
		perWorldTicksPerWaterAmbientSpawn = getSection(config,"world.ticks-per-water-ambient-spawn.per-world",ticksPerWaterAmbientSpawn);

		isTicksPerWaterAnimalSpawn = config.getBoolean("world.ticks-per-water-animal-spawn.enable",false);
		ticksPerWaterAnimalSpawn = config.getInt("world.ticks-per-water-animal-spawn.global",1);
		perWorldTicksPerWaterAnimalSpawn = getSection(config,"world.ticks-per-water-animal-spawn.per-world",ticksPerWaterAnimalSpawn);

	}

	private Map<String,Integer> getSection(FileConfiguration config,String key,int defaultValue){
		Map<String,Integer> tmp = new HashMap<String,Integer>();
		for(String i:config.getConfigurationSection(key).getKeys(false)) {
			tmp.put(i,config.getInt(key + "." + i,defaultValue));
		}
		return tmp;
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
		boolean tmp = true;
		for(String i:disableUnloadSpawnChunks)tmp = name.equals(i);
		world.setKeepSpawnInMemory(unloadSpawnChunk ? tmp : false);

		world.setAmbientSpawnLimit(isChunksPerAmbientSpawnLimit ? perWorldChunksPerAmbientSpawnLimit.getOrDefault(name, chunksPerAmbientSpawnLimit) : -1);
		world.setAnimalSpawnLimit(isChunksPerAnimalSpawnLimit ? perWorldChunksPerAnimalSpawnLimit.getOrDefault(name, chunksPerAnimalSpawnLimit) : -1);
		world.setMonsterSpawnLimit(isChunksPerMonsterSpawnLimit ? perWorldChunksPerMonsterSpawnLimit.getOrDefault(name, chunksPerMonsterSpawnLimit) : -1);
		world.setWaterAmbientSpawnLimit(isChunksPerWaterAmbientSpawnLimit ? perWorldChunksPerWaterAmbientSpawnLimit.getOrDefault(name, chunksPerWaterAmbientSpawnLimit) : -1);
		world.setWaterAnimalSpawnLimit(isChunksPerWaterAnimalSpawnLimit ? perWorldChunksPerWaterAnimalSpawnLimit.getOrDefault(name, chunksPerWaterAnimalSpawnLimit) : -1);

		world.setTicksPerAmbientSpawns(isTicksPerAmbientSpawn ? perWorldTicksPerAmbientSpawn.getOrDefault(name, ticksPerAmbientSpawn) : 1);
		world.setTicksPerAnimalSpawns(isTicksPerAnimalSpawn ? perWorldTicksPerAnimalSpawn.getOrDefault(name, ticksPerAnimalSpawn) : 400);
		world.setTicksPerMonsterSpawns(isTicksPerMonsterSpawn ? perWorldTicksPerMonsterSpawn.getOrDefault(name, ticksPerMonsterSpawn) : 1);
		world.setTicksPerWaterAmbientSpawns(isTicksPerWaterAmbientSpawn ? perWorldTicksPerWaterAmbientSpawn.getOrDefault(name, ticksPerWaterAmbientSpawn) : 1);
		world.setTicksPerWaterSpawns(isTicksPerWaterAnimalSpawn ? perWorldTicksPerWaterAnimalSpawn.getOrDefault(name, ticksPerWaterAnimalSpawn) : 1);
	}

}
