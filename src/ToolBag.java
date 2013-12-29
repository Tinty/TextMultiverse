import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
/*
 * ToolBag Class
 * This class contains supporting methods providing and fulfilling various functionality requirements for the engine.
 */
public class ToolBag {
	public int fileLines(String fileDirectory, String exclusion) throws IOException{
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(
					new InputStreamReader(getClass().getResourceAsStream(fileDirectory)));
		} catch (NullPointerException propertiesReaderNPE) {
			try {
				fileReader = new BufferedReader(
						new InputStreamReader(new FileInputStream(fileDirectory)));
			} catch (NullPointerException propertiesReaderNPE2) {
				System.err.print("\nThe directory: " + fileDirectory + " failed to load.\n\n");
				propertiesReaderNPE2.printStackTrace();
			}
		}
		String readIn = null;
		int lineCount = 0;
		do{
			try {
				readIn = fileReader.readLine();
				if(readIn.startsWith(exclusion)
						&& !exclusion.equals("")){
					continue;
				}
				lineCount++;
			} catch (NullPointerException fileReaderCounterNPE) {
				break;
			}
			
		}while(!readIn.equals(null));
		return lineCount;
	}
	public String[] fetchFromInventory(String dataName, String[][] inventory){
		String data[] = null;
		for(int x = 0; x < inventory.length; x++){
			if(inventory[x][0].equalsIgnoreCase(dataName)){
				data = new String[1];
				data[0] = inventory[x][1];
				break;
			}else if(inventory[x][0].contains(";")){
				StringTokenizer dataTokens = new StringTokenizer(inventory[x][0],";");
				data = new String[dataTokens.countTokens()];
				int counter = 0;
				while(dataTokens.hasMoreTokens()){
					data[counter] = dataTokens.nextToken();
					counter++;
				}
				break;
			}
		}
		return data;
	}
	public int localeInventoryDataCount(String dataName){
		int dataCount = 0;
		ArrayList<String> dataList = new ArrayList<String>();
		for(int x = 0; x < Main.localeInventory.length; x++){
			for(int y = 0; y < Main.localeInventory[x].length; y++){
				for(int z = 0; z < Main.localeInventory[x][y].length; z++){
					if(Main.localeInventory[x][y][z][0].equalsIgnoreCase(dataName)
							&& !dataList.contains(Main.localeInventory[x][y][z][1].toLowerCase())){
						dataList.add(Main.localeInventory[x][y][z][1].toLowerCase());
						dataCount++;
						break;
					}
				}
			}
		}
		return dataCount;
	}
	public ArrayList<String> localeInventoryDataFetch(String dataName){
		ArrayList<String> dataList = new ArrayList<String>();
		for(int x = 0; x < Main.localeInventory.length; x++){
			for(int y = 0; y < Main.localeInventory[x].length; y++){
				for(int z = 0; z < Main.localeInventory[x][y].length; z++){
					if(Main.localeInventory[x][y][z][0].equalsIgnoreCase(dataName)
							&& !dataList.contains(Main.localeInventory[x][y][z][1].toLowerCase())){
						dataList.add(Main.localeInventory[x][y][z][1].toLowerCase());
					}
				}
			}
		}
		return dataList;
	}
	public boolean checkLocaleArrayForType(int x, String typeToFind){
		boolean typeIsFound = false;
		for(int y = 0; y < Main.localeInventory[x].length; y++){
			if(typeIsFound){
				break;
			}
			String[] tempTypeStor = fetchFromInventory("type", Main.localeInventory[x][y]);
			try {
				for(String tempTest : tempTypeStor){
					if(tempTest.equalsIgnoreCase(typeToFind)){
						typeIsFound = true;
						break;
					}
				}
			} catch (NullPointerException findUserTypeNPE) {
				continue;
			}
		}
		return typeIsFound;
	}
	public String bracketedInterpolation(String temp, int x){
		boolean totalDone = false;
		do{
			if(temp.contains("[")
					&& temp.contains("]")){
				StringTokenizer bracketTokens = new StringTokenizer(temp);
				int tokenCounter = 0;
				do{
					String bracketed;
					try {
						if(tokenCounter == 0
								&& !temp.startsWith("[")){
							bracketTokens.nextToken("["); //scrap pre bracket
						}
						bracketed = bracketTokens.nextToken("]");
						tokenCounter++;
						if(bracketed.startsWith("[")){
							bracketed = bracketed.replaceFirst("\\[", "");
						}
						if(bracketed.equals("line")){
							temp = temp.replaceAll("\\[line\\]", "\n");
						}
					} catch (NoSuchElementException bISTNSEE) {
						break;
					}
					for(int w = 0; w < Main.localeInventory[x].length; w++){
						boolean done = false;
						for(int w2 = 0; w2 < Main.localeInventory[x][w].length; w2++){
							if(Main.localeInventory[x][w][w2][0].equalsIgnoreCase(bracketed)){
								temp = temp.replaceAll("\\[" + bracketed + "\\]", Main.localeInventory[x][w][w2][1]);
								done = true;
								break;
							}
							if(done){
								break;
							}
						}
						if(done){
							break;
						}
					}
				}while(bracketTokens.hasMoreTokens());
			}else{
				totalDone = true;
			}
		}while(!totalDone);
		return temp;
	}
}