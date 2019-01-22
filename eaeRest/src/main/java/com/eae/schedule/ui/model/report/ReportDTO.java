package com.eae.schedule.ui.model.report;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eae.schedule.model.Placement;
import com.eae.schedule.model.PublicationLanguage;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class ReportDTO {

	private List<Placement> placements;
	private JSONObject json;
	
	private final String LANGUAGES = "Languages";
	private final String PUBLICATION_TYPES = "Types";
	private final String PUBLICATION_TYPE= "Type";
	private final String PUBLICATIONS = "Publications";
	
	public ReportDTO(List<Placement> placements) {
		this.placements = placements;
		json = new JSONObject();
		for(Placement placement : placements) {
			PublicationLanguage lang = placement.getLanguage();
			JSONArray langs = json.getJSONArray(LANGUAGES);
			JSONObject languageToProcess = new JSONObject(lang);
			
			if(langs == null) {
				langs = new JSONArray();
				json.put(LANGUAGES, langs);
				langs.put(languageToProcess);
				JSONArray types = new JSONArray();
				JSONObject type = new JSONObject();
				type.put(PUBLICATION_TYPE, placement.getType());
				type.put(PUBLICATIONS, new JSONArray(placement));
				types.put(type);
				languageToProcess.put(PUBLICATION_TYPES, types);
				
			}
			
			
		
			
			
		}

		
	}

	public List<Placement> getPlacements() {
		return placements;
	}

	public JSONObject getJson() {
		return json;
	}
	
	private int indexOf(String value, String property, JSONArray array) {
		
		if(array.length() == 0) {
			return -2;
		}
		int index = 0;
		for(Object obj : array) {
			JSONObject jsonObj = (JSONObject) obj;
			String arrayElementGuid = jsonObj.getString(property);
			if(arrayElementGuid.equalsIgnoreCase(value)) {
				return index;
			}
			
			index++;
		}
		
		return -1;
	}
	
}
