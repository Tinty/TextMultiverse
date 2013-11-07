import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	public String propertiesInit(String propertiesDirectory, String propertyToFind) throws IOException{
		/*
		 * This try/catch block will try to access the directory parameter and if it can,
		 * it will send the stream to the readProperties(BufferedReader, String) method.
		 * else will return error.
		 */
		try {
			BufferedReader propertiesReader = new BufferedReader(
					new InputStreamReader(getClass().getResourceAsStream(propertiesDirectory)));
			return readProperties(propertiesReader, propertyToFind);
		} catch (NullPointerException propertiesReaderNPE) {
			// TODO Auto-generated catch block
			propertiesReaderNPE.printStackTrace();
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
	public String readProperties(BufferedReader propertiesReader, String propertyToFind) throws IOException{
		//variable initalize start
		boolean readMore = true;
		String propertyName = null;
		String propertyData = null;
		//variable initalize end
		//read in from file loop start
		do{
			String readIn = propertiesReader.readLine();
			//check if end of file is reached start
			try {
				if(readIn.equals(null)){
					break;
				}
			} catch (NullPointerException propertiesReaderNPE) {
				break;
			}
			//check if end of file is reached end
			if(readIn.startsWith("*")){
				//if current read in is to be skipped
				continue;
			}else  if(readIn.contains("\"")){
				StringTokenizer propertyTokens = new StringTokenizer(readIn);
				//Tokenize line for property name start
				propertyName = propertyTokens.nextToken("\"");
				propertyName = propertyName.trim();
				//Tokenize line for property name end
				//check if property name is correct with tofind start
				if(propertyName.equalsIgnoreCase(propertyToFind)){
					propertyData = propertyTokens.nextToken("\"");
					readMore = false;
				}else{
					continue;
				}
				//check if property name is correct with tofind end
			}
		}while(readMore);
		//read in from file loop end
		return propertyData;
	}
}