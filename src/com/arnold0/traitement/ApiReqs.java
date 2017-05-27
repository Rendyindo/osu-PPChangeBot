package com.arnold0.traitement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiReqs {
	
	private String apiKey;
	

	public ApiReqs(String key){
		// On enregistre l'API key qui est passée
		apiKey = key;
	}

	public String sendGet(String name, String mode) throws Exception {
		
		// On mets en forme l'URL a apeller
		String url = "http://osu.ppy.sh/api/get_user?k="+apiKey+"&u="+name+"&m="+mode;
 
		// On fait la connexion et on retourne la réponse de l'API
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");	
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		return response.toString();

	}
	
}
