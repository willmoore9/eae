package com.eae.schedule.ui.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eae.schedule.model.CartDelivery;
import com.eae.schedule.model.CartDeliveryKey;
import com.eae.schedule.model.CartSchedule;
import com.eae.schedule.model.Publisher;
import com.eae.schedule.model.PublisherAssignment;
import com.eae.schedule.model.ServiceDay;
import com.eae.schedule.model.Shift;
import com.eae.schedule.repo.CartDeliveryRepository;
import com.eae.schedule.repo.CartScheduleRepository;
import com.eae.schedule.repo.PublisherAssignmentRepository;
import com.eae.schedule.repo.PublisherRepository;
import com.eae.schedule.repo.ServiceDayRepository;
import com.eae.schedule.repo.ShiftRepository;
import com.eae.schedule.ui.exception.PublisherAlreadyBookedException;
import com.eae.schedule.ui.model.Response;
import com.eae.schedule.ui.model.StatusCode;

@RestController
@RequestMapping("/shifts")
public class ShiftsController {

	@Autowired
	private PublisherRepository pubisherRepo;
	
	@Autowired
	private ShiftRepository shiftRepo;
	
	@Autowired
	private ServiceDayRepository daysRepo;
	
	@Autowired
	private CartScheduleRepository cartScheduleRepo;
	
	@Autowired
	private PublisherAssignmentRepository publisherAssignmentRepo;
	
	@Autowired
	private CartDeliveryRepository cartDelivertRepo;
	
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
	
	
	@RequestMapping(value="/assign/{shiftId}/schedule/{scheduleId}/publisher/{publisherId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> assignPubisherToShift(@PathVariable(value="shiftId") String shiftId, 
			@PathVariable(value="scheduleId") String scheduleId,
			@PathVariable(value="publisherId") String publisherId) throws PublisherAlreadyBookedException {
		Response<Shift> response = new Response<Shift>();
		
		Shift shift = shiftRepo.findById(shiftId).get();
		
		CartSchedule schedule = cartScheduleRepo.findById(scheduleId).get();

		List<PublisherAssignment> assignments = shift.getAssignments();
		
		
		boolean assignUnrequested = true;
		for(PublisherAssignment assignment : assignments) {
			if(assignment.getPublisher().getGuid().equals(publisherId)) {
				
				if(assignment.getSchedule() != null) {
					response.setSuccessful(false);
					response.setStatus(StatusCode.BOOK1.toString());
					throw new PublisherAlreadyBookedException();
				}
				
				assignment.setSchedule(schedule);
				assignUnrequested = false;
				publisherAssignmentRepo.saveAndFlush(assignment);
			}
		}
		
		if(assignUnrequested) {
			PublisherAssignment assignment = new PublisherAssignment();
			assignment.setSchedule(schedule);
			assignment.setPublisher(new Publisher(publisherId));
			assignment.setShift(shift);
			shift.getAssignments().add(assignment);
		}
		
		
		
		shiftRepo.saveAndFlush(shift);
		
		shift = shiftRepo.findById(shift.getGuid()).get();
		
		response.addObject(shift);
		
		return response;
	}
	
	@RequestMapping(value="/unassign/{shiftId}/schedule/{scheduleId}/publisher/{publisherId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> unAssignPubisherToShift(@PathVariable(value="shiftId") String shiftId,
			@PathVariable(value="scheduleId") String scheduleId,
			@PathVariable(value="publisherId") String publisherId) {
		Response<Shift> response = new Response<Shift>();

		Shift shift = shiftRepo.findById(shiftId).get();
		List<PublisherAssignment> assignedPublisher = shift.getAssignments();
		
		for(PublisherAssignment assigmentToCancel : assignedPublisher) {
			if(assigmentToCancel.getPublisher().getGuid().equals(publisherId) && assigmentToCancel.getSchedule().getGuid().equals(scheduleId)) {
				assigmentToCancel.setSchedule(null);
				publisherAssignmentRepo.save(assigmentToCancel);
				if(!assigmentToCancel.getIsSelfAssigned()) {
					assignedPublisher.remove(assigmentToCancel);
				}
				response.setSuccessful(true);
				response.addObject(shift);
				break;
			}
		}
		
		
		shiftRepo.saveAndFlush(shift);
		
		response.addObject(shift);
		
		return response;
	}
	
	@RequestMapping(value="/removeAssignRequest/{shiftId}/{publisherId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> removeAssignRequest(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="publisherId") String publisherId) {
		Response<Shift> response = new Response<Shift>();

		Shift shift = shiftRepo.findById(shiftId).get();
		List<PublisherAssignment> assignedPublisher = shift.getAssignments();
		
		for(PublisherAssignment assigmentToCancel : assignedPublisher) {
			if(assigmentToCancel.getPublisher().getGuid().equals(publisherId)) {
				response.setSuccessful(true);
				response.addObject(shift);
				assignedPublisher.remove(assigmentToCancel);
				break;
			}
		}
		
		shiftRepo.saveAndFlush(shift);
		return response;
	}
	
	@RequestMapping(value="/addAssignRequest/{shiftId}/{publisherId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> requestAssignmentPubisherToShift(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="publisherId") String publisherId) {
		Response<Shift> response = new Response<Shift>();
		Publisher publisher = pubisherRepo.findById(publisherId).get();
		
		Shift shift = shiftRepo.findById(shiftId).get();
		PublisherAssignment assignement = new PublisherAssignment();
		assignement.setIsSelfAssigned(true);
		assignement.setShift(shift);
		assignement.setPublisher(publisher);	
		shift.getAssignments().add(assignement);
		
		shiftRepo.saveAndFlush(shift);
		
		response.addObject(shift);
		
		return response;
	}
	
	
	@RequestMapping(value="/assignableToShift/{shiftId}", method=RequestMethod.GET, produces={"application/json"})
	public Response<Publisher> requestAssignmentPubisherToShift(@PathVariable(value="shiftId") String shiftId) {
		Response<Publisher> response = new Response<Publisher>();
		
		Shift shift = shiftRepo.findById(shiftId).get();
		List<PublisherAssignment> assignements = shift.getAssignments();
		for(PublisherAssignment assignment : assignements) {
			if(assignment.getSchedule() == null){
				response.addObject(assignment.getPublisher());
			}
		}
		
		return response;
	}
	
	@RequestMapping(value="/assignShiftLeader/{shiftId}/assignment/{assignmentId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> assignToAsLeader(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="assignmentId") String assignmentId)  {
		Response<Shift> response = new Response<Shift>();
		PublisherAssignment assignement = publisherAssignmentRepo.findById(assignmentId).get();
		assignement.setIsShiftLeader(true);
		publisherAssignmentRepo.save(assignement);
		
		Shift shift = shiftRepo.findById(shiftId).get();
		response.addObject(shift);
		return response;
	}
	
	@RequestMapping(value="/unassignShiftLeader/{shiftId}/assignment/{assignmentId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> unassignToAsLeader(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="assignmentId") String assignmentId)  {
		Response<Shift> response = new Response<Shift>();
		
		PublisherAssignment assignemnt = publisherAssignmentRepo.findById(assignmentId).get();
		assignemnt.setIsShiftLeader(false);
		publisherAssignmentRepo.save(assignemnt);
		
		Shift shift = shiftRepo.findById(shiftId).get();
		response.addObject(shift);
		return response;
	}
	
	@RequestMapping(value="/assignTrolleyCarrier/{shiftId}/assignment/{assignmentId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> assignTrolleyCarrier(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="assignmentId") String assignmentId)  {
		Response<Shift> response = new Response<Shift>();
		
		PublisherAssignment assignemnt = publisherAssignmentRepo.findById(assignmentId).get();
		assignemnt.setIsCartCarrier(true);
		publisherAssignmentRepo.save(assignemnt);
		
		Shift shift = shiftRepo.findById(shiftId).get();
		response.addObject(shift);
		return response;
	}
	
	@RequestMapping(value="/unassignTrolleyCarrier/{shiftId}/assignment/{assignmentId}", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	public Response<Shift> unassignTrolleyCarrier(@PathVariable(value="shiftId") String shiftId, @PathVariable(value="assignmentId") String assignmentId)  {
		Response<Shift> response = new Response<Shift>();
		
		PublisherAssignment assignemnt = publisherAssignmentRepo.findById(assignmentId).get();
		assignemnt.setIsCartCarrier(false);
		publisherAssignmentRepo.save(assignemnt);
		
		Shift shift = shiftRepo.findById(shiftId).get();
		response.addObject(shift);
		return response;
	}
	
	@RequestMapping(value="/delete/{shiftId}", method=RequestMethod.DELETE)
    public Response<Object> deleteShift(@PathVariable(value="shiftId") String shiftId) {
    	Response<Object> response = new Response<Object>();
    	Shift shift = this.shiftRepo.findById(shiftId).get();
    	
    	ServiceDay day = shift.getServiceDay();
    	day.getShifts().remove(shift);
    	this.daysRepo.save(day);
    	
    	this.shiftRepo.deleteById(shiftId);
    	return response;
    }
	
	@RequestMapping(value="/deliverAfterDay/{dayId}/schedule/{scheduleId}/comment", method=RequestMethod.POST)
    public Response<ServiceDay> deliverAfterShift(@PathVariable(value="dayId") String dayId, 
    		@PathVariable(value="scheduleId") String scheduleId,
    		@RequestBody String comment) {
    	Response<ServiceDay> response = new Response<ServiceDay>();
    	Optional<CartSchedule> cartScheduleOpt = this.cartScheduleRepo.findById(scheduleId);
    	
    	Optional<ServiceDay> dayOpt = this.daysRepo.findById(dayId);    	
    	if(! (cartScheduleOpt.isPresent() || dayOpt.isPresent())) {
    		response.setStatus("404");
    		response.setSuccessful(false);
    		return response;
    	}
    	ServiceDay day = dayOpt.get();
    	CartSchedule cartSchedule = cartScheduleOpt.get();
    	CartDelivery deliverTo = new CartDelivery();
    	CartDeliveryKey deliverKey = new CartDeliveryKey(day.getGuid(), cartSchedule.getGuid());
    	deliverTo.setLocation(comment);
    	deliverTo.setKey(deliverKey);
    	deliverTo.setServiceDay(day);
    	deliverTo.setSchedule(cartSchedule);
    	
    	this.cartDelivertRepo.save(deliverTo);    	
    	
    	day.getDeliverTo().add(deliverTo);
    	this.daysRepo.save(day);
    	return response;
    }
}
