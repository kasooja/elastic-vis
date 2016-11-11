package mdb.elastic.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class state_lookup implements ElasticDataModel {

	@JsonProperty("state_id")
	public String state_id;

	@JsonProperty("state_abbrev")
	public String state_abbrev;

	@JsonProperty("description")
	public String description;
	
	@JsonProperty("description_coordinates")
	public GeoLocationSer description_coordinates;

}