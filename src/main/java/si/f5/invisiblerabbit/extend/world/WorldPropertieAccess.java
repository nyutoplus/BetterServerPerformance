package si.f5.invisiblerabbit.extend.world;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class WorldPropertieAccess implements Listener{

	private boolean unloadSpawnChunk;
	private String[] disableUnloadSpawnChunks;

	private boolean isAmbientSpawnLimit;
	private int ambientSpawnLimit;
	private Map<String, Integer> perWorldAmbientSpawnLimit;

	private boolean isAnimalSpawnLimit;
	private int animalSpawnLimit;
	private Map<String, Integer> perWorldAnimalSpawnLimit;

	private boolean isMonsterSpawnLimit;
	private int monsterSpawnLimit;
	private Map<String, Integer> perWorldMonsterSpawnLimit;

	private boolean isWaterAmbientSpawnLimit;
	private int waterAmbientSpawnLimit;
	private Map<String, Integer> perWorldWaterAmbientSpawnLimit;

	private boolean isWaterAnimalSpawnLimit;
	private int waterAnimalSpawnLimit;
	private Map<String, Integer> perWorldWaterAnimalSpawnLimit;

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

		isAmbientSpawnLimit = config.getBoolean("world.ambient-spawn-limit.enable",false);
		ambientSpawnLimit = config.getInt("world.ambient-spawn-limit.global",400);
		perWorldAmbientSpawnLimit = getSection(config,"world.ambient-spawn-limit.per-world",ambientSpawnLimit);

		isAnimalSpawnLimit = config.getBoolean("world.animal-spawn-limit.enable",false);
		animalSpawnLimit = config.getInt("world.animal-spawn-limit.global",800);
		perWorldAnimalSpawnLimit = getSection(config,"world.animal-spawn-limit.per-world",animalSpawnLimit);

		isMonsterSpawnLimit = config.getBoolean("world.monster-spawn-limit.enable",false);
		monsterSpawnLimit = config.getInt("world.monster-spawn-limit.global",800);
		perWorldMonsterSpawnLimit = getSection(config,"world.monster-spawn-limit.per-world",monsterSpawnLimit);

		isWaterAmbientSpawnLimit = config.getBoolean("world.water-ambient-spawn-limit.enable",false);
		waterAmbientSpawnLimit = config.getInt("world.water-ambient-spawn-limit.global",400);
		perWorldWaterAmbientSpawnLimit = getSection(config,"world.water-ambient-spawn-limit.per-world",waterAmbientSpawnLimit);

		isWaterAnimalSpawnLimit = config.getBoolean("world.water-animal-spawn-limit.enable",false);
		waterAnimalSpawnLimit = config.getInt("world.water-animal-spawn-limit.global",400);
		perWorldWaterAnimalSpawnLimit = getSection(config,"world.water-animal-spawn-limit.per-world",waterAnimalSpawnLimit);

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
	public void initWorld(WorldInitEvent e) {
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

		world.setAmbientSpawnLimit(isAmbientSpawnLimit ? perWorldAmbientSpawnLimit.getOrDefault(name, ambientSpawnLimit) : -1);
		world.setAnimalSpawnLimit(isAnimalSpawnLimit ? perWorldAnimalSpawnLimit.getOrDefault(name, animalSpawnLimit) : -1);
		world.setMonsterSpawnLimit(isMonsterSpawnLimit ? perWorldMonsterSpawnLimit.getOrDefault(name, monsterSpawnLimit) : -1);
		world.setWaterAmbientSpawnLimit(isWaterAmbientSpawnLimit ? perWorldWaterAmbientSpawnLimit.getOrDefault(name, waterAmbientSpawnLimit) : -1);
		world.setWaterAnimalSpawnLimit(isWaterAnimalSpawnLimit ? perWorldWaterAnimalSpawnLimit.getOrDefault(name, waterAnimalSpawnLimit) : -1);

		world.setTicksPerAmbientSpawns(isTicksPerAmbientSpawn ? perWorldTicksPerAmbientSpawn.getOrDefault(name, ticksPerAmbientSpawn) : 1);
		world.setTicksPerAnimalSpawns(isTicksPerAnimalSpawn ? perWorldTicksPerAnimalSpawn.getOrDefault(name, ticksPerAnimalSpawn) : 400);
		world.setTicksPerMonsterSpawns(isTicksPerMonsterSpawn ? perWorldTicksPerMonsterSpawn.getOrDefault(name, ticksPerMonsterSpawn) : 1);
		world.setTicksPerWaterAmbientSpawns(isTicksPerWaterAmbientSpawn ? perWorldTicksPerWaterAmbientSpawn.getOrDefault(name, ticksPerWaterAmbientSpawn) : 1);
		world.setTicksPerWaterSpawns(isTicksPerWaterAnimalSpawn ? perWorldTicksPerWaterAnimalSpawn.getOrDefault(name, ticksPerWaterAnimalSpawn) : 1);
	}

}
