package com.eae.schedule.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eae.schedule.model.Placement;
import com.eae.schedule.repo.PlacementsRepository;
import com.eae.schedule.ui.model.Response;

@RestController
@RequestMapping("/placements")
public class PlacementaController {

	@Autowired
	private PlacementsRepository placementsRepo;
	
	@RequestMapping(name="/", method=RequestMethod.GET)
	public Response<Placement> getAll() {
		Response<Placement> response = new Response<Placement>();
		List<Placement> placements = placementsRepo.findAll();
		response.setSuccessful(true);
		response.setObjects(placements);
		return response;
	}

	@RequestMapping(value="/create", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Placement> getCreate(@RequestBody Placement placement) {
		Response<Placement> response = new Response<Placement>();
		placementsRepo.saveAndFlush(placement);
		response.addObject(placement);
		response.setSuccessful(true);
		return response;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public Response<Object> deletePeriod(@PathVariable(value="id") String id) {
    	Response<Object> response = new Response<Object>();
    	this.placementsRepo.deleteById(id);
    	response.setSuccessful(true);
    	return response;
    }
	
	
}
