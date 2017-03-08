package it.univaq.scuna.client;

import java.io.EOFException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import it.univaq.scuna.common.to.ConfirmationUserDataTO;
import it.univaq.scuna.common.to.CrowdingDataTO;
import it.univaq.scuna.common.to.EntryDataTO;
import it.univaq.scuna.common.to.UserDataTO;
import it.univaq.scuna.common.types.EntryDataType;

public class ClientApplicationLogic {

	//private static final String CENTRAL_SERVER_ADDRESS = "http://10.170.12.203:8080/SCUnACentralServer/services";
	private static final String CENTRAL_SERVER_ADDRESS = "http://localhost:8080/SCUnACentralServer/services";
	private static final String EXAM_AUTHENTICATION_ENDPOINT = "/examAutentication";
	private static final String EXAM_CONFIRMATION_ENDPOINT = "/examConfirmation";
	private static final String LESSON_CONFIRMATION_ENDPOINT = "/lessonConfirmation";

	//private static final String LOCAL_SERVER_ADDRESS = "http://10.170.12.202:8081/SCUnALocalServer/services";
	private static final String LOCAL_SERVER_ADDRESS = "http://localhost:8081/SCUnALocalServer/services";
	private static final String ACCESS_LIST_ENDPOINT = "/accessList/";
	private static final String CROWDING_DATA_ENDPOINT = "/crowdingData";
	private static final String POST_ACCESS_ENDPOINT = "/restrictedAreaAccess";

	//private static final String BACKUP_SERVER_ADDRESS = "http://10.170.12.203:8082/SCUnALocalServer/services";
	private static final String BACKUP_SERVER_ADDRESS = "http://localhost:8082/SCUnALocalServer/services";

	private List<String> restrictedAreaAllowedGroups;

	private String areaCode;
	private String cardId;
	private String group;

	private boolean usingBackup;

	private final Client client;

	public ClientApplicationLogic() {
		this.restrictedAreaAllowedGroups = new LinkedList<String>();
		this.client = Client.create();
		this.areaCode = "";
		this.usingBackup = false;
	}

	private void requireAccessList() {
		ClientResponse response = localGet(ACCESS_LIST_ENDPOINT+this.areaCode);
		System.out.println("Application: requireAccessList(), server response "+response.getStatus());

		if (response.getStatus()!=200) return;

		String json = response.getEntity(String.class);

		ObjectMapper mapper = new ObjectMapper();
		try {
			this.restrictedAreaAllowedGroups = mapper.readValue(json, new TypeReference<List<String>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (EOFException e){
			this.restrictedAreaAllowedGroups = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendCrowdingData(final int number) {
		CrowdingDataTO crowdingDataTO = new CrowdingDataTO();
		crowdingDataTO.setAreaCode(this.areaCode);
		crowdingDataTO.setPresences(number);
		crowdingDataTO.setTimestamp(System.currentTimeMillis());

		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonInput = mapper.writeValueAsString(crowdingDataTO);
			ClientResponse response = localPost(CROWDING_DATA_ENDPOINT, jsonInput);
			
			System.out.println("Application: sendCrowdingData(), server response "+response.getStatus());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public UserDataTO askExamAuthentication(final String examID) {
		ConfirmationUserDataTO confirmationDataTO = new ConfirmationUserDataTO();
		confirmationDataTO.setBadgeNumber(cardId);
		confirmationDataTO.setCode(examID);

		UserDataTO userDataTO = null;
		ObjectMapper outMapper = new ObjectMapper();
		ObjectMapper inMapper = new ObjectMapper();
		try {
			String jsonInput = outMapper.writeValueAsString(confirmationDataTO);
			WebResource webResource = client.resource(CENTRAL_SERVER_ADDRESS+EXAM_AUTHENTICATION_ENDPOINT);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonInput);
			System.out.println("Application: askExamAuthentication(), server response "+response.getStatus());
			if (response.getStatus()!=200) return null;
			String json = response.getEntity(String.class);
			if (json.equals("")) return null;
			userDataTO = inMapper.readValue(json, new TypeReference<UserDataTO>(){});
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userDataTO;
	}

	public void examConfirmation(final String examID) {
		ConfirmationUserDataTO confirmationUserDataTO = new ConfirmationUserDataTO();
		confirmationUserDataTO.setBadgeNumber(cardId);
		confirmationUserDataTO.setCode(examID);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonInput = mapper.writeValueAsString(confirmationUserDataTO);
			WebResource webResource = client.resource(CENTRAL_SERVER_ADDRESS+EXAM_CONFIRMATION_ENDPOINT);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonInput);
			System.out.println("Application: examConfirmation(), server response "+response.getStatus());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void lessonConfirmation(final String lessonID) {
		ConfirmationUserDataTO confirmationUserDataTO = new ConfirmationUserDataTO();
		confirmationUserDataTO.setBadgeNumber(cardId);
		confirmationUserDataTO.setCode(lessonID);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonInput = mapper.writeValueAsString(confirmationUserDataTO);
			WebResource webResource = client.resource(CENTRAL_SERVER_ADDRESS+LESSON_CONFIRMATION_ENDPOINT);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonInput);
			System.out.println("Application: lessonConfirmation(), server response "+response.getStatus());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean accessArea() {

		if(!this.restrictedAreaAllowedGroups.contains(this.group)) {
			return false;
		}
		return true;
	}

	public void sendAccessData() {
		EntryDataTO dataTO = new EntryDataTO();
		dataTO.setAreaCode(this.areaCode);
		dataTO.setBadgeNumber(this.cardId);
		dataTO.setTimestamp(System.currentTimeMillis());
		dataTO.setType(EntryDataType.RESTRICTED_AREA_IN);
		postAccessArea(dataTO);
	}

	public void exitArea() {
		EntryDataTO dataTO = new EntryDataTO();
		dataTO.setAreaCode(this.areaCode);
		dataTO.setBadgeNumber(this.cardId);
		dataTO.setTimestamp(System.currentTimeMillis());
		dataTO.setType(EntryDataType.RESTRICTED_AREA_OUT);
		postAccessArea(dataTO);
	}

	public void postAccessArea(final EntryDataTO dataTO) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonInput = mapper.writeValueAsString(dataTO);
			ClientResponse response = localPost(POST_ACCESS_ENDPOINT, jsonInput);
			System.out.println("Application: postAcessArea(), server response "+response.getStatus());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setUserData(final String cardId, final String group) {
		this.cardId = cardId;
		this.group = group;
	}

	public void setAreaCode(final String areaCode) {
		if (!this.areaCode.equals(areaCode)) {
			this.areaCode = areaCode;
			requireAccessList();
		}
	}

	public List<String> getRestrictedAreaAllowedGroups() {
		return restrictedAreaAllowedGroups;
	}

	public void setRestrictedAreaAllowedGroups(final List<String> restrictedAreaAllowedGroups) {
		this.restrictedAreaAllowedGroups = restrictedAreaAllowedGroups;
	}

	private ClientResponse localPost(final String endPoint, final String json) {
		WebResource webResource;
		if (!this.usingBackup) {
			try {
				webResource = client.resource(LOCAL_SERVER_ADDRESS+endPoint);
				return webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
			} catch (Exception e) {
				System.out.println("Error on local server: using backup");
				this.usingBackup = true;
				return localPost(endPoint, json);
			}
		} else {
			webResource = client.resource(BACKUP_SERVER_ADDRESS+endPoint);
			return webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
		}
	}

	private ClientResponse localGet(final String endPoint) {
		WebResource webResource;
		if (!this.usingBackup) {
			try {
				webResource = client.resource(LOCAL_SERVER_ADDRESS+endPoint);
				return webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			} catch (Exception e) {
				System.out.println("Error on local server: using backup");
				this.usingBackup = true;
				return localGet(endPoint);
			}
		} else {
			webResource = client.resource(BACKUP_SERVER_ADDRESS+endPoint);
			return webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		}
	}

	
}
