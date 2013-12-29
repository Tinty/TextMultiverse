import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
/*
 * Properties Class
 * Used to read and parse information from property files used by the engine.
 */
public class Properties {
	/*
	 * propertiesInit(String, String)
	 * Determines if incoming parameter is directrory to existing property file.
	 * If it exists it will send it to be read and parsed, if it doesn't it will return error.
	 * 
	 * returns String, property data
	 */
	public String propertiesInit(String propertiesDirectory, String propertyToFind, int type) throws IOException{
		try {
			BufferedReader propertiesReader = new BufferedReader(
					new InputStreamReader(getClass().getResourceAsStream(propertiesDirectory)));
			if(type == 0){
				return readProperty(propertiesReader, propertyToFind);	
			}else if(type == 1){
				return readMassProperties(propertiesReader, propertyToFind);
			}
		} catch (NullPointerException propertiesReaderNPE) {
			try {
				BufferedReader propertiesReader = new BufferedReader(
						new InputStreamReader(new FileInputStream(propertiesDirectory)));
				if(type == 0){
					return readProperty(propertiesReader, propertyToFind);	
				}else if(type == 1){
					return readMassProperties(propertiesReader, propertyToFind);
				}
			} catch (NullPointerException propertiesReaderNPE2) {
				System.err.print("\nThe directory: " + propertiesDirectory + " failed to load.\n\n");
				propertiesReaderNPE2.printStackTrace();
			}
		}
		return null;
	}
	/*
	 * readProperties(BufferedReader, String)
	 * Will read property files line by line checking for the desired property and will return
	 * the String data it finds related to the property.
	 * 
	 * returns String, property data
	 */
	public String readProperty(BufferedReader propertiesReader, String propertyToFind) throws IOException{
		boolean readMore = true;
		String propertyName = null;
		String propertyData = null;
		do{
			String readIn = propertiesReader.readLine();
			try {
				if(readIn.equals(null)){
					break;
				}
			} catch (NullPointerException propertiesReaderNPE) {
				break;
			}
			if(readIn.startsWith("*")){
				continue;
			}else  if(readIn.contains("\"")){
				StringTokenizer propertyTokens = new StringTokenizer(readIn);
				propertyName = propertyTokens.nextToken("\"");
				propertyName = propertyName.trim();
				if(propertyName.equalsIgnoreCase(propertyToFind)){
					propertyData = propertyTokens.nextToken("\"");
					readMore = false;
				}else{
					continue;
				}
			}
		}while(readMore);
		return propertyData;
	}
	/*
	 * readMassProperties(BufferedReader, String)
	 * This method is different from readProperties(BufferedReader, String) because instead of reading in individual property data
	 * based on a property name, this will read in all property data into a tokenizable string based on the property name. Good for lists
	 * of some category.
	 * 
	 * returns a String with a ; delimiter between each substring of data that was read.
	 */
	public String readMassProperties(BufferedReader massPropertiesReader, String propertiesToFind) throws IOException{
		String propertyName = null,
				    propertyData = null,
					readIn = null;
		do{
			readIn = massPropertiesReader.readLine();
			try {
				if(readIn.equals(null)){
					break;
				}
			} catch (NullPointerException massPropertiesReaderNPE) {
				break;
			}
			if(readIn.startsWith("*")){
				continue;
			}else  if(readIn.contains("\"")){
				StringTokenizer propertyTokens = new StringTokenizer(readIn);
				propertyName = propertyTokens.nextToken("\"");
				propertyName = propertyName.trim();
				if(propertyName.equalsIgnoreCase(propertiesToFind)){
					try {
						if(propertyData.equals(null)){
							propertyData = propertyTokens.nextToken("\"") + ";";
						}else{
							propertyData += propertyTokens.nextToken("\"") + ";";
						}
					} catch (NullPointerException propertyDataNPE) {
						propertyData = propertyTokens.nextToken("\"") + ";";
					}
				}else{
					continue;
				}
			}
		}while(!readIn.equals(null));
		return propertyData;
	}
	public String[][] fileReadIn(String fileDirectory) throws IOException{
		ToolBag tool = new ToolBag();
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
		String[][] data = new String[tool.fileLines(fileDirectory, "*")][2];
		String dataNameToAdd = null,
					dataToAdd = null,
					readIn = null;
		for(int x = 0; x < data.length; x++){
			try {
				readIn = fileReader.readLine();
				if(readIn.equals(null)){
					break;
				}
			} catch (NullPointerException fileReaderNPE) {
				break;
			}
			if(readIn.startsWith("*")){
				continue;
			}else  if(readIn.contains("\"")){
				StringTokenizer readInTokens = new StringTokenizer(readIn);
				dataNameToAdd =readInTokens.nextToken("\"");
				dataNameToAdd = dataNameToAdd.trim();
				dataToAdd = readInTokens.nextToken("\"");
				data[x][0] = dataNameToAdd;
				data[x][1] = dataToAdd;
			}
		}
		return data;
	}
}