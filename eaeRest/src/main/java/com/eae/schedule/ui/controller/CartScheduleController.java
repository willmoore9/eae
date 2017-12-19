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
    public Response<CartSchedule> getAll() {
    	Response<CartSchedule> response = new Response<CartSchedule>();
    	List<CartSchedule> cartPoints = (List<CartSchedule>) cartScheduleRepo.findAll();
    	response.setObjects(cartPoints);
    	response.setSuccessful(true);
		return response; 
    }

    @RequestMapping(value="/read/{cartScheduleId}", consumes={"application/json"}, produces={"application/json"}, method=RequestMethod.GET)
    public CartSchedule getCartSchedule(@PathVariable(value="cartScheduleId") String cartScheduleId) {
    	CartSchedule schedue = cartScheduleRepo.findById(cartScheduleId).get();
		return schedue; 
    }
    
	@RequestMapping(value="/create", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<CartSchedule> createCartSchedule(@RequestBody CartSchedule cartPoint) {
		Response<CartSchedule> response = new Response<CartSchedule>();
		cartScheduleRepo.save(cartPoint);
		cartPoint = cartScheduleRepo.findById(cartPoint.getGuid()).get();
		response.addObject(cartPoint);
		return response;
	}
	
	@RequestMapping(value="/delete/{cartScheduleId}", method=RequestMethod.DELETE)
    public Response<Object> deleteCartSchedule(@PathVariable(value="cartScheduleId") String cartScheduleId) {
    	Response<Object> response = new Response<Object>();
    	this.cartScheduleRepo.deleteById(cartScheduleId);
    	return response;
    }
	
    @RequestMapping(value="/share/{cartScheduleId}", consumes={"application/json"}, produces={"application/json"}, method=RequestMethod.GET)
    public CartSchedule shareCartSchedule(@PathVariable(value="cartScheduleId") String cartScheduleId) {
    	CartSchedule schedue = cartScheduleRepo.findById(cartScheduleId).get();
    	schedue.setIsShared(true);
    	cartScheduleRepo.save(schedue);
		return schedue; 
    }
    
    @RequestMapping(value="/unshare/{cartScheduleId}", consumes={"application/json"}, produces={"application/json"}, method=RequestMethod.GET)
    public CartSchedule unshareCartSchedule(@PathVariable(value="cartScheduleId") String cartScheduleId) {
    	CartSchedule schedue = cartScheduleRepo.findById(cartScheduleId).get();
    	schedue.setIsShared(false);
    	cartScheduleRepo.save(schedue);
		return schedue; 
    }
}
