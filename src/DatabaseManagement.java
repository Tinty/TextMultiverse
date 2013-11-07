import java.io.*;
/*
 * DatabaseManagement Class
 * For use in reading/writing and manipulations of data files of the loaded enviroment
 */
public class DatabaseManagement {
	public static String universeDir = "data/multiverse/" + Main.getUniverse();
	public void databaseInit() throws InterruptedException{
		File universeFiles = new File(universeDir);
		if(universeFiles.exists()
				&& universeFiles.isDirectory()){
		}else{
			//if fails to load universe, print error message and terminate
			System.err.print("The directory, \"" + universeDir + "\" is an invalid universe directory or could not load properly.\n");
			Thread.sleep(5000);
			System.exit(001);
		}
	}
}