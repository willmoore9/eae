package com.eae.schedule.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eae.schedule.model.Publisher;
import com.eae.schedule.model.ServiceDay;
import com.eae.schedule.model.Shift;
import com.eae.schedule.repo.PublisherRepository;
import com.eae.schedule.repo.ServiceDayRepository;
import com.eae.schedule.repo.ShiftRepository;
import com.eae.schedule.ui.model.Response;

@RestController
@RequestMapping("/shifts")
public class ShiftsController {

	@Autowired
	private PublisherRepository pubisherRepo;
	
	@Autowired
	private ShiftRepository shiftRepo;
	
	@Autowired
	private ServiceDayRepository daysRepo;
	
	@RequestMapping(value="/create/{serviceDayId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> createShiftInDay(@PathVariable(value="serviceDayId") String serviceDayId, @RequestBody Shift shift) {
		Response<Shift> response = new Response<Shift>();
		
		ServiceDay day = daysRepo.findById(serviceDayId).get();

		shift.setServiceDay(day);
		shift = this.shiftRepo.save(shift);
		
		day.getShifts().add(shift);
		daysRepo.save(day);
		
		daysRepo.flush();
		shiftRepo.flush();
		response.getObjects().add(shift);
		return response;
	}
	
	
	@RequestMapping(value="/assign/{shiftId}/{publisherId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Publisher> assignPubisherToShift(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="publisherId") String publisherId) {
		Response<Publisher> response = new Response<Publisher>();
		Publisher publisher = pubisherRepo.findById(publisherId).get();
		
		Shift shift = shiftRepo.findById(shiftId).get();
		shift.getAssigned().add(publisher);
		
		shiftRepo.saveAndFlush(shift);
		
		response.addObject(publisher);
		
		return response;
	}
		
}
