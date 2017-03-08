package it.univaq.scuna.console;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import it.univaq.scuna.common.to.CrowdingDataTO;
import it.univaq.scuna.common.to.EntryDataTO;
import it.univaq.scuna.common.to.UserDataTO;
import it.univaq.scuna.common.types.EntryDataType;
import it.univaq.scuna.database.CentralDatabaseMock;
import it.univaq.scuna.logic.CentralServerLogic;


public class CentralAdministrationConsole {
	
	
	private static final String PAGE_HEADER = "<html><head><style>body{width:100%;text-align:center;margin:0;text-align:center;}</style></head><body><h1>Administration Console</h1>";

	
	private CentralDatabaseMock database;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	
	public CentralAdministrationConsole(final CentralServerLogic serverLogic) {
		this.database = serverLogic.getDB();
	}

	public String showAccesses() {
		
		String page = new String(PAGE_HEADER);			
		
		Map<String, List<EntryDataTO>> dataEntry =  database.getRestrictedAreaAccesses();
		for(Map.Entry<String, List<EntryDataTO>> currentEntry: dataEntry.entrySet()){

			page = page + "<table>"
							+ "<ul>"
								+ "Resrticted Area : "+ currentEntry.getKey()
							+"</ul>";
			
			List<EntryDataTO> a = currentEntry.getValue();
			
			for(EntryDataTO currentID:a){
				
				page = page + "<li>"
								+ "<span> Card ID: "+currentID.getBadgeNumber()+"</span>"
										+ "<span>, Timestamp: "+sdf.format(new Date(currentID.getTimestamp()))+"</span>"
										+ "<span>, Type: "+(currentID.getType().equals(EntryDataType.RESTRICTED_AREA_IN) ? "Access" : "Exit")+"</span>"
							+ "</li>";
				
			}
		}
		
		return page+"</html>";
	}

	public String showCrowdingData() {

		String page = new String(PAGE_HEADER);

		Map<String, CrowdingDataTO> crowdingData =  database.getCrowdingData();
		for(Map.Entry<String, CrowdingDataTO> currentEntry: crowdingData.entrySet()){

			page = page + "<table>"
							+ "<ul>"
								+ "Restricted Area : "+ currentEntry.getKey()
							+"</ul>";
			
			CrowdingDataTO a = currentEntry.getValue();
			
			
			page = page + "<li>"
							+ "<span>Number of presences : "+a.getPresences()+"</span>"
						+ "</li><br>";
			
			page = page + "<li>"
							+ "<span>Timestamp : "+sdf.format(new Date(a.getTimestamp()))+"</span>"
						+ "</li>";	
			

			page = page+"</table>";
			
		}
		
		return page+"</html>";
		
	}
			
		
	
	public String showAreaGroup(){
		
		String page = new String(PAGE_HEADER);
					
		Map<String, List<String>> examList = database.getAreaAllowedLists();
		for(Map.Entry<String, List<String>> currentEntry: examList.entrySet()){
			page = page + "<table>"
							+ "<ul>"
								+ "User list for restricted Area: "+ currentEntry.getKey()
							+"</ul>";
			
			List<String> a = currentEntry.getValue();
			for(String currentID:a){
				page = page + "<li>"
								+ "<span>"+currentID+"</span>"
							+ "</li>";
				
			}
			page = page+"</table>";
		}
		return page+"</html>";
	}
	
	public String showExamList(){
		
		String page = new String(PAGE_HEADER);

		Map<String, List<String>> examList =  database.getExamLists();
		for(Map.Entry<String, List<String>> currentEntry: examList.entrySet()){
			page = page + "<table>"
							+ "<ul>"
								+ "Registred student for exam: "+ currentEntry.getKey()
							+"</ul>";
			
			List<String> a = currentEntry.getValue();
			for(String currentID:a){
				page = page + "<li>"
								+ "<span>"+currentID+"</span>"
							+ "</li>";
				
			}
			page = page+"</table>";
		}
		return page+"</html>";
	}
	
	
	public String showUserList(){
		
		String page = new String(PAGE_HEADER);

		Map<String, UserDataTO> userList = database.getUserList();
		
		for(Map.Entry<String, UserDataTO> currentEntry: userList.entrySet()){
			page = page + "<table>"
							+ "<ul>"
								+ "Card ID: "+ currentEntry.getKey()
							+"</ul>";
			
			UserDataTO a = currentEntry.getValue();
			
				
				page = page + "<li>"
								+ "<span>Name : "+a.getName()+"</span>"
							+ "</li>";
				
				page = page + "<li>"
								+ "<span>Surname : "+a.getSurname()+"</span>"
							+ "</li>";
				
				page = page + "<li>"
								+ "<span>Birthdate : "+a.getBirthdate()+"</span>"
							+ "</li>";	
			
		}
		
		return page+"</table></html>";
	}
	
	public String adminConsole(){
		
				
		String page = new String(PAGE_HEADER);
		
		page = page + "<form action="+CentralServerLogic.CENTRAL_SERVER_ADDRESS+"/userList>";
			page = page + "<button type=”submit” value=”User List”>User List</button>";
		page = page + "</form>";
		
		page = page + "<form action="+CentralServerLogic.CENTRAL_SERVER_ADDRESS+"/areaGroup>";
			page = page + "<button type=”submit” value=”User List”>Group Area</button>";
		page = page + "</form>";

		page = page + "<form action="+CentralServerLogic.CENTRAL_SERVER_ADDRESS+"/examList>";
			page = page + "<button type=”submit” value=”User List”>Exam List</button>";
		page = page + "</form>";
		
		page = page + "<form action="+CentralServerLogic.CENTRAL_SERVER_ADDRESS+"/accesses>";
			page = page + "<button type=”submit” value=”User List”>Access List</button>";
		page = page + "</form>";
	
		page = page + "<form action="+CentralServerLogic.CENTRAL_SERVER_ADDRESS+"/crowdingData>";
			page = page + "<button type=”submit” value=”User List”>Crowding Data List</button>";
		page = page + "</form>";
		
		return page + "</html>";
	}

}
