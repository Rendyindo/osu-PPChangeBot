package com.rendyak.traitement;


import org.json.JSONArray;
import org.json.JSONObject;

public class TraitementJson {
	private static double pp;
	private static int rank;
	private static double mapPP;
	private static String ScoreJSON;
	private static String MapInfo;
	private static String artist;
	private static String title;
	private static String strmapid;
	private static String diffname;
	
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
			
			Thread.sleep(1000);
			
			// On extrait un JsonArray depuis le string retourné par l'API
			JSONArray jsonArr2 = new JSONArray(sJSON2);
			// On récupère le premier Json objet ans cet array
			JSONObject json2 = jsonArr2.getJSONObject(0);
			// On récupère toutes les données dont on a besoin
			strmapid = json2.getString("beatmap_id");
			ScoreJSON = ApiReqs.sendGetScores(osuUser, mode, strmapid);
			JSONArray jsonArr3 = new JSONArray(ScoreJSON);
			JSONObject json3 = jsonArr3.getJSONObject(0);
			String strmapPP = json3.getString("pp");
			
			Thread.sleep(1000);
			
			MapInfo = ApiReqs.sendGetBeatmapInfo(strmapid);
			JSONArray jsonArr4 = new JSONArray(MapInfo);
			JSONObject json4 = jsonArr4.getJSONObject(0);
			artist = json4.getString("artist");
			title = json4.getString("title");
			diffname = json4.getString("version");
			
			if(strmapPP!=null)
			{
				mapPP = Double.parseDouble(strmapPP);
			}
			
			if(pp!=0 && rank != 0 && artist!=null && title!=null)
			{
				success = true;
			}
			else success = false;
			
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
	
	public String getmapartist()
	{
		return artist;
	}
	
	public String getmaptitle()
	{
		return title;
	}
	
	public String getmapid()
	{
		return strmapid;
	}
	
	public String getdiffname()
	{
		return diffname;
	}
}
