package com.eae.schedule.ui.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eae.schedule.model.Placement;
import com.eae.schedule.model.PublicationLanguage;
import com.eae.schedule.model.ShiftReport;
import com.eae.schedule.model.ShiftReportItem;
import com.eae.schedule.model.ShiftReportKey;
import com.eae.schedule.repo.PlacementsRepository;
import com.eae.schedule.repo.PublicationLangsRepository;
import com.eae.schedule.repo.ShiftReportItemRepository;
import com.eae.schedule.repo.ShiftReportRepository;
import com.eae.schedule.ui.model.Response;
import com.eae.schedule.ui.model.report.ReportDTO;

@RestController
@RequestMapping("/shiftReport")
public class ShiftReportController {

	@Autowired
	private ShiftReportRepository shiftReportRepo;
	
	@Autowired
	private PlacementsRepository placementsRepo;
	
	@Autowired
	private ShiftReportItemRepository reporItem;

	@Autowired
	private PublicationLangsRepository langRepo;
	
	@RequestMapping(name="/", method=RequestMethod.GET)
	public Response<ShiftReport> getAll() {
		Response<ShiftReport> response = new Response<ShiftReport>();
		List<ShiftReport> placements = shiftReportRepo.findAll();
		response.setSuccessful(true);
		response.setObjects(placements);
		return response;
	}

	@RequestMapping(value="/report/{scheduleId}/{shiftId}/placenent/{placementId}/count/{count}", method=RequestMethod.POST)
	public Response<Object> addPlacement(@PathVariable(value="scheduleId") String scheduleId, @PathVariable(value="shiftId") String shiftId,
										@PathVariable(value="placementId") String placementId, @PathVariable(value="count") String count) {
		ShiftReportKey key = new ShiftReportKey(shiftId, scheduleId);
		
		Response<Object> response = new Response<Object>();
    	ShiftReport report = shiftReportRepo.findById(key).get();
    	ShiftReportItem item = report.findReportItem(placementId);
    	
    	if(item != null) {
        	item.setCount(Integer.parseInt(count));
    		this.reporItem.saveAndFlush(item);
    	} else {
    		item = new ShiftReportItem();
    		item.setPlacement(new Placement(placementId));
        	item.setCount(Integer.parseInt(count));
        	item.setReport(report);
        	report.getItems().add(item);
        	this.shiftReportRepo.saveAndFlush(report);
    	}
    	
    	response.setSuccessful(true);
    	return response;		
	}
	
	@RequestMapping(value="/delete/{scheduleId}/{shiftId}", method=RequestMethod.DELETE)
    public Response<Object> deletePeriod(@PathVariable(value="scheduleId") String scheduleId, @PathVariable(value="shiftId") String shiftId) {
		ShiftReportKey key = new ShiftReportKey(shiftId, scheduleId);
		Response<Object> response = new Response<Object>();
    	this.shiftReportRepo.deleteById(key);
    	response.setSuccessful(true);
    	return response;
    }
	
	@RequestMapping(value="/report/{scheduleId}/{shiftId}", method=RequestMethod.GET)
	public Response<ReportDTO> getCurrentStatusReportDTO(@PathVariable(value="scheduleId") String scheduleId, 
														 @PathVariable(value="shiftId") String shiftId,
														 @RequestParam(value = "lang", required = false) String lang
			) {
	
		ShiftReportKey key = new ShiftReportKey(shiftId, scheduleId);
    	ShiftReport report = null;
    	
    	List<Placement> allPlacements = this.placementsRepo.findAll();
    	List<PublicationLanguage> allLanguages = this.langRepo.findAll();
    	
    	
		Optional<ShiftReport> alreadyExist = shiftReportRepo.findById(key);
		List<ShiftReportItem> reportItems = null;
		if(alreadyExist.isPresent()) {
			report = alreadyExist.get();
			reportItems = report.getItems();
		} else {
			report = new ShiftReport();
			report.setKey(key);
			report = shiftReportRepo.saveAndFlush(report);	
		}
    	
		ReportDTO reportDto = new ReportDTO(report, allPlacements, allLanguages, lang);
		Response<ReportDTO> response = new Response<ReportDTO>();
		response.addObject(reportDto);
    	response.setSuccessful(true);
    	return response;
	}
	
	
}
