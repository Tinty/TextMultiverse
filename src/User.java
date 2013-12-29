/*
 * User class
 * Class specifically for accessing and calling methods that refer to the user end of the software.
 */
public class User {
	DatabaseManagement database = new DatabaseManagement();
	public static String userLocation = null;
	//return true if user is found, false if not.
	public boolean userInit(){
		//Scan database for c_user start
		for(int x = 0; x < database.universeDirectory.length; x++){
			if(database.universeDirectory[x][2].contains("c_user")){
				userLocation = database.universeDirectory[x][0];
				return true;
			}
		}
		return false;
		//Scan database for c_user end
	}
	public String[][] fetchUserDescription(int x){
		String[][] userInfo = new String[5][];
		ToolBag tool = new ToolBag();
		for(int y = 0; y < Main.localeInventory[x].length; y++){
			try {
				if(!userInfo[0].equals(null));
			} catch (NullPointerException uNNPE) {
				try {
					userInfo[0] = new String[tool.fetchFromInventory("name", Main.localeInventory[x][y]).length];
				} catch (Exception e) {
				}
				userInfo[0] = tool.fetchFromInventory("name", Main.localeInventory[x][y]);
			}
			try {
				if(!userInfo[1].equals(null));
			} catch (NullPointerException uLNPE) {
				try {
					userInfo[1] = new String[tool.fetchFromInventory("location", Main.localeInventory[x][y]).length];
				} catch (Exception e) {
				}
				userInfo[1] = tool.fetchFromInventory("location", Main.localeInventory[x][y]);
			}
			try {
				if(!userInfo[2].equals(null));
			} catch (NullPointerException uNNPE) {
				try {
					userInfo[2] = new String[tool.fetchFromInventory("state", Main.localeInventory[x][y]).length];
				} catch (Exception e) {
				}
				userInfo[2] = tool.fetchFromInventory("state", Main.localeInventory[x][y]);
			}
			try {
				if(!userInfo[3].equals(null));
			} catch (NullPointerException uLDNPE) {
				try {
					userInfo[3] = new String[tool.fetchFromInventory("description_loc", Main.localeInventory[x][y]).length];
				} catch (Exception e) {
				}
				userInfo[3] = tool.fetchFromInventory("description_loc", Main.localeInventory[x][y]);
			}
			try {
				if(!userInfo[4].equals(null));
			} catch (NullPointerException uVDNPE) {
				try {
					userInfo[4] = new String[tool.fetchFromInventory("description_vic", Main.localeInventory[x][y]).length];
				} catch (Exception e) {
				}
				userInfo[4] = tool.fetchFromInventory("description_vic", Main.localeInventory[x][y]);
			}
			try {
				if(!userInfo[0].equals(null)
						&& !userInfo[1].equals(null)
						&& !userInfo[2].equals(null)
						&& !userInfo[3].equals(null)
						&& !userInfo[4].equals(null)){
					break;
				}
			} catch (NullPointerException entityNPE) {
				continue;
			}
		}
		return userInfo;
	}
}