package utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MapCache<T> {

	public Map<Object, T> map; 
	private String cachePath;

	public MapCache(String cachePath){
		this.cachePath = cachePath;
		if(new File(cachePath).exists()){
			map = SerializationUtils.readObject(new File(cachePath));
		} else {
			map = new HashMap<Object, T>();
		}
	}

	public MapCache(){
		map = SerializationUtils.readObject(new File(cachePath));
	}
	
	public void saveCache(){
		SerializationUtils.saveObject(map, new File(cachePath));
	}

}
