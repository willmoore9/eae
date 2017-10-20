package com.eae.schedule.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eae.schedule.model.Publisher;
import com.eae.schedule.repo.PublisherRepository;
import com.eae.schedule.ui.model.Response;

@RestController
@RequestMapping("/publishers")
public class PublishersContoller {
	
	@Autowired
	private PublisherRepository publisherRepo;
	
    @RequestMapping(name="/", method=RequestMethod.GET)
    public Response<Publisher> getAll() {
    	Response<Publisher> response = new Response<Publisher>();
    	List<Publisher> periods = (List<Publisher>) this.publisherRepo.findAll();
    	response.setObjects(periods);
        return response; 
    }
    
	@RequestMapping(value="/create", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Publisher> createPublisher(@RequestBody Publisher publisher) {
		Response<Publisher> response = new Response<Publisher>();
		publisher = this.publisherRepo.save(publisher);
		response.addObject(publisher);
		return response;
	}
	
	@RequestMapping(value="/delete/{publisherId}", method=RequestMethod.DELETE)
    public Response<Object> deletePeriod(@PathVariable(value="publisherId") String publisherId) {
    	Response<Object> response = new Response<Object>();
    	this.publisherRepo.deleteById(publisherId);
    	return response;
    }
    
}
