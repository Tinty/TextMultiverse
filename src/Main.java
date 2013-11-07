import java.io.IOException;
/*
 * Pnoy
 * 
 * Main Class
 * From here methods and classes will be called to perform required actions
 * for the engine on demand.
 */
public class Main {
	public static String universe = null;
	public static void main(String[] args) throws IOException, InterruptedException{
		Window window = new Window();
		User user = new User();
		DatabaseManagement database = new DatabaseManagement();
		window.windowInit();
		database.databaseInit();
		user.userInit();
	}
	public static void setUniverse(String incoming){
		universe = incoming;
	}
	public static String getUniverse(){
		return universe;
	}
}