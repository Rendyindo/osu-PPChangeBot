package com.rendyak.traitement;


import org.json.JSONArray;
import org.json.JSONObject;

public class TraitementJson {
	private static double pp;
	private static int rank;
	private static double mapPP;
	private static String LMAO;
	
	public boolean decode (String sJSON, String sJSON2, String osuUser, String mode)
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
			
			// On extrait un JsonArray depuis le string retourné par l'API
			JSONArray jsonArr2 = new JSONArray(sJSON2);
			// On récupère le premier Json objet ans cet array
			JSONObject json2 = jsonArr2.getJSONObject(0);
			// On récupère toutes les données dont on a besoin
			String strmapid = json2.getString("beatmap_id");
			LMAO = ApiReqs.sendGetScores(osuUser, mode, strmapid);
			JSONArray jsonArr3 = new JSONArray(LMAO);
			JSONObject json3 = jsonArr3.getJSONObject(0);
			String strmapPP = json3.getString("pp");
			
			if(strmapPP!=null)
			{
				mapPP = Double.parseDouble(strmapPP);
			}
			
		}
		catch(Exception e)
		{
			success = true;
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

	public double getmapPP()
	{
		return mapPP;
	}
}
