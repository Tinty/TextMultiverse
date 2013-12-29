import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
/*
 * Translator class
 * Class for taking various input and assigning meaning and effects to them, distributing them
 * to appropriate methods based on constructed meaning.
 */
public class Translator {
	Properties properties = new Properties();
	static String libraryRoot = "data/" + Main.getUniverse().toLowerCase();
	/*
	 * translationInit()
	 */
	public boolean translationInit() throws IOException{
		for(int x = 0; x < Input.commandsUsed.size(); x++){
			String type = fetchType("command", Input.commandsUsed.get(x));
			try {
				if(!type.equals(null)){
					switch(type){
						case "search":{
							String[][] output = searchOutput();
							ToolBag tool = new ToolBag();
							String[] userVicinities = null;
							for(int y = 0; y < Main.localeInventory.length; y++){
								for(int z = 0; z < Main.localeInventory[y].length; z++){
									String[] typeStor = tool.fetchFromInventory("type", Main.localeInventory[y][z]);
									try {
										for(String typeToCheck : typeStor){
											if(typeToCheck.toLowerCase().contains("user")){
												userVicinities = tool.fetchFromInventory("location", Main.localeInventory[y][z]);
												break;
											}
											try {
												if(!userVicinities.equals(null)){
													break;
												}
											} catch (NullPointerException uLTINPE2) {
												continue;
											}
										}
									} catch (NullPointerException uLTINPE) {
										continue;
									}
									try {
										if(!userVicinities.equals(null)){
											break;
										}
									} catch (NullPointerException uLTINPE2) {
										continue;
									}
								}
								try {
									if(!userVicinities.equals(null)){
										break;
									}
								} catch (NullPointerException uLTINPE2) {
									continue;
								}
							}
							try {
								if(!output.equals(null)){
									try {
										if(!userVicinities.equals(null)){
											ArrayList<String> printedLocations = new ArrayList<String>();
											//print user first start
											for(String loc : userVicinities){
												int counter = 0;
												do{
													try {
														if(!output[counter][0].equals(null)
																&& output[counter][0].toLowerCase().equals(loc.toLowerCase())
																&& !output[counter][1].equals(null)){
															System.out.print(output[counter][1]);
															printedLocations.add(output[counter][0]);
														}
													} catch (NullPointerException oPNPE) {
													}
													counter++;
												}while(counter != output.length);
											}
											//print user first end
											//print non user last start
											int counter = 0;
											do{
												try {
													if(!output[counter][0].equals(null)
															&& !printedLocations.contains(output[counter][0])
															&& !output[counter][1].equals(null)){
														System.out.print(output[counter][1]);
													}
												} catch (NullPointerException oPNPE) {
												}
												counter++;
											}while(counter != output.length);
											//print non user last end
										}
									} catch (NullPointerException uLTINPE2) {
										//print reg start
										int counter = 0;
										do{
											try {
												if(!output[counter][0].equals(null)
														&& !output[counter][1].equals(null)){
													System.out.print(output[counter][1]);
													counter++;
												}
											} catch (NullPointerException oPNPE) {
												counter++;
											}
										}while(counter != output.length);
										//print reg end
									}
									return true;
								}
							} catch (NullPointerException searchOutputNPE) {
								return false;
							}
							break;
						}
						default:{
							return false;
						}
					}
				}
			} catch (NullPointerException translationNPE) {
			}
		}
		return false;
	}
	/*
	 * fetchType(String,String)
	 */
	private String fetchType(String subDirectory, String toFind) throws IOException{
		File[] directory = new File(libraryRoot + "/" + subDirectory).listFiles();
		for(int x = 0; x < directory.length; x++){
			if(directory[x].getName().equalsIgnoreCase(toFind + ".txt")){
				return properties.propertiesInit(directory[x].getPath(), "type", 0);
			}
		}
		return null;
	}
	/*
	 * seatchOutput()
	 */
	public String[][] searchOutput(){
		ToolBag tool = new ToolBag();
		String[][] output = new String[tool.localeInventoryDataCount("location")][2];
		ArrayList<String> locations = tool.localeInventoryDataFetch("location");
		for(int x = 0; x < locations.size(); x++){
			output[x][0] = locations.get(x);
		}
		//user information fetch start
		String[] userName = null,
					  userLocation = null,
					  userState = null,
					  userLocalDescription = null,
					  userVicinityDescription = null;
		boolean isUser = false;
		for(int x = 0; x < Main.localeInventory.length; x++){
			//check if user start
			isUser = tool.checkLocaleArrayForType(x, "user");
			//check if user end
			//user description information fetch start
			if(isUser){
				User user = new User();
				String[][] userInfoTemp = user.fetchUserDescription(x);
				for(int y = 0; y < userInfoTemp.length; y++){
					if(y == 0){
						userName = userInfoTemp[y];
					}else if(y == 1){
						userLocation = userInfoTemp[y];
					}else if(y == 2){
						userState = userInfoTemp[y];
					}else if(y == 3){
						userLocalDescription = userInfoTemp[y];
					}else if(y == 4){
						userVicinityDescription = userInfoTemp[y];
					}
				}
			}
		}
		//user description information fetch end
		//User specific integration start
		for(int z = 0; z < output.length; z++){
			for(String loc : userLocation){
				if(output[z][0].toLowerCase().contains(loc.toLowerCase())){
					try {
						if(!output[z][1].equals(null)){
							String outputTemp = output[z][1];
							output[z][1] = "You are ";
							try {
								if(!userState.equals(null)){
									int counter = 0;
									do{
										if(counter > 1
												&& counter != userState.length){
											output[z][1] += ", ";
										}else if(counter > 1
												&& counter == userState.length){
											output[z][1] += " and ";
										}
										output[z][1] += " " + userState[counter];
										counter++;
									}while(counter != userState.length);
									output[z][1] += " near " + outputTemp;
									break;
								}
							} catch (NullPointerException uSONPE2) {
								output[z][1] = "You are here with " + output[z][1];
								break;
							}
						}
					} catch (NullPointerException uSONPE) {
						try {
							if(!userState.equals(null)){
								output[z][1] = "You are";
								int counter = 0;
								do{
									if(counter > 1
											&& counter != userState.length){
										output[z][1] += ",";
									}else if(counter > 1
											&& counter == userState.length){
										output[z][1] += " and";
									}
									output[z][1] += " " + userState[counter] + " ";
									counter++;
								}while(counter != userState.length);
								output[z][1] += "here and observe your surroundings.\n\n";
								break;
							}
						} catch (NullPointerException uSONPE2) {
							output[z][1] = "You are here and observe your surroundings.\n\n";
							break;
						}
					}
				}
			}
		}
		//User specific integration end
		for(int x = 0; x < Main.localeInventory.length; x++){
			String[] entityName = null,
						  entityType = null,
					   	  entityLocation = null,
					   	  entityState = null,
					   	  entityLocalDescription = null,
					   	  entityVicinityDescription = null;
			//user skip check start
			boolean skip = false;
			for(int y = 0; y < Main.localeInventory[x].length; y++){
				try {
					String[] typesTemp = tool.fetchFromInventory("type", Main.localeInventory[x][y]);
					for(String typeToCheck : typesTemp){
						if(typeToCheck.toLowerCase().contains("user")){
							skip = true;
							break;
						}
					}
				} catch (NullPointerException uSCSONPE) {
					continue;
				}
				if(skip){
					break;
				}
			}
			if(skip){
				continue;
			}
			//user skip check end
			//fetch entity output information start
			Entities entity = new Entities();
			String[][] entityInfoTemp = entity.fetchEntityDescription(x);
			for(int y = 0; y < entityInfoTemp.length; y++){
				if(y == 0){
					entityName = entityInfoTemp[y];
				}else if(y == 1){
					entityType = entityInfoTemp[y];
				}else if(y == 2){
					entityLocation = entityInfoTemp[y];
				}else if(y == 3){
					entityState = entityInfoTemp[y];
				}else if(y == 4){
					entityLocalDescription = entityInfoTemp[y];
				}else if(y == 5){
				   	entityVicinityDescription = entityInfoTemp[y];
				}
			}
			//fetch entity output information end
			//entity integration start
			boolean isUserAlone = true;
			for(int z = 0; z < output.length; z++){
				for(String loc : entityLocation){
					if(output[z][0].toLowerCase().contains(loc.toLowerCase())){
						boolean userIsHere = false;
						for(String userLoc : userLocation){
							if(output[z][0].toLowerCase().contains(userLoc.toLowerCase())){
								userIsHere = true;
							}
						}
						if(userIsHere){
							isUserAlone = false;
							//if user is in location
							//entity vicinity description insertion start
							//since the user is here, will try loading
							//vicinity description, assumed since user is here
							//that output element is not null.
							try {
								if(!entityVicinityDescription.equals(null)){
									output[z][1] += "Near you, you find...\n\n";
									int counter = 0;
									do{
										if(counter > 1
												&& counter != entityVicinityDescription.length){
											output[z][1] += ", ";
										}else if(counter > 1
												&& counter == entityVicinityDescription.length){
											output[z][1] += ". Additionally, ";
										}
										String temp = entityVicinityDescription[counter];
										//bracket info sub & check start
										temp = tool.bracketedInterpolation(temp, x);
										//bracket info sub & check end
										if(!output[z][1].trim().replaceAll("\\n", "").endsWith(".")){
											output[z][1] += StringUtils.uncapitalize(temp);
										}else{
											output[z][1] += StringUtils.capitalize(temp);
										}
										counter++;
									}while(counter != entityVicinityDescription.length);
								}
								//entity vicinity description insertion start
								//entity state insertion start
								try {
									if(!entityState.equals(null)){
										int counter = 0;
										do{
											if(counter > 1
													&& counter != entityState.length){
												output[z][1] += ", ";
											}else if(counter > 1
													&& counter == entityState.length){
												output[z][1] += " and ";
											}
											output[z][1] += " " + entityState[counter];
											counter++;
										}while(counter != entityState.length);
										try {
											if(!output[z][1].endsWith("\\n")
													&& !output[z][1].endsWith(".")
													&& !output[z][1].endsWith("?")
													&& !output[z][1].endsWith("!")){
												output[z][1] += " here.";
											}else{
												output[z][1] += ".";
											}
										} catch (NullPointerException eSONPE) {
											continue;
										}
										continue;
									}
								} catch (NullPointerException eDONPE2) {
									try {
										if(!output[z][1].endsWith("\\n")
												&& !output[z][1].endsWith(".")
												&& !output[z][1].endsWith("?")
												&& !output[z][1].endsWith("!")){
											output[z][1] += ".";
										}
									} catch (NullPointerException eSONPE) {
										continue;
									}
									continue;
								}
								//entity state insertion end
							} catch (NullPointerException eDSONPE2) {
								//if vicinity description == null
								//entity local description insertion start
								//Though if an entity is in the same location
								//as the user, the vicinity description should load,
								//if one is not available, will try to load local description.
								try {
									if(!entityLocalDescription.equals(null)){
										output[z][1] += "Near you, you find...\n\n";
										int counter = 0;
										do{
											if(counter > 1
													&& counter != entityLocalDescription.length){
												output[z][1] += ", ";
											}else if(counter > 1
													&& counter == entityLocalDescription.length){
												output[z][1] += ". Additionally, ";
											}
											String temp = entityLocalDescription[counter];
											//bracket info sub & check start
											temp = tool.bracketedInterpolation(temp, x);
											//bracket info sub & check end
											if(!output[z][1].trim().replaceAll("\\n", "").endsWith(".")){
												output[z][1] += StringUtils.uncapitalize(temp);
											}else{
												output[z][1] += StringUtils.capitalize(temp);
											}
											counter++;
										}while(counter != entityLocalDescription.length);
									}
									//entity local description insertion end
									//entity state insertion start
									try {
										if(!entityState.equals(null)){
											int counter = 0;
											do{
												if(counter > 1
														&& counter != entityState.length){
													output[z][1] += ",";
												}else if(counter > 1
														&& counter == entityState.length){
													output[z][1] += " and";
												}
												output[z][1] += " " + entityState[counter];
												counter++;
											}while(counter != entityState.length);
											try {
												if(!output[z][1].endsWith("\\n")
														&& !output[z][1].endsWith(".")
														&& !output[z][1].endsWith("?")
														&& !output[z][1].endsWith("!")){
													output[z][1] += " here.";
												}else{
													output[z][1] += ".";
												}
											} catch (NullPointerException eSONPE) {
												continue;
											}
											continue;
										}
									} catch (NullPointerException eDONPE2) {
										try {
											if(!output[z][1].endsWith("\\n")
													&& !output[z][1].endsWith(".")
													&& !output[z][1].endsWith("?")
													&& !output[z][1].endsWith("!")){
												output[z][1] += ".\n";
											}
										} catch (NullPointerException eSONPE) {
											continue;
										}
										continue;
									}
									//entity state insertion end
								} catch (NullPointerException eSONPE2) {
									//if vicinity and local description == null
									try {
										if(!output[z][1].endsWith("\\n")
												&& !output[z][1].endsWith(".")
												&& !output[z][1].endsWith("?")
												&& !output[z][1].endsWith("!")){
											output[z][1] += ".\n";
										}
									} catch (NullPointerException eSONPE) {
										continue;
									}
									continue;
								}
							}
						}else{
							//if user is not in location
							//entity local description insertion start
							//since the user is not in the location,
							//local description is used and vicinity description is ignored.
							//This will execute if the previous output
							//element is not null, or has been written to prior.
							try {
								if(!output[z][1].equals(null)){
									try {
										if(!entityLocalDescription.equals(null)){
											if(output[z][1].endsWith(".")){
												output[z][1] = output[z][1].substring(0, output[z][1].length() - 1);
											}
											boolean isOutside = false;
											for(String temp : entityType){
												if(temp.toLowerCase().contains("indoor")){
													output[z][1] += " outside ";
													isOutside = true;
													break;
												}
											}
											if(!isOutside){
												output[z][1] += " near by is ";
											}
											int counter = 0;
											do{
												if(counter > 1
														&& counter != entityLocalDescription.length){
													output[z][1] += ", ";
												}else if(counter > 1
														&& counter == entityLocalDescription.length){
													output[z][1] += ". Additionally, ";
												}
												String temp = entityLocalDescription[counter];
												//bracket info sub & check start
												temp = tool.bracketedInterpolation(temp, x);
												//bracket info sub & check end
												if(!output[z][1].trim().replaceAll("\\n", "").endsWith(".")){
													output[z][1] += StringUtils.uncapitalize(temp);
												}else{
													output[z][1] += StringUtils.capitalize(temp);
												}
												counter++;
											}while(counter != entityLocalDescription.length);
										}
										//entity local description insertion end
										//entity state insertion start
										try {
											if(!entityState.equals(null)){
												int counter = 0;
												do{
													if(counter > 1
															&& counter != entityState.length){
														output[z][1] += ",";
													}else if(counter > 1
															&& counter == entityState.length){
														output[z][1] += " and";
													}
													output[z][1] += " " + entityState[counter];
													counter++;
												}while(counter != entityState.length);
												try {
													if(!output[z][1].endsWith("\\n")
															&& !output[z][1].endsWith(".")
															&& !output[z][1].endsWith("?")
															&& !output[z][1].endsWith("!")){
														output[z][1] += ".";
													}
												} catch (NullPointerException eSONPE) {
													continue;
												}
												continue;
											}
										} catch (NullPointerException eDONPE2) {
											try {
												if(!output[z][1].endsWith("\\n")
														&& !output[z][1].endsWith(".")
														&& !output[z][1].endsWith("?")
														&& !output[z][1].endsWith("!")){
													output[z][1] += ".";
												}
											} catch (NullPointerException eSONPE) {
												continue;
											}
											continue;
										}
										//entity state insertion end
									} catch (NullPointerException uSONPE2) {
										//if local description == null
										try {
											if(!output[z][1].endsWith("\\n")
													&& !output[z][1].endsWith(".")
													&& !output[z][1].endsWith("?")
													&& !output[z][1].endsWith("!")){
												output[z][1] += ".\n";
											}
										} catch (NullPointerException eSONPE) {
											continue;
										}
										continue;
									}
								}
							} catch (NullPointerException eLDSONPE) {
								//entity local description insertion start
								//This since the user is not in the location, and nothing else was
								//previously loaded into this location's output element
								//as the element being amended previously was null
								try {
									if(!entityLocalDescription.equals(null)){
										output[z][1] = "In the distance you see...\n\n";
										int counter = 0;
										do{
											if(counter > 1
													&& counter != entityLocalDescription.length){
												output[z][1] += ", ";
											}else if(counter > 1
													&& counter == entityLocalDescription.length){
												output[z][1] += ". Additionally, ";
											}
											String temp = entityLocalDescription[counter];
											//bracket info sub & check start
											temp = tool.bracketedInterpolation(temp, x);
											//bracket info sub & check end
											if(!output[z][1].trim().replaceAll("\\n", "").endsWith(".")){
												output[z][1] += StringUtils.uncapitalize(temp);
											}else{
												output[z][1] += StringUtils.capitalize(temp);
											}
											counter++;
										}while(counter != entityLocalDescription.length);
									}
									//entity local description insertion end
									//entity state insertion start
									try {
										if(!entityState.equals(null)){
											int counter = 0;
											do{
												if(counter > 1
														&& counter != entityState.length){
													output[z][1] += ",";
												}else if(counter > 1
														&& counter == entityState.length){
													output[z][1] += " and";
												}
												if(counter == 0){
													output[z][1] += " is";
												}
												output[z][1] += " " + entityState[counter];
												counter++;
											}while(counter != entityState.length);
											try {
												if(!output[z][1].endsWith("\\n")
														&& !output[z][1].endsWith(".")
														&& !output[z][1].endsWith("?")
														&& !output[z][1].endsWith("!")){
													output[z][1] += ".";
												}
											} catch (NullPointerException eSONPE) {
												continue;
											}
											continue;
										}
									} catch (NullPointerException eDONPE2) {
										try {
											if(!output[z][1].endsWith("\\n")
													&& !output[z][1].endsWith(".")
													&& !output[z][1].endsWith("?")
													&& !output[z][1].endsWith("!")){
												output[z][1] += ".";
											}
										} catch (NullPointerException eSONPE) {
											continue;
										}
										continue;
									}
									//entity state insertion end
								} catch (NullPointerException eLDSONPE2) {
									//if local description == null
									try {
										if(!output[z][1].endsWith("\\n")
												&& !output[z][1].endsWith(".")
												&& !output[z][1].endsWith("?")
												&& !output[z][1].endsWith("!")){
											output[z][1] += ".\n";
										}
									} catch (NullPointerException eSONPE) {
										continue;
									}
									continue;
								}
							}
						}
					}
				}
			}
			//entity integration end
		}
		return output;
	}
}