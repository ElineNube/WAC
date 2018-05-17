package nl.hu.v1wac.firstapp.webservices;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;


import nl.hu.v1wac.firstapp.model.Country;
import nl.hu.v1wac.firstapp.model.ServiceProvider;
import nl.hu.v1wac.firstapp.model.WorldService;

@Path("/countries")
public class WorldResourse {

	@GET
	@Produces("application/json")
	public String getCountries() {
		WorldService service = ServiceProvider.getWorldService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Country country : service.getAllCountries()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("Code", country.getCode());
			job.add("Name", country.getName());
			job.add("Capital", country.getCapital());
			job.add("Surface", country.getSurface());
			job.add("Government", country.getGovernment());
			job.add("Lat", country.getLatitude());
			job.add("Ing", country.getLongitude());
			job.add("Iso3", country.getIso3());
			job.add("Continent", country.getContinent());
			job.add("Region", country.getRegion());
			job.add("Population", country.getPopulation());
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("{code}")
	@Produces("application/json")
	public String getLandInfo(@PathParam("code")String code) {
		WorldService service = ServiceProvider.getWorldService();
		Country country = service.getCountryByCode(code);
		
		if (country == null) {
			throw new WebApplicationException("No lands found!");
		}
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Code", country.getCode());
		job.add("Name", country.getName());
		job.add("Capital", country.getCapital());
		job.add("Surface", country.getSurface());
		job.add("Government", country.getGovernment());
		job.add("Lat", country.getLatitude());
		job.add("Ing", country.getLongitude());
		job.add("Iso3", country.getIso3());
		job.add("Continent", country.getContinent());
		job.add("Region", country.getRegion());
		job.add("Population", country.getPopulation());
		
		return job.build().toString();
	}
	
	@GET
	@Path("/largestsurfaces")
	@Produces("application/json")
	public String getCountriesLargestSurfaces() {
		WorldService service = ServiceProvider.getWorldService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Country c : service.get10LargestSurfaces()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("Name", c.getName());
			job.add("Surface", c.getSurface());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
		
	}
	@GET
	@Path("/largestpopulation")
	@Produces("application/json")
	public String getCountriesLargestPopulation() {
		WorldService service = ServiceProvider.getWorldService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Country c : service.get10LargestPopulations()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("Name", c.getName());
			job.add("Population", c.getPopulation());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
		
	}


}
