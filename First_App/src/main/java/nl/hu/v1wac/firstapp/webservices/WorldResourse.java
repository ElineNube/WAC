package nl.hu.v1wac.firstapp.webservices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import nl.hu.v1wac.firstapp.persistence.Country;
import nl.hu.v1wac.firstapp.model.ServiceProvider;
import nl.hu.v1wac.firstapp.model.WorldService;

@Path("/countries")
public class WorldResourse {
	private WorldService service = ServiceProvider.getWorldService();
	
	@GET
	@Produces("application/json")
	public String getCountries() {
		JsonArray countryArray = buildJsonCountryArray(service.getAllCountries());
		
		return countryArray.toString();
	}
	
	private JsonArray buildJsonCountryArray(List<Country> countries) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		
		for (Country country : countries) {
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
			
			jsonArrayBuilder.add(job);
		}
		
		return jsonArrayBuilder.build();
	}
	
	@POST
	@RolesAllowed("user")
	@Produces("application/json")
	public Response createCountry(@Context SecurityContext sc, @FormParam("code") String cd,
	@FormParam("name") String nm,
	@FormParam("continent") String cont,
	@FormParam("region") String reg,
	@FormParam("surfacearea") double sur,
	@FormParam("population") int pop,
	@FormParam("capital") String cap,
	@FormParam("governmentform") String gov
	) {
	Country newCountry = new Country(cd, nm, cap, cont, reg, sur, pop, gov);
	
	boolean role = sc.isUserInRole("user");
	
	if (role) {
		service.save(newCountry);
		return Response.ok(newCountry).build();
	}
	Map<String, String> messages = new HashMap<String, String>();
    messages.put("error", "Account mag dit niet uitvoeren!");
    return Response.status(409).entity(messages).build();	
	}
	
	@GET
	@Path("/largestsurfaces")
	@Produces("application/json")
	public String getCountriesLargestSurfaces() {
		JsonArray countryArray = buildJsonCountryArray(service.get10LargestSurfaces());
		return countryArray.toString();
	}
	

	@GET
	@Path("{code}")
	@Produces("application/json")
	public String getLandInfo(@PathParam("code")String code) {
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
	@Path("/largestpopulation")
	@Produces("application/json")
	public String getCountriesLargestPopulation() {
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
	
	@DELETE
	@RolesAllowed("user")
	@Path("{code}")
	@Produces("application/json")
	public Response deleteCountry(@Context SecurityContext sc, @PathParam("code") String code) {
		Country country = service.getCountryByCode(code);
		boolean role = sc.isUserInRole("user");
		
		if(role) {
			if (service.delete(country)) {
				return Response.ok().build();
			}
		}
		Map<String, String> messages = new HashMap<String, String>();
        messages.put("error", "Account mag dit niet uitvoeren!");
        return Response.status(409).entity(messages).build();	
	}
	
	@PUT
	@RolesAllowed("user")
	@Path("{code}")
	@Produces("application/json")
	public Response updateCountry(@Context SecurityContext sc, @PathParam("code") String code,
			@FormParam("name") String name,
			@FormParam("capital") String cap,
			@FormParam("region") String reg,
			@FormParam("surface") double sur,
			@FormParam("population") int pop) {
		
		Country country = service.getCountryByCode(code);
		boolean role = sc.isUserInRole("user");
		
		if (role) {
			country.setName(name);
			country.setCapital(cap);
			country.setRegion(reg);
			country.setSurface(sur);
			country.setPopulation(pop);
			
			service.update(country);
			
			return Response.ok(country).build();
		}
		Map<String, String> messages = new HashMap<String, String>();
        messages.put("error", "Account mag dit niet uitvoeren!");
        return Response.status(409).entity(messages).build();	
		
		
	}
	


}
