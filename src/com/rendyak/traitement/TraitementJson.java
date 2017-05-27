package com.rendyak.traitement;


import org.json.JSONArray;
import org.json.JSONObject;

public class TraitementJson {
	private static double pp;
	private static int rank;
	
	public boolean decode (String sJSON)
	{
		boolean success;
		try{
			// On extrait un JsonArray depuis le string retourné par l'API
			JSONArray jsonArr = new JSONArray(sJSON);
			// On récupère le premier Json objet ans cet array
			JSONObject json = jsonArr.getJSONObject(0);
			// On récupère toutes les données dont on a besoin
			String strPP = json.getString("pp_raw");
			pp = Double.parseDouble(strPP);
			rank = json.getInt("pp_rank");
			
			if(pp!=0 && rank != 0)
			{
				success = true;
			}
			else success = false;
		
		}
		catch(Exception e)
		{
			success = false;
		}
		// Sinon on retourne notre String.
		return success;
	}
	
	public double getPP()
	{
		return pp;
	}
	
	public int getRank()
	{
		return rank;
	}
}
