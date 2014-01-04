import java.io.File;
import java.io.IOException;
/*
 * Pnoy;Tinty
 *
 * Main Class
 * From here methods and classes will be called to perform required actions
 * for the engine on demand.
 */
public class Main {
	public static String universe = null;
	public static boolean systemReady = false;
	public static String[][][][] localeInventory;
	public static void main(String[] args) throws IOException, InterruptedException{
		Window window = new Window();
		DatabaseManagement database = new DatabaseManagement();
		User user = new User();
		window.windowInit();
		database.databaseInit();
		database.listsInit();
		if(!user.userInit()){
			//error & terminate output start
			System.err.print("The user was not found.\n");
			Thread.sleep(3000);
			System.out.print("\nTerminating...\n");
			Thread.sleep(10000);
			System.exit(001);
			//error & terminate output end
		}else{
			loadLocale();
			systemReady = true;
		}
	}
	public static void setUniverse(String incoming){
		universe = incoming;
	}
	public static String getUniverse(){
		return universe;
	}
	public static void loadLocale() throws IOException{
		Properties properties = new Properties();
		File[] directory = new File(User.userLocation).listFiles();
		int numOfFiles = 0,
			  numOfSubs = 0;
		//count number of subdirectories and files in directory start
		for(int x = 0; x < directory.length; x++){
			if(directory[x].isFile()){
				numOfFiles++;
			}else if(directory[x].isDirectory()){
				numOfSubs++;
			}
		}
		//count number of subdirectories and files in directory end
		//init localeInventory start
		//plus one to account for current directory
		//TODO: To include parent Directories
		localeInventory = new String[numOfSubs+1][][][];
		//init localInventory end
		File[] directoryFiles = new File[numOfFiles],
				  directorySubs = new File[numOfSubs];
		//load and populate directory files start
		int read = 0,
			  marker = 0;
		while(read != numOfFiles){
			try {
				if(directory[marker].isFile()){
					directoryFiles[read] = directory[marker];
					read++;
				}
			} catch (IndexOutOfBoundsException populateDFIOOE) {
				break;
			}
			marker++;
		}
		//load and populate directory files end
		read = 0;
		marker = 0;
		while(read != numOfSubs){
			try {
				if(directory[marker].isDirectory()){
					directorySubs[read] = directory[marker];
					read++;
				}
			} catch (IndexOutOfBoundsException populateDFIOOE) {
				break;
			}
			marker++;
		}
		//add subDirectory to inventory start
		for(int x = 0; x < directoryFiles.length; x++){
			if(directoryFiles.length == 1
					&& directoryFiles[0].isFile()
					&& directoryFiles[0].getName().endsWith(".txt")){
				localeInventory[x] = new String[1][][];
				localeInventory[x][0] = properties.fileReadIn(directoryFiles[0].getPath());
			}else if (directoryFiles.length > 1){
				String[][][] tempFiles = new String[directoryFiles.length][][];
				for(int y = 0; y < directoryFiles.length; y++){
					if(directoryFiles[y].isFile()
							&& directoryFiles[y].getName().endsWith(".txt")){
						tempFiles[y] = properties.fileReadIn(directoryFiles[y].getPath());
					}
				}
				localeInventory[x] = tempFiles;
			}
		}
		//add subDirectory to inventory end
		for(int x = 0; x < directorySubs.length; x++){
			if(directorySubs[x].isDirectory()){
				File[] subDirectoryTemp = new File(directorySubs[x].getPath()).listFiles(),
						  subDirectory;
				int subNumOfFiles = 0;
				//count number of files in subDirectory start
				for(int y = 0; y < subDirectoryTemp.length; y++){
					if(subDirectoryTemp[y].isFile()){
						subNumOfFiles++;
					}
				}
				//count number of files in subDirectory end
				//init size of subDirectory array based on number of files,
				//removing and skipping additional subdirectories from the root subdirectory
				//here we are only interested in files contained in the root subdirectory
				subDirectory = new File[subNumOfFiles];
				//populate subDirectory array with files start
				int z = 0,
					  counter = 0;
				while(z != subNumOfFiles){
					try {
						if(subDirectoryTemp[counter].isFile()){
							subDirectory[z] = subDirectoryTemp[counter];
							z++;
						}
					} catch (IndexOutOfBoundsException populateSDIOOE) {
						break;
					}
					counter++;
				}
				//populate subDirectory array with files end
				//add subDirectory to inventory start
				if(subDirectory.length == 1
						&& subDirectory[0].isFile()
						&& subDirectory[0].getName().endsWith(".txt")){
					try {
						if(localeInventory[x].equals(null)){
							localeInventory[x] = new String[1][][];
							localeInventory[x][0] = properties.fileReadIn(subDirectory[0].getPath());
						}else{
							int vacancyIncrement = 1;
							try {
								while(!localeInventory[x+vacancyIncrement].equals(null)){
									vacancyIncrement++;
								}
								localeInventory[x+vacancyIncrement] = new String[1][][];
								localeInventory[x+vacancyIncrement][0] = properties.fileReadIn(subDirectory[0].getPath());
							} catch (NullPointerException lINPE) {
								localeInventory[x+vacancyIncrement] = new String[1][][];
								localeInventory[x+vacancyIncrement][0] = properties.fileReadIn(subDirectory[0].getPath());
							}
						}
					} catch (NullPointerException lINPE) {
						localeInventory[x] = new String[1][][];
						localeInventory[x][0] = properties.fileReadIn(subDirectory[0].getPath());
					}
				}else if (subDirectory.length > 1){
					String[][][] tempFiles = new String[subDirectory.length][][];
					for(int y = 0; y < subDirectory.length; y++){
						if(subDirectory[y].isFile()
								&& subDirectory[y].getName().endsWith(".txt")){
							tempFiles[y] = properties.fileReadIn(subDirectory[y].getPath());
						}
					}
					try {
						if(localeInventory[x].equals(null)){
							localeInventory[x] = tempFiles;
						}else{
							int vacancyIncrement = 1;
							try {
								while(!localeInventory[x+vacancyIncrement].equals(null)){
									vacancyIncrement++;
								}
								localeInventory[x+vacancyIncrement] = tempFiles;
							} catch (NullPointerException lINPE) {
								localeInventory[x+vacancyIncrement] = tempFiles;
							}
						}
					} catch (NullPointerException lINPE) {
						localeInventory[x] = tempFiles;
					}
				}
				//add subDirectory to inventory end
			}
		}
	}
}