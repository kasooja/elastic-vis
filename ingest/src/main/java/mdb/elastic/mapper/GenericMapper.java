package mdb.elastic.mapper;
import java.lang.reflect.Field;

import org.elasticsearch.Version;
import org.elasticsearch.index.mapper.DateFieldMapper;
import org.elasticsearch.index.mapper.GeoPointFieldMapper;
import org.elasticsearch.index.mapper.RootObjectMapper;
import org.elasticsearch.index.mapper.TextFieldMapper;

import de.bytefish.elasticutils.elasticsearch5.mapping.BaseElasticSearchMapping;

public class GenericMapper extends BaseElasticSearchMapping {

	public static String INDEX_TYPE = "document";

	private Field[] fields;

	public GenericMapper(Field[] fields) {
		super(INDEX_TYPE, Version.V_5_0_0);
		this.fields = fields;		
	}

	@Override
	protected void configureRootObjectBuilder(RootObjectMapper.Builder builder) {
		for(Field field : fields){
			String name = field.getName();
			Class<?> classVal = field.getType();			
			if(classVal.getTypeName().equalsIgnoreCase("java.lang.String")){
				builder.add(new TextFieldMapper.Builder(name).fielddata(true));
			} else if(classVal.getTypeName().equalsIgnoreCase("java.util.Date")) {
				builder.add(new DateFieldMapper.Builder(name));
			} else if(classVal.getTypeName().equalsIgnoreCase("mdb.elastic.model.GeoLocationSer")){		
				builder.add(new GeoPointFieldMapper.Builder(name)
						.enableLatLon(true)
						.enableGeoHash(false));
			}
		}
	}
	
}
