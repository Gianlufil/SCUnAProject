package it.univaq.scuna.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.univaq.scuna.common.to.CrowdingDataTO;
import it.univaq.scuna.common.to.EntryDataTO;
import it.univaq.scuna.logic.LocalServerLogic;



@Path("/services")
public class LocalServerService {

	private final LocalServerLogic logic;

	{  
		logic = new LocalServerLogic();
	}

	@POST
	@Path("/restrictedAreaAccess")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveUserEntryTime(final EntryDataTO entryDataTO) {
		logic.saveUserEntryTime(entryDataTO);
		return Response.ok().build();
	}

	@GET
	@Path("/accessList/{restrictedAreaCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccessList(@PathParam("restrictedAreaCode") final String restrictedAreaCode) {
		return Response.ok(logic.getAccessList(restrictedAreaCode)).build();
	}

	@POST
	@Path("/crowdingData")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendCrowdingData(final CrowdingDataTO crowdingDataTO) {
		logic.saveCrowdingData(crowdingDataTO);
		return Response.ok().build();
	}

	@GET
	@Path("/crowdingData")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCrowdingData() {
		return Response.ok(logic.getCrowdingData()).build();
	}

	@GET
	@Path("/restrictedAreaAccess")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccessData() {
		return Response.ok(logic.getAccessData()).build();
	}

}