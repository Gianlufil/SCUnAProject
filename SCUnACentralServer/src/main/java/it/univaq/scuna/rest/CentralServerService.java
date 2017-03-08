package it.univaq.scuna.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.univaq.scuna.common.to.ConfirmationUserDataTO;
import it.univaq.scuna.logic.CentralServerLogic;
import it.univaq.scuna.console.CentralAdministrationConsole;

//http://localhost:8080/SCUnACentralServer/*
@Path("/services")
public class CentralServerService {

	private final CentralServerLogic logic;
	private final CentralAdministrationConsole admin;

	{
		logic = new CentralServerLogic();
		admin = new CentralAdministrationConsole(logic);
	}

	@GET
	@Path("/accessLists/{localAreaCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRestrictedAreaAccessList(@PathParam("localAreaCode") final String localAreaCode) {
		Map<String, List<String>> accessLists = logic.getAccessLists(localAreaCode);
		return Response.ok(accessLists).build();
	}

	@POST
	@Path("/examAutentication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response askExamAuthentication(final ConfirmationUserDataTO examUserDataTO) {
		return Response.ok(logic.getExamAuthenticationData(examUserDataTO)).build();
	}

	@POST
	@Path("/examConfirmation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response examCoonfirmation(final ConfirmationUserDataTO examUserDataTO) {
		logic.examConfirmation(examUserDataTO);
		return Response.ok().build();
	}

	@POST
	@Path("/lessonConfirmation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLessonConfirmationData(final ConfirmationUserDataTO lessonDataTO) {
		logic.lessonPresence(lessonDataTO);
		return Response.ok().build();
	}
	
	@GET 
	@Path("/adminConsole")
	public Response adminConsole() {
		return Response.ok(admin.adminConsole()).build();
	}
	
	@GET 
	@Path("/userList")
	public Response getUserList() {
		return Response.ok(admin.showUserList()).build();
	}
	
	@GET 
	@Path("/areaGroup")
	public Response getAreaGruop() {
		return Response.ok(admin.showAreaGroup()).build();
	}
	
	@GET 
	@Path("/examList")
	public Response getExamList() {
		return Response.ok(admin.showExamList()).build();
	}

	@GET
	@Path("/accesses")
	public Response getAccesses() {
		logic.getAccesses();
		return Response.ok(admin.showAccesses()).build();
	}

	@GET
	@Path("/crowdingData")
	public Response getCrowdingData() {
		logic.getCrowdingData();
		return Response.ok(admin.showCrowdingData()).build();
	}
}
