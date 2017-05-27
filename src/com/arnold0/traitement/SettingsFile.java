package com.arnold0.traitement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class SettingsFile {
	
	private static String osuUser;
	private static String osuPassword;
	private static String apiKey;
	private static String mode;

	public static void readConfig()
	{
		// On indique que le fichier a lire est config.txt dans ./ (M�me dosser)
		String filePath = "./config.txt";
		Scanner scanner;
		try {
			scanner = new Scanner(new File(filePath));
			// On indique que l'on charge le fichier
			System.out.println("Config file loading...");
			// Tnat qu'il reste des lignes
			while (scanner.hasNextLine()) {
				// On passe a la ligne suivante
			    String line = scanner.nextLine();
			    // Si la ligne ne commance pas par //
				if(line.startsWith("//")==false) 
					{
						//On r�cup�re l'emplacement du | s�parateur
						int separateur = line.indexOf("|",0);
						// Si avant le s�parateur ce trouve un des param�tres
						// On r�cup�re ce qui ce trouve apr�s et le mets dans la variable d�di�e.	
						if(line.substring(0,separateur).equalsIgnoreCase("osuUser"))
							osuUser = line.substring(separateur+1);							
						else if(line.substring(0,separateur).equalsIgnoreCase("osuPassword"))
							osuPassword = line.substring(separateur+1);	
						else if(line.substring(0,separateur).equalsIgnoreCase("apiKey"))
							apiKey = line.substring(separateur+1);	
						else if(line.substring(0,separateur).equalsIgnoreCase("mode"))
							mode = line.substring(separateur+1);	
					}
			}
			 
			// On ferme le fichier apr�s la lecture
			scanner.close();
			
			// Si le fichier n'existe pas
		} catch (FileNotFoundException e) {
			// On retourne dans la console un message indiquant que le bot ne fonctionne pas sans celui-ci.
			System.out.println("File config.txt not found. The program will not work without this file.");
		}
	}

	// Getters pour r�cup�rer les param�tres.

	public static String getOsuUser() {
		return osuUser;
	}

	public static String getOsuPassword() {
		return osuPassword;
	}

	public static String getApiKey() {
		return apiKey;
	}
	
	public static String getMode() {
		return mode;
	}
	
}
