
public class Entities {
	public String[][] fetchEntityDescription(int x){
		String[][] entityInfo = new String[6][];
		ToolBag tool = new ToolBag();
		for(int y = 0; y < Main.localeInventory[x].length; y++){
			try {
				if(entityInfo[0].equals(null));
			} catch (NullPointerException eNNPE) {
				try {
					entityInfo[0] = new String[tool.fetchFromInventory("name", Main.localeInventory[x][y]).length];
				} catch (Exception e) {
				}
				entityInfo[0] = tool.fetchFromInventory("name", Main.localeInventory[x][y]);
			}
			try {
				if(entityInfo[1].equals(null));
			} catch (NullPointerException eTNPE) {
				try {
					entityInfo[1] = new String[tool.fetchFromInventory("type", Main.localeInventory[x][y]).length];
				} catch (Exception e) {
				}
				entityInfo[1] = tool.fetchFromInventory("type", Main.localeInventory[x][y]);
			}
			try {
				if(entityInfo[2].equals(null));
			} catch (NullPointerException eLNPE) {
				try {
					entityInfo[2] = new String[tool.fetchFromInventory("location", Main.localeInventory[x][y]).length];
				} catch (Exception e) {
				}
				entityInfo[2] = tool.fetchFromInventory("location", Main.localeInventory[x][y]);
			}
			try {
				if(entityInfo[3].equals(null));
			} catch (NullPointerException eLNPE) {
				try {
					entityInfo[3] = new String[tool.fetchFromInventory("state", Main.localeInventory[x][y]).length];
				} catch (Exception e) {
				}
				entityInfo[3] = tool.fetchFromInventory("state", Main.localeInventory[x][y]);
			}
			try {
				if(entityInfo[4].equals(null));
			} catch (NullPointerException eLDNPE) {
				try {
					entityInfo[4] = new String[tool.fetchFromInventory("description_loc", Main.localeInventory[x][y]).length];
				} catch (Exception e) {
				}
				entityInfo[4] = tool.fetchFromInventory("description_loc", Main.localeInventory[x][y]);
			}
			try {
				if(!entityInfo[5].equals(null));
			} catch (NullPointerException eVDNPE) {
				try {
					entityInfo[5] = new String[tool.fetchFromInventory("description_vic", Main.localeInventory[x][y]).length];
				} catch (Exception e) {
				}
				entityInfo[5] = tool.fetchFromInventory("description_vic", Main.localeInventory[x][y]);
			}
			try {
				if(!entityInfo[0].equals(null)
						&& !entityInfo[1].equals(null)
						&& !entityInfo[2].equals(null)
						&& !entityInfo[3].equals(null)
						&& !entityInfo[4].equals(null)
						&& !entityInfo[5].equals(null)){
					break;
				}
			} catch (NullPointerException entityNPE) {
				continue;
			}
		}
		return entityInfo;
	}
}
