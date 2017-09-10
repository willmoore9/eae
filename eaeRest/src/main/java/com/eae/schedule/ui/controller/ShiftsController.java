package com.eae.schedule.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eae.schedule.model.ServiceDay;
import com.eae.schedule.model.Shift;
import com.eae.schedule.repo.ServiceDayRepository;
import com.eae.schedule.repo.ShiftRepository;
import com.eae.schedule.ui.model.Response;

@RestController
@RequestMapping("/shifts")
public class ShiftsController {

	@Autowired
	private ShiftRepository shiftRepo;
	
	@Autowired
	private ServiceDayRepository daysRepo;
	
	@RequestMapping(value="/create/{serviceDayId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> createShiftInDay(@PathVariable(name="serviceDayId", required=true) String serviceDayId, @RequestBody Shift shift) {
		Response<Shift> response = new Response<Shift>();
		
		ServiceDay day = daysRepo.findOne(serviceDayId).get();
		shift.setServiceDay(day);
		
		this.shiftRepo.save(shift);
		
		response.getObjects().add(shift);
		return response;
	}
		
}
