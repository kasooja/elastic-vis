package main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

import de.bytefish.elasticutils.elasticsearch5.client.ElasticSearchClient;
import de.bytefish.elasticutils.elasticsearch5.client.bulk.configuration.BulkProcessorConfiguration;
import de.bytefish.elasticutils.elasticsearch5.client.bulk.options.BulkProcessingOptions;
import de.bytefish.elasticutils.elasticsearch5.mapping.IElasticSearchMapping;

import de.bytefish.elasticutils.elasticsearch5.utils.ElasticSearchUtils;
import mdb.elastic.converter.TableToElasticModelConverter;
import mdb.elastic.mapper.GenericMapper;
import mdb.elastic.model.ElasticDataModel;
import mdb.elastic.model.GeoLocationSer;
import utils.GeoUtils;

public class LCDTablesIndexer {

	//private static String tableName = "lcd_x_primary_jurisdiction";
	//private static String tableName = "lcd";
	private static String tableName = "state_lookup";
	//private static String tableName = "contractor_jurisdiction";

	private static String indexName = "lcddataindex";
	private static String dataFile = "src/main/resources/LCDData/20161024/all_lmrp.mdb";
	private static Database db;
	private static List<String> notNullColumns;
	private static List<String> addressColumns;

	static{
		try {
			db = DatabaseBuilder.open(new File(dataFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		notNullColumns = new ArrayList<String>();
		notNullColumns.add("orig_det_eff_date");
		addressColumns = new ArrayList<String>();
		addressColumns.add("description");
	}

	public static void main(String[] args) throws Exception {	
		System.out.println(tableName);
		
		Field[] fields = Class.forName("mdb.elastic.model." + tableName).getFields();
		GenericMapper.INDEX_TYPE = tableName;
		IElasticSearchMapping mapping = new GenericMapper(fields);
		//(IElasticSearchMapping) Class.forName("mdb.elastic.mapper." + tableName +"Mapper").newInstance();

		BulkProcessorConfiguration bulkConfiguration = new BulkProcessorConfiguration(BulkProcessingOptions.builder()
				.setBulkActions(100)
				.build());

		try {
			Settings settings = Settings.builder()
					.put("cluster.name", "elasticsearch")
					.put("xpack.security.user", "elastic:changeme").build();
			TransportClient transportClient = new PreBuiltXPackTransportClient(settings);
			transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 
					9300));

			createIndex(transportClient, indexName);
			createMapping(transportClient, indexName, mapping);
			System.out.println("Mapping done");

			try {
				ElasticSearchClient<ElasticDataModel> client = new ElasticSearchClient<>(transportClient, indexName, mapping, bulkConfiguration);
				Stream<ElasticDataModel> dataStream = getTableDataFromMdb(); 	
				client.index(dataStream);
				System.out.println("Indexing done");
				client.awaitClose(2, TimeUnit.SECONDS);
				client.close();
				transportClient.close();
			} catch(Exception e){
				e.printStackTrace();
			}

		} catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("Addresses Not Found " + GeoUtils.addressNotFound.size());
		for(String add : GeoUtils.addressNotFound){
			System.out.println(add);
		}
	}

	private static void createIndex(Client client, String indexName) {
		if(!ElasticSearchUtils.indexExist(client, indexName).isExists()) {
			ElasticSearchUtils.createIndex(client, indexName);
		}
	}

	private static void createMapping(Client client, String indexName, IElasticSearchMapping mapping) {
		if(ElasticSearchUtils.indexExist(client, indexName).isExists()) {
			ElasticSearchUtils.putMapping(client, indexName, mapping);
		}
	}

	private static Stream<ElasticDataModel> getTableDataFromMdb(){
		List<ElasticDataModel> tableData = new ArrayList<ElasticDataModel>();		
		Table table;		
		try {
			table = db.getTable(tableName);
			for(Row row : table){
				boolean isNullColumn = false;
				if("lcd".equalsIgnoreCase(tableName)){
					for(String key : notNullColumns){
						Object object = row.get(key);
						if(object==null){
							isNullColumn = true;
						}
					}
				}
				if(isNullColumn){
					continue;
				}
				ElasticDataModel model = (ElasticDataModel) Class.forName("mdb.elastic.model." + tableName).newInstance();
				if("state_lookup".equalsIgnoreCase(tableName)){					
					for(String addressCol : addressColumns){
						String address = (String) row.get(addressCol);
						GeoLocationSer geoPoint = GeoUtils.getGeoPoint(address);
						row.put(addressCol + "_" + "coordinates", geoPoint);
					}
				}
				ElasticDataModel rowData = TableToElasticModelConverter.convert(row, model); 
				tableData.add(rowData);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		GeoUtils.cache.saveCache();
		return tableData.stream();
	}

}

//	private static Stream<lcdElasticCSV> getLCDData() {
//		// Data to read from:
//		Path lcdDataFilePath = FileSystems.getDefault().getPath("src/main/resources/LCDData/20161014/CSVs", "lcd.csv");
//		Stream<lcd> lcdData = getLcdData(lcdDataFilePath);
//		List<lcd> collect = lcdData.collect(Collectors.toList());
//		for(lcd next : collect){
//			System.out.println(next.getadd_icd10_info());
//		}
//		try {		
//			return getLcdData(lcdDataFilePath)
//					.map(x -> {return lcdDataConverter.convert(x);});
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}

//private static Stream<lcd> getLcdData(Path path) {
//	Stream<CsvMappingResult<lcd>> readFromFile = Parsers.lcdParser().readFromFile(path, StandardCharsets.ISO_8859_1);
//	List<CsvMappingResult<lcd>> listt = readFromFile.collect(Collectors.toList());
//	for(CsvMappingResult<lcd> d : listt){
//		System.out.println(d.getError());
//	}
//	return readFromFile
//			.filter(x -> x.isValid())
//			.map(x -> x.getResult());
//}
