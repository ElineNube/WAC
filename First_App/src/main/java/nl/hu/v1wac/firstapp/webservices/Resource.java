package nl.hu.v1wac.firstapp.webservices;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;

import nl.hu.v1wac.firstapp.model.ServiceProvider;
import nl.hu.v1wac.firstapp.model.WorldService;
import nl.hu.v1wac.firstapp.persistence.Country;

@Path("/countries/{code}")
public class Resource {
	
	private WorldService service = ServiceProvider.getWorldService();
	
	@DELETE
	@Produces("application/json")
	public Response deleteCountry(@PathParam("code") String code) {
		Country country = service.getCountryByCode(code);
		if (!service.delete(country)) {
			return Response.status(404).build();
		}
		return Response.ok().build();
	}
	
	@PUT
	@Produces("application/json")
	public Response updateCountry(@PathParam("code") String code,
			@FormParam("name") String name,
			@FormParam("capital") String cap,
			@FormParam("region") String reg,
			@FormParam("surface") double sur,
			@FormParam("population") int pop) {
		
		Country country = service.getCountryByCode(code);
		
		if (country == null) {
			Map<String, String> messages = new HashMap<String, String>();
			messages.put("error", "Country does not exist!");
			return Response.status(409).entity(messages).build();
		}
		
		country.setName(name);
		country.setCapital(cap);
		country.setRegion(reg);
		country.setSurface(sur);
		country.setPopulation(pop);
		
		service.update(country);
		
		return Response.ok(country).build();
	}
		
	}

	
