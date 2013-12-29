import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
/*
 * Input Class
 * To collect and sort input from the user.
 * Then sends the information to execute what the command implies to do.
 */
public class Input {
	DatabaseManagement database = new DatabaseManagement();
	Properties properties = new Properties();
	Translator translator = new Translator();
	User user = new User();
	public static ArrayList<String> commandsUsed = new ArrayList<String>(),
														   charactersUsed = new ArrayList<String>(),
														   localesUsed = new ArrayList<String>(),
														   objectsUsed = new ArrayList<String>(),
														   othersUsed = new ArrayList<String>();
	/*
	 * inputCollect()
	 * When called by the Main, should wait until the user inputs a command.
	 */
	public void inputCollect(String input) throws InterruptedException, IOException{
		inputVariablesReset();
		if(input.endsWith("\n")){
			input = input.replace("\n", "");
		}
		if(Main.systemReady
				&& 	commandSort(input)){
			translator.translationInit();
		}
	}
	/*
	 * commandSort(String)
	 * Method to sorting and finding valid components of user input to use as in-game interaction
	 * sorts components into arraylists for use in other areas of the engine, clears variables when new input
	 * is entered.
	 * 
	 * returns true if valid command detected, false if not.
	 */
	public boolean commandSort(String input) throws InterruptedException, IOException{
		if(input.contains(" ")){
			//find array in universe directory relative to user location to load available nouns start
			int directoryMarker = 0;
			boolean localeFound = false;
			for(int x = 0; x < database.universeDirectory.length; x++){
				if(database.universeDirectory[x][0].equals(user.userLocation)){
					directoryMarker = x;
					localeFound = true;
					break;
				}
			}
			//find array in universe directory relative to user location to load available nouns end
			//Scan for nouns first so their tokens are ignored.
			//noun scan start
			if(localeFound){
				//character scan start
				StringTokenizer characters;
				try {
					characters = new StringTokenizer(database.universeDirectory[directoryMarker][2],";");
					while(characters.hasMoreTokens()){
						String charactersToken = characters.nextToken();
						File characterDirectory = null;
						if(charactersToken.equals("c_user")
								&& characters.hasMoreTokens()){
							continue;
						}else if(charactersToken.equals("c_user")
								&& !characters.hasMoreTokens()){
							break;
						}else{
							characterDirectory = new File(user.userLocation + "\\" + charactersToken);
						}
						if(characterDirectory.isDirectory()
								&& characterDirectory.exists()){
							File[] characterFiles = characterDirectory.listFiles();
							String characterName = null;
							for(int x = 0; x < characterFiles.length; x++){
								if(characterFiles[x].getName().equals("c_info.txt")){
									characterName = properties.propertiesInit(characterFiles[x].getPath(), "name", 0);
									break;
								}
							}
							try {
								if(input.toLowerCase().contains(characterName.toLowerCase())){
									charactersUsed.add(characterName);
								}
							} catch (NullPointerException charactersNPE) {
							}
						}else{
							//error & terminate output start
							System.err.print("The access to the path " + characterDirectory.getPath() + " was attemped but is not a valid directory."
									+ " Characters, Locations or Objects are their own directory and have their supporting files inside them."
									+ " They are not files themselves, either this path lead to a file or did not exist.\n");
							Thread.sleep(3000);
							System.out.print("\nTerminating...\n");
							Thread.sleep(10000);
							System.exit(001);
							//error & terminate output end
						}
					}
				} catch (NullPointerException charactersNPE) {
				}
				//character scan end
				//locale scan start
				StringTokenizer locales;
				try {
					locales = new StringTokenizer(database.universeDirectory[directoryMarker][1],";");
					while(locales.hasMoreTokens()){
						String localesToken = locales.nextToken();
						File localeDirectory = null;
						if(localesToken.equals("c_user")
								&& locales.hasMoreTokens()){
							continue;
						}else if(localesToken.equals("c_user")
								&& !locales.hasMoreTokens()){
							break;
						}else{
							localeDirectory = new File(user.userLocation + "\\" + localesToken);
						}
						if(localeDirectory.isDirectory()
								&& localeDirectory.exists()){
							File[] localeFiles = localeDirectory.listFiles();
							String localeName = null;
							for(int x = 0; x < localeFiles.length; x++){
								if(localeFiles[x].getName().equals("l_info.txt")){
									localeName = properties.propertiesInit(localeFiles[x].getPath(), "name", 0);
									break;
								}
							}
							try {
								if(input.toLowerCase().contains(localeName.toLowerCase())){
									localesUsed.add(localeName);
								}
							} catch (NullPointerException localeNPE) {
							}
						}else{
							//error & terminate output start
							System.err.print("The access to the path " + localeDirectory.getPath() + " was attemped but is not a valid directory."
									+ " Characters, Locations or Objects are their own directory and have their supporting files inside them."
									+ " They are not files themselves, either this path lead to a file or did not exist.\n");
							Thread.sleep(3000);
							System.out.print("\nTerminating...\n");
							Thread.sleep(10000);
							System.exit(001);
							//error & terminate output end
						}
					}
				} catch (NullPointerException localesNPE) {
				}
				//locale scan end
				//object scan start
				StringTokenizer objects;
				try {
					objects = new StringTokenizer(database.universeDirectory[directoryMarker][3],";");
					while(objects.hasMoreTokens()){
						String objectsToken = objects.nextToken();
						File objectDirectory = null;
						if(objectsToken.equals("c_user")
								&& objects.hasMoreTokens()){
							continue;
						}else if(objectsToken.equals("c_user")
								&& !objects.hasMoreTokens()){
							break;
						}else{
							objectDirectory = new File(user.userLocation + "\\" + objectsToken);
						}
						if(objectDirectory.isDirectory()
								&& objectDirectory.exists()){
							File[] objectFiles = objectDirectory.listFiles();
							String objectName = null;
							for(int x = 0; x < objectFiles.length; x++){
								if(objectFiles[x].getName().equals("o_info.txt")){
									objectName = properties.propertiesInit(objectFiles[x].getPath(), "name", 0);
									break;
								}
							}
							try {
								if(input.toLowerCase().contains(objectName.toLowerCase())){
									objectsUsed.add(objectName);
								}
							} catch (NullPointerException objectsNPE) {
							}
						}else{
							//error & terminate output start
							System.err.print("The access to the path " + objectDirectory.getPath() + " was attemped but is not a valid directory."
									+ " Characters, Locations or Objects are their own directory and have their supporting files inside them."
									+ " They are not files themselves, either this path lead to a file or did not exist.\n");
							Thread.sleep(3000);
							System.out.print("\nTerminating...\n");
							Thread.sleep(10000);
							System.exit(001);
							//error & terminate output end
						}
					}
				} catch (NullPointerException objectsNPE) {
				}
				//object scan end
			}else{
				//error & terminate output start
				System.err.print("The user was not found.\n");
				Thread.sleep(3000);
				System.out.print("\nTerminating...\n");
				Thread.sleep(10000);
				System.exit(001);
				//error & terminate output end
			}
			//noun scan end
			StringTokenizer inputTokens = null;
			try {
				inputTokens = new StringTokenizer(input);
			} catch (NullPointerException inputNPE) {
			}
			while(inputTokens.hasMoreTokens()){
				String currentInputToken = inputTokens.nextToken();
				if(charactersUsed.contains(currentInputToken.CASE_INSENSITIVE_ORDER)
						|| localesUsed.contains(currentInputToken.CASE_INSENSITIVE_ORDER)
						|| objectsUsed.contains(currentInputToken.CASE_INSENSITIVE_ORDER)){
					continue;
				}
				//lists scan start
				//command scan start
				for(int x = 0; x < database.lists.length; x++){
					if(database.lists[x][0].endsWith("command.txt")){
						StringTokenizer commands;
						try {
							commands = new StringTokenizer(database.lists[x][1],";");
						} catch (NullPointerException commandNPE) {
							break;
						}
						while(commands.hasMoreTokens()){
							String commandListToken = commands.nextToken();
							if(commandListToken.contains(" ")
									&& input.toLowerCase().contains(commandListToken.toLowerCase())){
								commandsUsed.add(commandListToken);
							}else if(commandListToken.equalsIgnoreCase(currentInputToken)){
								commandsUsed.add(commandListToken);
							}
						}
					}
				}
				//command scan end
				//other lists scan start
				for(int x = 0; x < database.lists.length; x++){
					if(!database.lists[x][0].endsWith("command.txt")){
						StringTokenizer nameTokenizer;
						try {
							nameTokenizer = new StringTokenizer(database.lists[x][0],"\\");
						} catch (NullPointerException listNameNPE) {
							continue;
						}
						String listName = null;
						while(nameTokenizer.hasMoreTokens()){
							//skip directory path
							listName = nameTokenizer.nextToken();
							if(listName.endsWith(".txt")){
								break;
							}
						}
						if(listName.endsWith(".txt")){
							listName = listName.replaceFirst(".txt", "");
						}
						StringTokenizer others;
						try {
							others = new StringTokenizer(database.lists[x][1],";");
						} catch (NullPointerException othersListNPE) {
							continue;
						}
						while(others.hasMoreTokens()){
							String otherListToken = others.nextToken();
							if(otherListToken.contains(" ")
									&& input.toLowerCase().contains(otherListToken.toLowerCase())){
								othersUsed.add(listName + "_" + otherListToken);
							}else if(otherListToken.equalsIgnoreCase(currentInputToken)){
								othersUsed.add(listName + "_" + otherListToken);
							}
						}
					}
				}
				//other lists scan end
			}
			//lists scan end
		}else{
			//if the input is a single word, must be command if not, return false
			//command scan start
			for(int x = 0; x < database.lists.length; x++){
				if(database.lists[x][0].endsWith("command.txt")){
					StringTokenizer commands;
					try {
						commands = new StringTokenizer(database.lists[x][1],";");
					} catch (NullPointerException commandsNPE) {
						break;
					}
					while(commands.hasMoreTokens()){
						String commandListToken = commands.nextToken();
						if(commandListToken.equalsIgnoreCase(input)){
							commandsUsed.add(commandListToken);
							break;
						}
					}
				}
			}
			//command scan end
		}
		try {
			if(commandsUsed.isEmpty()){
				return false;
			}else{
				return true;
			}
		} catch (NullPointerException cUNPE) {
			return false;
		}
	}
	public void inputVariablesReset(){
		commandsUsed.clear();
		charactersUsed.clear();
		localesUsed.clear();
		objectsUsed.clear();
		othersUsed.clear();
	}
}