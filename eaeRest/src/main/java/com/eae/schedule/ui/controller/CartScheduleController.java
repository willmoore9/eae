package com.eae.schedule.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eae.communication.email.EmailUtils;
import com.eae.schedule.model.CartSchedule;
import com.eae.schedule.model.PublisherAssignment;
import com.eae.schedule.model.Shift;
import com.eae.schedule.repo.CartScheduleRepository;
import com.eae.schedule.repo.PublisherAssignmentRepository;
import com.eae.schedule.repo.ShiftRepository;
import com.eae.schedule.ui.model.Response;

@RestController
@RequestMapping("/cartSchedule")
public class CartScheduleController {
	
	@Autowired
	private CartScheduleRepository cartScheduleRepo;
	
	@Autowired
	private PublisherAssignmentRepository publisherAssignmentRepo;
	
	@Autowired
	private ShiftRepository shiftRepo;
	
	
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
    	
    	List<PublisherAssignment> assignedToSchedule = publisherAssignmentRepo.findPublisherAssignmentByScheduleGuid(cartScheduleId);
    	for(PublisherAssignment assigned : assignedToSchedule) {
    		assigned.setSchedule(null);
    		publisherAssignmentRepo.save(assigned);
    	}
    	
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
    
    
    @RequestMapping(value="/sendShiftInvite/shift/{shiftId}/schedule/{scheduleId}", consumes={"application/json"}, produces={"application/json"}, method=RequestMethod.GET)
    public Response<Object> sendInvites(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="scheduleId") String scheduleId) {
    	Response<Object> response = new Response<Object>();
    	Shift shift = shiftRepo.findById(shiftId).get();
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yyyy");
    	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    	SimpleDateFormat summaryFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
    	StringBuffer bufferSubject = new StringBuffer();

    	bufferSubject.append("EAE: ");
    	bufferSubject.append(" ");
    	bufferSubject.append(summaryFormat.format(shift.getStarts()));
    	bufferSubject.append(" - ");
    	bufferSubject.append(timeFormat.format(shift.getEnds()));
    	
    	StringBuffer bufferBody = new StringBuffer();
    	bufferBody.append(dateFormat.format(shift.getStarts()));
    	bufferBody.append("\n\r");
    	bufferBody.append("From/Von ").append(timeFormat.format(shift.getStarts())).append(" ").append("to/bis ").append(timeFormat.format(shift.getEnds()));
    	List<PublisherAssignment> assignments = publisherAssignmentRepo.findPublisherAssignmentByScheduleGuidAndShiftGuid(scheduleId, shiftId);
    	if(assignments.size() == 0) {
    		return response;
    	}
    	
    	List<String> emailList = new ArrayList<String>();
    	List<PublisherAssignment> assignmentsToUpdate  = new ArrayList<PublisherAssignment>();
    	for(PublisherAssignment assignment : assignments) {
    		if(!assignment.getIsInvitationSent()) {
    			String email = assignment.getPublisher().getEmail();
    			if(email != null && email.length() > 0 && email.contains("@")) {
    				emailList.add(email);	
    				assignment.setIsInvitationSent(true);
    				assignmentsToUpdate.add(assignment);
    			}
    			
    		}
    	}
    	if(emailList.size() > 0) {
    		EmailUtils.sendBulkInvite(bufferSubject.toString(),
    				bufferBody.toString(), 
    				"EAE", 
    				assignments.get(0).getSchedule().getCart().getAddress(), 
    				shift.getStarts(),
    				shift.getEnds(),
    				emailList);	
    	}
		
		this.publisherAssignmentRepo.saveAll(assignmentsToUpdate);
		return response; 
    }
    
    @RequestMapping(value="/sendShiftInvites/schedule/{scheduleId}", consumes={"application/json"}, produces={"application/json"}, method=RequestMethod.POST)
    public Response sendBulkEmailInvites(@PathVariable(value="scheduleId") String scheduleId, @RequestBody Map shifts) {
    	
    	System.out.println("scheduleId " + scheduleId);
    	System.out.println("shifts.length " + shifts.size());
    	return new Response<>();
    }
    
    Response<CartSchedule> response = new Response<CartSchedule>();
}
