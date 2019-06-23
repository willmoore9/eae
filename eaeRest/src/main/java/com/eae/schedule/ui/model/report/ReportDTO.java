package com.eae.schedule.ui.model.report;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.eae.schedule.model.Constants;
import com.eae.schedule.model.Placement;
import com.eae.schedule.model.PlacementTitle;
import com.eae.schedule.model.PublicationLanguage;
import com.eae.schedule.model.ShiftReport;
import com.eae.schedule.model.ShiftReportItem;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class ReportDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ShiftReport report;
	private BaseDTO root;
	private String lang;
	
	public ReportDTO(ShiftReport report, List<Placement> allPlacements, List<PublicationLanguage> languages, String lang) {
		this.report = report;
		this.root = new BaseDTO(); 
		this.lang = lang;
		languages.forEach((language) -> {
			String nodeLang = language.getGuid();
			BaseDTO langChild = new  BaseDTO();
			langChild.setDisplayCode(language.getWtCode());
			langChild.setType(Constants.LANG);
			
			buildLeafStructure(langChild, allPlacements, nodeLang, Constants.PUBLICATION_TYPE_BROCHURE);
			buildLeafStructure(langChild, allPlacements, nodeLang, Constants.PUBLICATION_TYPE_VIDEO);
			buildLeafStructure(langChild, allPlacements, nodeLang, Constants.PUBLICATION_TYPE_TRACT);
			
			root.addChild(langChild);
		});		
		
	}


	private void buildLeafStructure(BaseDTO parent, List<Placement> allPlacements, String langGuid, String type) {
		BaseDTO branch = new BaseDTO();
		branch.setDisplayCode(type);
		branch.setType(type);
		parent.addChild(branch);

		allPlacements.forEach((placement) -> {
			if(placement.getType() != null 
					&& placement.getType().equalsIgnoreCase(type) 
					&& langGuid.equalsIgnoreCase(placement.getLanguage().getGuid()) ) {
				BaseDTO leaf = new BaseDTO();
				leaf.setGuid(placement.getGuid());
				List<PlacementTitle> titles = placement.getTitles();
				String display = placement.getEnglishName();
				if(this.lang != null) {
					for(PlacementTitle title : titles) {
						if(title.getLanguage().getIsoCode().equalsIgnoreCase(this.lang)) {
							display = title.getTitle();
						}
					}
				}
				
				leaf.setDisplayCode(display);
				leaf.setKey(this.report.getKey());
				leaf.setCount(this.getCountOfPlacements(placement));
				branch.addChild(leaf);
			}
		});
	}

	private int getCountOfPlacements(Placement placememt) {
		List<ShiftReportItem> items = this.report.getItems();
		
		for(ShiftReportItem reportItem : items) {
			if(reportItem.getPlacement().getGuid().equalsIgnoreCase(placememt.getGuid())) {
				return reportItem.getCount();
			}
		}
		
		return 0;
	}
	
	
	public ShiftReport getReport() {
		return report;
	}

	public void setReport(ShiftReport report) {
		this.report = report;
	}

	public BaseDTO getRoot() {
		return root;
	}


	public void setRoot(BaseDTO root) {
		this.root = root;
	}
	
	
}
