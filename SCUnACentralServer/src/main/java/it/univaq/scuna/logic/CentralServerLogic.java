package it.univaq.scuna.logic;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

import it.univaq.scuna.common.to.ConfirmationUserDataTO;
import it.univaq.scuna.common.to.CrowdingDataTO;
import it.univaq.scuna.common.to.EntryDataTO;
import it.univaq.scuna.common.to.UserDataTO;
import it.univaq.scuna.database.CentralDatabaseMock;

public class CentralServerLogic {

	public static final String CENTRAL_SERVER_ADDRESS = "http://localhost:8080/SCUnACentralServer/services";

	private static final List<String> LOCAL_SERVER_ADDRESSES = Arrays.asList(
			"http://localhost:8081/SCUnALocalServer/services",
			"http://localhost:8082/SCUnALocalServer/services");

	private static final String ACCESSES_ENDPOINT = "/restrictedAreaAccess";
	private static final String CROWDING_DATA_ENDPOINT = "/crowdingData";

	private final Client client;

	private static CentralDatabaseMock database;

	static {
		database = new CentralDatabaseMock();
		database.initDatabase();
	}

	public CentralServerLogic() {
		this.client = new Client();
	}

	public Map<String, List<String>> getAccessLists(final String localAreaCode) {
		System.out.println("[LOG]\tACCESS LIST REQEUST: Central server sent to local server "+localAreaCode+" access lists to restricted area");
		return database.getAreaAllowedLists();
	}

	public UserDataTO getExamAuthenticationData(final ConfirmationUserDataTO examUserDataTO) {
		System.out.println("[LOG]\tEXAM AUTHENTICATION: Central server sent to client authentication data for user "+examUserDataTO.getBadgeNumber());
		UserDataTO user = database.getUser(examUserDataTO.getBadgeNumber());
		if (user!=null) {
			if (database.getExamLists().get(examUserDataTO.getCode()).contains(examUserDataTO.getBadgeNumber())) {
				return user;
			}
		}
		return null;
	}

	public void examConfirmation(final ConfirmationUserDataTO examUserDataTO) {
		System.out.println("[LOG]\tEXAM AUTHENTICATION: Central server received exam presence confirmation for user "+examUserDataTO.getBadgeNumber()+" at exam "+examUserDataTO.getCode());
		database.addExamConfirmation(examUserDataTO);
	}

	public void lessonPresence(final ConfirmationUserDataTO lessonUserDataTO) {
		System.out.println("[LOG]\tEXAM AUTHENTICATION: Central server received lesson presence confirmation for user "+lessonUserDataTO.getBadgeNumber()+" at lesson "+lessonUserDataTO.getCode());
		database.addLessonConfirmation(lessonUserDataTO);
	}

	public void getAccesses() {
		for (String serverAddress: LOCAL_SERVER_ADDRESSES) {
			try {
				WebResource webResource = client.resource(serverAddress+ACCESSES_ENDPOINT);
				ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
				String json = response.getEntity(String.class);
				ObjectMapper mapper = new ObjectMapper();

				Map<String, List<EntryDataTO>> data = mapper.readValue(json, new TypeReference<HashMap<String, List<EntryDataTO>>>(){});
				for (Map.Entry<String, List<EntryDataTO>> entry : data.entrySet()) {
					database.addRestrictedAreaAccess(entry.getKey(), entry.getValue());
				}
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (ClientHandlerException e) {
				e.printStackTrace();
			} catch (UniformInterfaceException e) {
				e.printStackTrace();
			} catch (java.net.ConnectException e) {
				//
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void getCrowdingData() {
		for (String serverAddress: LOCAL_SERVER_ADDRESSES) {
			try {
				WebResource webResource = client.resource(serverAddress+CROWDING_DATA_ENDPOINT);
				ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
				String json = response.getEntity(String.class);
				ObjectMapper mapper = new ObjectMapper();

				Map<String, CrowdingDataTO> data = mapper.readValue(json, new TypeReference<HashMap<String, CrowdingDataTO>>(){});
				for (Map.Entry<String, CrowdingDataTO> entry : data.entrySet()) {
					database.updateCrowdingData(entry.getValue());
				}
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (ClientHandlerException e) {
				e.printStackTrace();
			} catch (UniformInterfaceException e) {
				e.printStackTrace();
			} catch (java.net.ConnectException e) {
				//
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("[LOG]\tCROWDING DATA BACKUP: Central server asked to "+serverAddress+" list of accesses");
		}
	}

	public CentralDatabaseMock getDB() {
		return database;
	}
}
