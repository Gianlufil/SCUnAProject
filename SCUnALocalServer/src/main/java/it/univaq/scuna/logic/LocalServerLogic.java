package it.univaq.scuna.logic;

import java.io.IOException;
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

import it.univaq.scuna.common.to.CrowdingDataTO;
import it.univaq.scuna.common.to.EntryDataTO;
import it.univaq.scuna.common.types.EntryDataType;
import it.univaq.scuna.database.LocalDatabaseMock;

public class LocalServerLogic {

	private static final String CENTRAL_SERVER_ADDRESS = "http://localhost:8080/SCUnACentralServer/services";
	private static final String ACCESS_LIST_ENDPOINT = "/accessLists/";
	private static final String LOCAL_AREA_CODE = "A0001";

	private static LocalDatabaseMock database;

	private final Client client;

	static {
		database = new LocalDatabaseMock();
	}

	public LocalServerLogic() {
		this.client = Client.create();
	}

	public void saveUserEntryTime(final EntryDataTO entryDataTO) {
		database.addAccess(entryDataTO);
		if(entryDataTO.getType().equals(EntryDataType.RESTRICTED_AREA_IN)) {
			System.out.println("[LOG]\tACCESS DATA MANAGEMENT: Server logged access of user "+entryDataTO.getBadgeNumber()+" into restricted area "+entryDataTO.getAreaCode()+" at "+entryDataTO.getTimestamp());
		} else {
			System.out.println("[LOG]\tACCESS DATA MANAGEMENT: Server logged exit of user "+entryDataTO.getBadgeNumber()+" from restricted area "+entryDataTO.getAreaCode()+" at "+entryDataTO.getTimestamp());
		}
	}

	public List<String> getAccessList(final String restrictedAreaCode) {
		if (database.getAreaAllowedGroups().isEmpty()) {
			WebResource webResource = client.resource(CENTRAL_SERVER_ADDRESS+ACCESS_LIST_ENDPOINT+LOCAL_AREA_CODE);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
	
			String json = response.getEntity(String.class);
			ObjectMapper mapper = new ObjectMapper();
			try {
				Map<String, List<String>> areaAllowedGroups = mapper.readValue(json, new TypeReference<HashMap<String, List<String>>>(){});
				database.setAreaAllowedGroups(areaAllowedGroups);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (ClientHandlerException e) {
				e.printStackTrace();
			} catch (UniformInterfaceException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// DEBUG:
		System.out.println("[LOG]\tACCESS LIST REQUEST: Local server sent to client access list for restricted area "+restrictedAreaCode);
		return database.getAllowedGroups(restrictedAreaCode);
	}
	
	public void saveAccessList(final EntryDataTO entryDataTO) {
		database.addAccess(entryDataTO);
		// DEBUG:
		System.out.println("[LOG]\tACCESS DATA UPDATE: Local server received update for enrty "+entryDataTO.getBadgeNumber()+": "+entryDataTO.getTimestamp()+" timestamp");
	}

	public void saveCrowdingData(final CrowdingDataTO crowdingDataTO) {
		database.addCrowdingData(crowdingDataTO);
		// DEBUG:
		System.out.println("[LOG]\tCROWDING DATA UPDATE: Local server received update for people in area "+crowdingDataTO.getAreaCode()+": "+crowdingDataTO.getPresences()+" people");
	}

	public Map<String, CrowdingDataTO> getCrowdingData() {
		Map<String, CrowdingDataTO> crowdingData = new HashMap<String, CrowdingDataTO>();
		crowdingData.putAll(database.getCrowdingData());
		database.setCrowdingData(new HashMap<String, CrowdingDataTO>());
		System.out.println("[LOG]\tCROWDING DATA BACKUP: Local server sent to central server crowding data and cleaned its cache");
		return crowdingData;
	}

	public Map<String, List<EntryDataTO>> getAccessData() {
		Map<String, List<EntryDataTO>> accessData = new HashMap<String, List<EntryDataTO>>();
		accessData.putAll(database.getRestrictedAreaAccesses());
		database.setRestrictedAreaAccesses(new HashMap<String, List<EntryDataTO>>());
		System.out.println("[LOG]\tACCESS DATA BACKUP: Local server sent to central server access data and cleaned its cache");
		return accessData;
	}
}
