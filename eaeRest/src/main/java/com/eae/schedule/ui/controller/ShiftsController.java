package com.eae.schedule.ui.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eae.schedule.model.Publisher;
import com.eae.schedule.model.PublisherAssignment;
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
	
	
//	@RequestMapping(value="/assign/{shiftId}/{publisherId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
//	public Response<Publisher> assignPubisherToShift(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="publisherId") String publisherId) {
//		Response<Publisher> response = new Response<Publisher>();
//		Publisher publisher = pubisherRepo.findById(publisherId).get();
//		
//		Shift shift = shiftRepo.findById(shiftId).get();
//		shift.getAssigned().add(publisher);
//		
//		shiftRepo.saveAndFlush(shift);
//		
//		response.addObject(publisher);
//		
//		return response;
//	}
	
//	@RequestMapping(value="/unassign/{shiftId}/{publisherId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
//	public Response<Shift> unAssignPubisherToShift(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="publisherId") String publisherId) {
//		Response<Shift> response = new Response<Shift>();
//
//		Shift shift = shiftRepo.findById(shiftId).get();
//		List<Publisher> assignedPublisher = shift.getAssigned();
//		
//		for(Publisher publisherToUnassign : assignedPublisher) {
//			if(publisherToUnassign.getGuid().equals(publisherId)) {
//				response.setSuccessful(true);
//				response.addObject(shift);
//				assignedPublisher.remove(publisherToUnassign);
//				break;
//			}
//		}
//		
//		shiftRepo.saveAndFlush(shift);
//		
//		response.addObject(shift);
//		
//		return response;
//	}
	
	@RequestMapping(value="/requestAssign/{shiftId}/{publisherId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Publisher> requestAssignmentPubisherToShift(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="publisherId") String publisherId) {
		Response<Publisher> response = new Response<Publisher>();
		Publisher publisher = pubisherRepo.findById(publisherId).get();
		
		Shift shift = shiftRepo.findById(shiftId).get();
		PublisherAssignment assignement = new PublisherAssignment();
		assignement.setPublisher(publisher);	
		shift.getAssignments().add(assignement);
//		shift.getAssignable().add(publisher);
		
		shiftRepo.saveAndFlush(shift);
		
		response.addObject(publisher);
		
		return response;
	}
	
	
//	@RequestMapping(value="/assignableToShift/{shiftId}", method=RequestMethod.GET, produces={"application/json"})
//	public Response<Publisher> requestAssignmentPubisherToShift(@PathVariable(value="shiftId") String shiftId) {
//		Response<Publisher> response = new Response<Publisher>();
//		
//		Shift shift = shiftRepo.findById(shiftId).get();
//		List<Publisher> assignedPublishers = shift.getAssigned();
//		List<Publisher> assignablePublishers = shift.getAssignable();
//		
//		for(Publisher assignable : assignablePublishers) {
//			boolean isAlreadyAssigned = false;
//			
//			for(Publisher assigned : assignedPublishers) {
//				if(assignable.getGuid().equals(assigned.getGuid())) {
//					isAlreadyAssigned = true;
//				}
//			}
//			
//			if(!isAlreadyAssigned) {
//				response.addObject(assignable);
//			}
//		}
//		
//		return response;
//	}
	
	@RequestMapping(value="/assignShiftLeader/{shiftId}/{publisherId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> assignToAsLeader(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="publisherId") String publisherId)  {
		Response<Shift> response = new Response<Shift>();
		
		Publisher publisher = pubisherRepo.findById(publisherId).get();
		Shift shift = shiftRepo.findById(shiftId).get();
//		shift.setShiftLeader(publisher);
		shiftRepo.saveAndFlush(shift);
		
		response.addObject(shift);
		
		return response;
	}
	
	@RequestMapping(value="/unassignShiftLeader/{shiftId}/{publisherId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> unassignToAsLeader(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="publisherId") String publisherId)  {
		Response<Shift> response = new Response<Shift>();
		
		Shift shift = shiftRepo.findById(shiftId).get();
//		shift.setShiftLeader(null);
		shiftRepo.saveAndFlush(shift);
		
		response.addObject(shift);
		
		return response;
	}
	
	@RequestMapping(value="/assignTrolleyCarrier/{shiftId}/{publisherId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> assignTrolleyCarrier(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="publisherId") String publisherId)  {
		Response<Shift> response = new Response<Shift>();
		
		Publisher publisher = pubisherRepo.findById(publisherId).get();
		Shift shift = shiftRepo.findById(shiftId).get();
//		shift.setTrolleyCarrier(publisher);
		shiftRepo.saveAndFlush(shift);
		
		response.addObject(shift);
		
		return response;
	}
	
	@RequestMapping(value="/unassignTrolleyCarrier/{shiftId}/{publisherId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> unassignTrolleyCarrier(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="publisherId") String publisherId)  {
		Response<Shift> response = new Response<Shift>();
		
		Shift shift = shiftRepo.findById(shiftId).get();
//		shift.setTrolleyCarrier(null);
		shiftRepo.saveAndFlush(shift);
		
		response.addObject(shift);
		
		return response;
	}
}
