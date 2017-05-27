package com.rendyak.bot;

import com.rendyak.traitement.ApiReqs;
import com.rendyak.traitement.SettingsFile;
import com.rendyak.traitement.TraitementJson;

public class mainBot {
	private static String osuUser;
	private static String osuPassword;
	private static String apiKey;
	private static String mode;
	private static double PPs;
	private static int rank;
	private static TraitementJson jsonDecoder;
	private static String Json;
	private static boolean shouldLoop = true;
	private static boolean justStarted = true;
	
	public static void main(String[] args) throws Exception {
        
		//On lit le fichier de config
		SettingsFile.readConfig();
		
		//Si les paramètres requis ne sont pas manquants
		if(SettingsFile.getOsuPassword()!=null&&SettingsFile.getOsuUser()!=null&&SettingsFile.getApiKey()!=null&&SettingsFile.getMode()!=null) {	
			//On récupère tout les paramètres du fichier de config.
			osuUser = SettingsFile.getOsuUser();
			osuPassword = SettingsFile.getOsuPassword();
			apiKey = SettingsFile.getApiKey();
			mode = SettingsFile.getMode();
			
			OsuBot osubot = new OsuBot(osuUser);
			
			// Connexion a l'IRC d'osu!
			osubot.connect("irc.ppy.sh",6667,osuPassword);
			ApiReqs apiReq = new ApiReqs(apiKey);
			
			jsonDecoder = new TraitementJson();
			while(shouldLoop){
				try {
					// on envoie la requête a l'API avec le username et le mode
					Json = apiReq.sendGet(osuUser, mode);
				} catch (Exception e) {
					// Si on erreur est survenue, on passe Json a NULL
					Json=null;
				}
				// On verifie que Json n'est pas null pour être sur qu'il n'y a pas eu d'erreur
				if(Json!=null){
			       	// On décode le Json retourné par l'API
			       	boolean success = jsonDecoder.decode(Json);
			       	// Si le decodage a bien retourné un String (Qui contient les infos de la map)
			       	if (success) {
			       		if(justStarted) {
			       			PPs = jsonDecoder.getPP();
			       			rank = jsonDecoder.getRank();
			       			osubot.sendMessage(osuUser, "Welcome to PPChangeBot, "+osuUser+"!");
			       			osubot.sendMessage(osuUser, "This bot is based on arnold0 PP change bot.");
							osubot.sendMessage(osuUser, "Your current PPs are : "+PPs+". Your current rank is #"+rank+".");
							System.out.println("PPChangeBot is now running and will check the API for PP change every 5 seconds.");
							System.out.println("You should have recieved a message in game. If not, please check the config file for errors.");
							justStarted = false;
			       		}
			       		else {
			       			if (jsonDecoder.getPP()!=PPs){
			       				double NewPP = jsonDecoder.getPP();
			       				double PPdiff = Math.round((NewPP - PPs)*100.00)/100.00;
			       				int NewRank = jsonDecoder.getRank();
			       				int rankDiff = 0;
			       				if(NewPP != PPs){
			       					if(NewPP>PPs){
			       						osubot.sendMessage(osuUser, "Your PPs changed! ("+PPdiff+" gained). New PP amount : "+NewPP+".");
			       					}
			       					else{
			       						osubot.sendMessage(osuUser, "Your PPs changed! ("+PPdiff+" lost). New PP amount : "+NewPP+".");
			       					}
			       				}
			       				else osubot.sendMessage(osuUser, "Your PP did not change during last play.");
			       				if(NewRank != rank){
			       					if(NewRank>rank){
			       						rankDiff = NewRank - rank;
			       						osubot.sendMessage(osuUser, "Your rank changed! New rank : #"+NewRank+" ("+rankDiff+" lost)");
			       					}
			       					else{
			       						rankDiff = rank - NewRank;
			       						osubot.sendMessage(osuUser, "Your rank changed! New rank : #"+NewRank+" ("+rankDiff+" gained)");
			       					}
			       				}
			       				else osubot.sendMessage(osuUser, "Your rank did not change during last play.");
			       				PPs = NewPP;
			       				rank = NewRank;
			       			}
			       		}
		        	}
		        	else {
			       		shouldLoop = false;
			       		System.out.println("An error occured, check the informations in the config file are correct and you are connected to the internet.");
			      	}
			    }
				else {
					shouldLoop = false;
					System.out.println("An error occured, check the informations in the config file are correct and you are connected to the internet.");
				}
				Thread.sleep(5000);
			}
		}
			
		else System.out.println("Config file is incomplete or invalid. The bot will now stop.");
    }
}
