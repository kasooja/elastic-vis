
package mdb.elastic.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoLocationSer implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("lat")
    public double lat;

    @JsonProperty("lon")
    public double lon;

    public GeoLocationSer() {}

    public GeoLocationSer(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
