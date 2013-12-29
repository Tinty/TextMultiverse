import java.util.ArrayList;
import java.io.*;
/*
 * Database Management Class
 * For the manipulations of database structures that the engine uses to sort and organize
 * structures and inventories of universe information used and parsed by the engine.
 */
public class DatabaseManagement{
	public static String rootDirectory = "data/multiverse/" + Main.getUniverse().toLowerCase();
	public static String[][] universeDirectory;
	public static String[][] lists;
	/*
	 * databaseInit()
	 * This method intializes the primary inventory management system required for the engine's operation.
	 * It sorts and categorizes the universe filesystem from root, reading all files and subdirectories appropriatley into a 2D array.
	 * 
	 * Constructs the universeDirectory.
	 */
	public void databaseInit() throws InterruptedException{
		try {
			if(rootVerify(rootDirectory).equals(null) == false){
				//Initialize additional directory navigation variables start
				ArrayList<String> directoriesScanned = new ArrayList<String>();
				String currentDirectory = rootDirectory;
				boolean complete = false;
				File directory = new File(rootDirectory);
				int numOfDirs = directoryCount(directory),
					  repeaterMarker = 0;
				universeDirectory = new String[numOfDirs][5];
				//Initialize additional directory navigation variables end
				do{
					repeaterMarker++;
					//repetition check if go to parent directory from current, done if repeat on root start
					if(repeaterMarker >= 2
							&& !directory.getPath().equals(directoriesScanned.get(0))){
						directory = new File(directory.getParent());
						repeaterMarker = 0;
						continue;
					}else if(repeaterMarker >= 2
							&& directory.getPath().equals(directoriesScanned.get(0))){
						complete = true;
					}
					//repetition check if go to parent directory from current, done if repeat on root end
					if(complete == false){
						//Current directory variable scan init start
						File[] subNames = directory.listFiles();
						int uDMarker = 0;
						//Current directory variable scan init end
						if (!directoriesScanned.contains(directory.getPath())){
							//Add unscanned directory to array start
							for(int x = 0; x < numOfDirs; x++){
								try {
									if(universeDirectory[x][0].isEmpty()){
										universeDirectory[x][0] = directory.getPath();
										uDMarker = x;
										break;
									}
								} catch (NullPointerException uDNPE) {
									universeDirectory[x][0] = directory.getPath();
									uDMarker = x;
									break;
								}
							}
							//Add unscanned directory to array end
							//current directory scan start
							for(int x = 0; x < subNames.length; x++){
								if(subNames[x].isDirectory()
										&& !subNames[x].isHidden()){
									if(subNames[x].getName().startsWith("l_")){
										try {
											if(universeDirectory[uDMarker][1].isEmpty()){
												universeDirectory[uDMarker][1] = subNames[x].getName() + ";";
											}else{
												universeDirectory[uDMarker][1] += subNames[x].getName() + ";";
											}
										} catch (NullPointerException lNPE) {
											universeDirectory[uDMarker][1] = subNames[x].getName() + ";";
										}
									}else if(subNames[x].getName().startsWith("c_")){
										try {
											if(universeDirectory[uDMarker][2].isEmpty()){
												universeDirectory[uDMarker][2] = subNames[x].getName() + ";";
											}else{
												universeDirectory[uDMarker][2] += subNames[x].getName() + ";";
											}
										} catch (NullPointerException lNPE) {
											universeDirectory[uDMarker][2] = subNames[x].getName() + ";";
										}
									}else if(subNames[x].getName().startsWith("o_")){
										try {
											if(universeDirectory[uDMarker][3].isEmpty()){
												universeDirectory[uDMarker][3] = subNames[x].getName() + ";";
											}else{
												universeDirectory[uDMarker][3] += subNames[x].getName() + ";";
											}
										} catch (NullPointerException lNPE) {
											universeDirectory[uDMarker][3] = subNames[x].getName() + ";";
										}
									}
								}else if(subNames[x].isFile()
										&& !subNames[x].isHidden()){
									try {
										if(universeDirectory[uDMarker][4].isEmpty()){
											universeDirectory[uDMarker][4] = subNames[x].getName() + ";";
										}else{
											universeDirectory[uDMarker][4] += subNames[x].getName() + ";";
										}
									} catch (NullPointerException lNPE) {
										universeDirectory[uDMarker][4] = subNames[x].getName() + ";";
									}
								}
							}
							directoriesScanned.add(directory.getPath());
							repeaterMarker = 0;
							//current directory scan end
						}else{
							//set subdirectory path to scan start
							for(int x = 0; x < subNames.length; x++){
								if(subNames[x].isDirectory()
										&& !directoriesScanned.contains(subNames[x].getPath())){
									directory = new File(subNames[x].getPath());
									repeaterMarker = 0;
									break;
								}
							}
							//set subdirectory path to scan end
						}
					}
				}while(complete == false);
			}
		} catch (NullPointerException rootVerifyDBINPE) {
			//error & terminate output start
			System.err.print("The universe directory, \"" + rootDirectory + "\" is invalid or could not load properly.\n\n");
			Thread.sleep(3000);
			rootVerifyDBINPE.printStackTrace();
			System.out.print("\nTerminating...\n");
			Thread.sleep(10000);
			System.exit(001);
			//error & terminate output end
		}
	}
	/*
	 * listsInit()
	 * Initalizes and is responsible for ensuring the population of lists determined be list files in the lists directory.
	 * 
	 * produces a public static 2D string array the directory, and it's appropriate content.
	 */
	public void listsInit() throws IOException{
		Properties properties = new Properties();
		String commandRoot = "data/" + Main.getUniverse().toLowerCase() + "/lists";
		File[] directory = new File(commandRoot).listFiles();
		lists = new String[directory.length][2];
		for(int x = 0; x < directory.length; x++){
			lists[x][0] = directory[x].getPath();
			String propertyName = directory[x].getName();
			if(propertyName.endsWith(".txt")){
				propertyName = propertyName.replaceAll(".txt", "");
			}
			lists[x][1] = properties.propertiesInit(directory[x].getPath(), propertyName, 1);
		}
	}
	/*
	 * rootVerify(String)
	 * Method to determine whether or not root directory provided is valid and engine can proceed with it.
	 * 
	 * returns String, directory or null if not valid.
	 */
	public String rootVerify(String directory){
		File root = new File(directory);
		if(root.exists()
				&& root.isDirectory()){
			return directory;
		}else{
			return null;
		}
	}
	/*
	 * directoryCount(File)
	 * Method to scan parameter that should be root of a directory filesystem
	 * 
	 * returns int, number of total subdirectories inside root directory.
	 */
	public int directoryCount(File directory){
		//init variables start
		ArrayList<String> directoriesChecked = new ArrayList<String>();
		boolean allDirectoriesChecked = false;
		//Directory count init at 1 to account for root directory
		int directoryCount = 1,
			 repeaterMarker = 0;
		//init variables end
		do{
			repeaterMarker++;
			//repetition check if go to parent directory from current, done if repeat on root start
			if(repeaterMarker >= 2
					&& !directory.getPath().equals(directoriesChecked.get(0))){
				directory = new File(directory.getParent());
				repeaterMarker = 0;
				continue;
			}else if(repeaterMarker >= 2
					&& directory.getPath().equals(directoriesChecked.get(0))){
				allDirectoriesChecked = true;
			}
			//repetition check if go to parent directory from current, done if repeat on root end
			if(allDirectoriesChecked == false){
				File[] subDirectories = directory.listFiles();
				if(!directoriesChecked.contains(directory.getPath())){
					//count subdirectories in directory start
					for(int x = 0; x < subDirectories.length; x++){
						if(subDirectories[x].isDirectory()
								&& !subDirectories[x].isHidden()){
							directoryCount++;
						}
					}
					//count subdirectories in directory end
					directoriesChecked.add(directory.getPath());
					repeaterMarker = 0;
				}else{
					//set subdirectory path to scan start
					for(int x = 0; x < subDirectories.length; x++){
						if(subDirectories[x].isDirectory()
								&& !subDirectories[x].isHidden()
								&& !directoriesChecked.contains(subDirectories[x].getPath())){
							directory = new File(subDirectories[x].getPath());
							repeaterMarker = 0;
							break;
						}
					}
					//set subdirectory path to scan end
				}
			}
		}while(allDirectoriesChecked == false);
		return directoryCount;
	}
}