package com.eae.schedule.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eae.schedule.model.CartPoint;
import com.eae.schedule.repo.CartPointRepository;
import com.eae.schedule.ui.model.Response;

@RestController
@RequestMapping("/cartLocations")
public class CartLocations {
	
	@Autowired
	private CartPointRepository cartPointRepo;
	
    @RequestMapping(name="/", method=RequestMethod.GET)
    public Response<CartPoint> getLandPublisher() {
    	Response<CartPoint> response = new Response<CartPoint>();
    	List<CartPoint> cartPoints = (List<CartPoint>) cartPointRepo.findAll();
    	response.setObjects(cartPoints);
    	response.setSuccessful(true);
		return response; 
    }
    
	@RequestMapping(value="/create", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<CartPoint> createPublisher(@RequestBody CartPoint cartPoint) {
		Response<CartPoint> response = new Response<CartPoint>();
		cartPointRepo.save(cartPoint);
		response.addObject(cartPoint);
		return response;
	}
	
	@RequestMapping(value="/delete/{cartPointId}", method=RequestMethod.DELETE)
    public Response<Object> deletePeriod(@PathVariable(value="cartPointId") String cartPointId) {
    	Response<Object> response = new Response<Object>();
    	this.cartPointRepo.deleteById(cartPointId);
    	return response;
    }
    
}
