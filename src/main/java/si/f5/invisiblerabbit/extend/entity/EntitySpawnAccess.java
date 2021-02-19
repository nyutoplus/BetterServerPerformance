package si.f5.invisiblerabbit.extend.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class EntitySpawnAccess implements Listener {

	private boolean isServerLimits;
	private boolean isWorldLimits;
	private boolean isChunkLimits;

	private int serverSpawnLimit;
	private int worldSpawnLimit;
	private int chunksPerSpawnLimit;
	private Map<String,Integer> perWorldWorldSpawnLimit;
	private Map<String,Integer> perWorldChunksPerSpawnLimit;

	private int serverPerEntitySpawnLimit;
	private int worldPerEntitySpawnLimit;
	private int chunksPerEntitySpawnLimit;
	private Map<String,Integer> serverPerEntitySpawnLimits;
	private Map<String,Integer> worldPerEntitySpawnLimits;
	private Map<String,Integer> chunksPerEntitySpawnLimits;
	private Map<String,Boolean> serverPerEntityOverLimits;
	private Map<String,Boolean> worldPerEntityOverLimits;
	private Map<String,Boolean> chunksPerEntityOverLimits;
	private Map<String,Integer> perWorldWorldPerEntitySpawnLimit;
	private Map<String,Integer> perWorldChunksPerEntitySpawnLimit;
	private Map<String,Map<String,Integer>> perWorldWorldPerEntitySpawnLimits;
	private Map<String,Map<String,Integer>> perWorldChunksPerEntitySpawnLimits;
	private Map<String,Map<String,Boolean>> perWorldWorldPerEntityOverLimits;
	private Map<String,Map<String,Boolean>> perWorldChunksPerEntityOverLimits;

	private int serverBreedingLimit;
	private int worldBreedingLimit;
	private int chunksPerBreedingLimit;
	private Map<String,Integer> perWorldWorldBreedingLimit;
	private Map<String,Integer> perWorldChunksPerBreedingLimit;

	private int serverPerEntityBreedingLimit;
	private int worldPerEntityBreedingLimit;
	private int chunksPerEntityBreedingLimit;
	private Map<String,Integer> serverPerEntityBreedingLimits;
	private Map<String,Integer> worldPerEntityBreedingLimits;
	private Map<String,Integer> chunksPerEntityBreedingLimits;
	private Map<String,Integer> perWorldWorldPerEntityBreedingLimit;
	private Map<String,Integer> perWorldChunksPerEntityBreedingLimit;
	private Map<String,Map<String,Integer>> perWorldWorldPerEntityBreedingLimits;
	private Map<String,Map<String,Integer>> perWorldChunksPerEntityBreedingLimits;

	private int serverEntitySpawns;
	private Map<String,Integer> serverPerEntitySpawns;

	private Map<String,Integer> worldEntitySpawns;
	private Map<String,Map<String,Integer>> worldPerEntitySpawns;

	private Map<String,Map<Long,Integer>> chunksEntitySpawns;
	private Map<String,Map<Long,Map<String,Integer>>> chunksPerEntitySpawns;

	public EntitySpawnAccess() {
		perWorldWorldSpawnLimit = new HashMap<String,Integer>();
		perWorldChunksPerSpawnLimit = new HashMap<String,Integer>();

		serverPerEntitySpawnLimits = new HashMap<String,Integer>();
		worldPerEntitySpawnLimits = new HashMap<String,Integer>();
		chunksPerEntitySpawnLimits = new HashMap<String,Integer>();

		perWorldWorldPerEntitySpawnLimit = new HashMap<String,Integer>();
		perWorldChunksPerEntitySpawnLimit = new HashMap<String,Integer>();
		perWorldWorldPerEntitySpawnLimits = new HashMap<String,Map<String,Integer>>();
		perWorldChunksPerEntitySpawnLimits = new HashMap<String,Map<String,Integer>>();

		perWorldWorldBreedingLimit = new HashMap<String,Integer>();
		perWorldChunksPerBreedingLimit = new HashMap<String,Integer>();

		serverPerEntityBreedingLimits = new HashMap<String,Integer>();
		worldPerEntityBreedingLimits = new HashMap<String,Integer>();
		chunksPerEntityBreedingLimits = new HashMap<String,Integer>();

		perWorldWorldPerEntityBreedingLimit = new HashMap<String,Integer>();
		perWorldChunksPerEntityBreedingLimit = new HashMap<String,Integer>();
		perWorldWorldPerEntityBreedingLimits = new HashMap<String,Map<String,Integer>>();
		perWorldChunksPerEntityBreedingLimits = new HashMap<String,Map<String,Integer>>();

		serverPerEntityOverLimits = new HashMap<String, Boolean>();
		worldPerEntityOverLimits = new HashMap<String, Boolean>();
		chunksPerEntityOverLimits = new HashMap<String,Boolean>();

		perWorldWorldPerEntityOverLimits = new HashMap<String,Map<String,Boolean>>();
		perWorldChunksPerEntityOverLimits = new HashMap<String, Map<String,Boolean>>();

		serverEntitySpawns = 0;
		serverPerEntitySpawns = new HashMap<String, Integer>();

		worldEntitySpawns = new HashMap<String, Integer>();
		worldPerEntitySpawns = new HashMap<String,Map<String,Integer>>();

		chunksEntitySpawns = new HashMap<String, Map<Long,Integer>>();
		chunksPerEntitySpawns = new HashMap<String, Map<Long,Map<String,Integer>>>();
	}




	public void setConfig(FileConfiguration config,FileConfiguration perWorldConfig) {
		isServerLimits = config.getBoolean("entity.enable",false);
		isWorldLimits = config.getBoolean("entity.per-world.enable",false);
		isChunkLimits = config.getBoolean("entity.per-chunks.enalbe",false);

		serverSpawnLimit = config.getInt("entity.spawn-limit",-1);
		worldSpawnLimit = config.getInt("entity.per-world.spawn-limit",-1);
		chunksPerSpawnLimit = config.getInt("entity.per-chunks.spawn-limit",-1);

		serverPerEntitySpawnLimit = config.getInt("entity.per-entity-spwan-limit",-1);
		worldPerEntitySpawnLimit = config.getInt("entity.per-world.per-entity-spawn-limit",-1);
		chunksPerEntitySpawnLimit = config.getInt("entity.per-chunks.per-entity-spawn-limit",-1);

		serverBreedingLimit = config.getInt("entity.breeding-limit",-1);
		worldBreedingLimit = config.getInt("entity.per-world.breeding-limit",-1);
		chunksPerBreedingLimit = config.getInt("entity.per-chunks.breeding-limit",-1);

		serverPerEntityBreedingLimit = config.getInt("entity.per-entity-breeding-limit",-1);
		worldPerEntityBreedingLimit = config.getInt("entity.per-world.per-entity-breeding-limit",-1);
		chunksPerEntityBreedingLimit = config.getInt("entity.per-chunks.per-entity-breeding-limit",-1);

		perWorldWorldSpawnLimit = getPerWorldMaps(perWorldConfig,"entity.spawn-limit",-1);
		perWorldChunksPerSpawnLimit = getPerWorldMaps(perWorldConfig,"entity.per-chunks.spawn-limit",-1);

		serverPerEntitySpawnLimits = getMaps(config,"entity.entities","per-server-spawn-limit",-1);
		worldPerEntitySpawnLimits = getMaps(config,"entity.entities","per-world-spawn-limit",-1);
		chunksPerEntitySpawnLimits = getMaps(config,"entity.entities","per-chunk-spwan-limit",-1);

		perWorldWorldPerEntitySpawnLimit = getPerWorldMaps(perWorldConfig,"entity.per-entity-spawn-limit",-1);
		perWorldChunksPerEntitySpawnLimit = getPerWorldMaps(perWorldConfig,"entity.per-chunks.per-entity-spawn-limit",-1);

		perWorldWorldPerEntitySpawnLimits = getPerWorldPerEntityMaps(perWorldConfig,"entity.entities","per-world-spawn-limit",-1);
		perWorldChunksPerEntitySpawnLimits = getPerWorldPerEntityMaps(perWorldConfig,"entity.entities","per-chunk-spawn-limit",-1);

		serverPerEntityOverLimits = getMaps(config,"entity.entities","per-server-over-limit",false);
		worldPerEntityOverLimits = getMaps(config,"entity.entities","per-world-over-limit",false);
		chunksPerEntityOverLimits = getMaps(config,"entity.entities","per-chunk-over-limit",false);

		Map<String,Boolean> tmp = getMaps(config,"entity.entities","over-limit",false);
		for(String i:tmp.keySet()) {
			if(tmp.get(i)) {
				serverPerEntityOverLimits.put(i,true);
				worldPerEntityOverLimits.put(i,true);
				chunksPerEntityOverLimits.put(i,true);
			}
		}

		perWorldWorldPerEntityOverLimits = getPerWorldPerEntityMaps(perWorldConfig,"entity.entities","per-world-over-limit",false);
		perWorldChunksPerEntityOverLimits = getPerWorldPerEntityMaps(perWorldConfig,"entity.entities","per-chunk-over-limit",false);

		perWorldWorldBreedingLimit = getPerWorldMaps(perWorldConfig,"entity.per-entity-breeding-limit",-1);
		perWorldChunksPerBreedingLimit = getPerWorldMaps(perWorldConfig,"entity.per-chunks.per-entity-breeding-limit",-1);

		serverPerEntityBreedingLimits = getMaps(config,"entity.entities","per-server-breeding-limit",-1);
		worldPerEntityBreedingLimits = getMaps(config,"entity.entities","per-world-breeding-limit",-1);
		chunksPerEntityBreedingLimits = getMaps(config,"entity.entities","per-chunk-breeding-limit",-1);

		perWorldWorldPerEntityBreedingLimit = getPerWorldMaps(perWorldConfig,"entity.per-entity-breeding-limit",-1);
		perWorldChunksPerEntityBreedingLimit = getPerWorldMaps(perWorldConfig,"entity.per-chunks.per-entity-breeding-limit",-1);

		perWorldWorldPerEntityBreedingLimits = getPerWorldPerEntityMaps(perWorldConfig,"entity.entities","per-world-breeding-limit",-1);
		perWorldChunksPerEntityBreedingLimits = getPerWorldPerEntityMaps(perWorldConfig,"entity.entities","per-chunk-breeding-limit",-1);
	}

	public Map<String,Integer> getMaps(FileConfiguration config,String path,String subPath,int def){
		Map<String,Integer> tmp = new HashMap<String, Integer>();
		for(String i:config.getConfigurationSection(path).getKeys(false)) {
			String fullPath = path + "." + i + "." + subPath;
			if(config.contains(fullPath) && config.isInt(fullPath)) {
				String namePath = path + "." + i + ".has-name";
				if(config.contains(namePath) && config.isBoolean(namePath) && config.getBoolean(namePath)) {
					tmp.put(i + "_HAS_NAME", config.getInt(fullPath, def));
				}else {
					tmp.put(i, config.getInt(fullPath, def));
					tmp.put(i + "_HAS_NAME", config.getInt(fullPath, def));
				}
			}
		}
		return tmp;
	}

	public Map<String,Boolean> getMaps(FileConfiguration config,String path,String subPath,boolean def){
		Map<String,Boolean> tmp = new HashMap<String,Boolean>();
		for(String i:config.getConfigurationSection(path).getKeys(false)) {
			String fullPath = path + "." + i + "." + subPath;
			if(config.contains(fullPath) && config.isInt(fullPath)) {
				String namePath = path + "." + i + ".has-name";
				if(config.contains(namePath) && config.isBoolean(namePath) && config.getBoolean(namePath)) {
					tmp.put(i + "_HAS_NAME", config.getBoolean(fullPath, def));
				}else {
					tmp.put(i, config.getBoolean(fullPath, def));
					tmp.put(i + "_HAS_NAME", config.getBoolean(fullPath, def));
				}
			}
		}
		return tmp;
	}

	public Map<String,Integer> getPerWorldMaps(FileConfiguration config,String path,int def){
		Map<String,Integer> tmp = new HashMap<String,Integer>();
		for(String i:config.getConfigurationSection("").getKeys(false)) {
			String fullPath = i + "." + path;
			if((!i.equals("configversion")) && config.contains(fullPath) && config.isInt(fullPath)) {
				String namePath = path + "." + i + ".has-name";
				if(config.contains(namePath) && config.isBoolean(namePath) && config.getBoolean(namePath)) {
					tmp.put(i + "_HAS_NAME", config.getInt(fullPath,def));
				}else {
					tmp.put(i, config.getInt(fullPath,def));
					tmp.put(i + "_HAS_NAME", config.getInt(fullPath,def));
				}

			}
		}
		return tmp;
	}

	public Map<String,Boolean> getPerWorldMaps(FileConfiguration config,String path,boolean def){
		Map<String,Boolean> tmp = new HashMap<String,Boolean>();
		for(String i:config.getConfigurationSection("").getKeys(false)) {
			String fullPath = i + "." + path;
			if((!i.equals("configversion")) && config.contains(fullPath) && config.isInt(fullPath)) {
				String namePath = path + "." + i + ".has-name";
				if(config.contains(namePath) && config.isBoolean(namePath) && config.getBoolean(namePath)) {
					tmp.put(i + "_HAS_NAME", config.getBoolean(fullPath,def));
				}else {
					tmp.put(i, config.getBoolean(fullPath,def));
					tmp.put(i + "_HAS_NAME", config.getBoolean(fullPath,def));
				}

			}
		}
		return tmp;
	}

	public Map<String,Map<String,Integer>> getPerWorldPerEntityMaps(FileConfiguration config,String path,String subPath,int def){
		Map<String,Map<String,Integer>> tmp = new HashMap<String,Map<String,Integer>>();
		for(String i:config.getConfigurationSection("").getKeys(false)) {
			if(i.equals("configversion"))continue;
			Map<String,Integer> secTmp = new HashMap<String, Integer>();
			for(String j:config.getConfigurationSection(i + "." + path).getKeys(false)) {
				String fullPath = i + "." + path + "." + j + "." + subPath;
				if(config.contains(fullPath) && config.isInt(fullPath)) {
					String namePath = i + "." + path + "." + j + ".has-name";
					if(config.contains(namePath) && config.isBoolean(namePath) && config.getBoolean(namePath)) {
						secTmp.put(j + "_HAS_NAME",config.getInt(fullPath,def));
					}else {
						secTmp.put(j,config.getInt(fullPath,def));
						secTmp.put(j + "_HAS_NAME",config.getInt(fullPath,def));
					}

				}
			}
			tmp.put(i,secTmp);
		}
		return tmp;
	}

	public Map<String,Map<String,Boolean>> getPerWorldPerEntityMaps(FileConfiguration config,String path,String subPath,boolean def){
		Map<String,Map<String,Boolean>> tmp = new HashMap<String,Map<String,Boolean>>();
		for(String i:config.getConfigurationSection("").getKeys(false)) {
			if(i.equals("configversion"))continue;
			Map<String,Boolean> secTmp = new HashMap<String, Boolean>();
			for(String j:config.getConfigurationSection(i + "." + path).getKeys(false)) {
				String fullPath = i + "." + path + "." + j + "." + subPath;
				if(config.contains(fullPath) && config.isInt(fullPath)) {
					String namePath = i + "." + path + "." + j + ".has-name";
					if(config.contains(namePath) && config.isBoolean(namePath) && config.getBoolean(namePath)) {
						secTmp.put(j + "_HAS_NAME",config.getBoolean(fullPath,def));
					}else {
						secTmp.put(j,config.getBoolean(fullPath,def));
						secTmp.put(j + "_HAS_NAME",config.getBoolean(fullPath,def));
					}

				}
			}
			tmp.put(i,secTmp);
		}
		return tmp;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void worldGenerate(WorldInitEvent e) {
		String worldName = e.getWorld().getName();
		worldEntitySpawns.put(worldName, 0);
		worldPerEntitySpawns.put(worldName,new HashMap<String,Integer>());
		chunksEntitySpawns.put(worldName,new HashMap<Long,Integer>());
		chunksPerEntitySpawns.put(worldName,new HashMap<Long,Map<String,Integer>>());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void worldDelete(WorldUnloadEvent e) {
		String worldName = e.getWorld().getName();
		worldEntitySpawns.remove(worldName);
		worldPerEntitySpawns.remove(worldName);
		chunksEntitySpawns.remove(worldName);
		chunksPerEntitySpawns.remove(worldName);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void chunkLoad(ChunkLoadEvent e) {
		Long chunkID = getChunkLongs(e.getChunk());
		String worldName = e.getWorld().getName();
		Entity[] entity = e.getChunk().getEntities();
		serverEntitySpawns += entity.length;
		worldEntitySpawns.put(worldName, worldEntitySpawns.get(worldName) + entity.length);
		chunksEntitySpawns.get(worldName).put(chunkID,entity.length);
		Map<String,Integer> worldMap = worldPerEntitySpawns.get(worldName);
		Map<String,Integer> chunksMap = chunksPerEntitySpawns.get(worldName).get(chunkID);
		for(Entity i:entity) {
			String eName = i.getType().toString() + i.getCustomName() == null ? "" : "_HAS_NAME";

		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void ChunkUnload(ChunkUnloadEvent e) {

	}


	@EventHandler(priority = EventPriority.LOWEST)
	public void entityBreed(EntityBreedEvent e) {
		if(isBreedingLimited(e.getEntity()))e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void entityBreedCheck(EntityBreedEvent e) {

	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void entitySpawn(EntitySpawnEvent e) {
		if(isSpawnLimited(e.getEntity())) e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void entitySpawnCheck(EntitySpawnEvent e) {
	}


	@EventHandler(priority = EventPriority.MONITOR)
	public void entityDeath(EntityDeathEvent e) {

	}

	private boolean isSpawnLimited(Entity e) {
		String entityName = e.getName() != null ? e.getType().toString() + "_HAS_NAME" : e.getType().toString();
		String worldName = e.getWorld().getName();
		int server = 0;
		int serverPerEntity = 0;
		for(World i:e.getServer().getWorlds()) {
			List<Entity> tmp = i.getEntities();
			int amount = tmp.size();
			if(worldName == i.getName() && worldSpawnLimit >= 0 && amount >= worldSpawnLimit)return true;
			server += amount;
			int worldPerEntity = 0;
			for(Entity j:tmp) {
				String name = j.getName() != null ? j.getType().toString() + "_HAS_NAME" : j.getType().toString();
				worldPerEntity += entityName == name ? 1 : 0;
			}


		}
		return false;
	}

	private boolean isBreedingLimited(Entity e) {
		return false;
	}

	private long getChunkLongs(Chunk chunk) {
		return ((long)chunk.getX()) << 32 | chunk.getZ();
	}
}
