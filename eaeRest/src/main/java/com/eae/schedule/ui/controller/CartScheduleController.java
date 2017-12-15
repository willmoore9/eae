package com.eae.schedule.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eae.schedule.model.CartSchedule;
import com.eae.schedule.repo.CartScheduleRepository;
import com.eae.schedule.ui.model.Response;

@RestController
@RequestMapping("/cartSchedule")
public class CartScheduleController {
	
	@Autowired
	private CartScheduleRepository cartScheduleRepo;
	
    @RequestMapping(name="/", method=RequestMethod.GET)
    public Response<CartSchedule> getLandPublisher() {
    	Response<CartSchedule> response = new Response<CartSchedule>();
    	List<CartSchedule> cartPoints = (List<CartSchedule>) cartScheduleRepo.findAll();
    	response.setObjects(cartPoints);
    	response.setSuccessful(true);
		return response; 
    }
    
	@RequestMapping(value="/create", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<CartSchedule> createPublisher(@RequestBody CartSchedule cartPoint) {
		Response<CartSchedule> response = new Response<CartSchedule>();
		cartScheduleRepo.save(cartPoint);
		cartPoint = cartScheduleRepo.findById(cartPoint.getGuid()).get();
		response.addObject(cartPoint);
		return response;
	}
	
	@RequestMapping(value="/delete/{cartScheduleId}", method=RequestMethod.DELETE)
    public Response<Object> deletePeriod(@PathVariable(value="cartScheduleId") String cartScheduleId) {
    	Response<Object> response = new Response<Object>();
    	this.cartScheduleRepo.deleteById(cartScheduleId);
    	return response;
    }
    
}
