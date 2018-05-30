package nl.hu.v1wac.firstapp.webservices;

import javax.ws.rs.DELETE;
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
	
}
