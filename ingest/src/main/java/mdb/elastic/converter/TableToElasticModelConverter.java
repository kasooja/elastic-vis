package mdb.elastic.converter;

import java.lang.reflect.Field;

import com.healthmarketscience.jackcess.Row;

import mdb.elastic.model.ElasticDataModel;

public class TableToElasticModelConverter {

	public static String getString(Row data, String key){
		Object object = data.get(key);
		String str = null;
		if(object!=null){
			if("String".equalsIgnoreCase(object.getClass().getSimpleName())){
				str = (String) object;
			}
		}
		return str;
	}

	public static ElasticDataModel convert(Row data, ElasticDataModel eDataModel) {		
		//elasticStation.geoLocation = new GeoLocation(csvStation.getLatitude(), csvStation.getLongitude());
		for(Field field : eDataModel.getClass().getFields()){
			String name = field.getName();
			try {
				field.set(eDataModel, data.get(name));				
			} catch (IllegalArgumentException e) {
				Object object = data.get(name);
				String str = object.toString();
				try {
					field.set(eDataModel, str);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
				//e.printStackTrace();
			} catch (IllegalAccessException e) {
				//	e.printStackTrace();
			}			
		}
		return eDataModel;
	}
}