package com.eae.schedule.ui.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eae.schedule.model.CartDelivery;
import com.eae.schedule.model.CartSchedule;
import com.eae.schedule.model.PublisherAssignment;
import com.eae.schedule.model.ServiceDay;
import com.eae.schedule.model.ServicePeriod;
import com.eae.schedule.model.Shift;
import com.eae.schedule.repo.ServiceDayRepository;
import com.eae.schedule.repo.ServicePeriodRepository;
import com.eae.schedule.ui.model.Response;
import com.eae.schedule.ui.model.ServiceWeek;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/periods")
public class SerivePeriodsController {
	Logger log = Logger.getLogger(SerivePeriodsController.class.getName());
	
	@Autowired
	private ServicePeriodRepository periodRepo;

	@Autowired
	private ServiceDayRepository daysRepo;

    @RequestMapping(name="/", method=RequestMethod.GET)
    public Response<ServicePeriod> getAll() {
    	Response<ServicePeriod> response = new Response<ServicePeriod>();
    	Sort sortByFromDate = new Sort(Sort.Direction.ASC, "starts");
    	List<ServicePeriod> periods = (List<ServicePeriod>) this.periodRepo.findAll(sortByFromDate);
    	
    	for(ServicePeriod period : periods) {
    		period.setServiceDays(null);
    	}
    	
    	response.setObjects(periods);
        return response; 
    }
    
    @RequestMapping(path="/create", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
    public Response<ServicePeriod> save(@RequestBody ServicePeriod period) {
    	Response<ServicePeriod> response = new Response<ServicePeriod>();
    	Integer zoneOffset = period.getZoneOffset();
    	Integer shiftStartHours = Integer.parseInt(period.getFirstShiftStart().split(":")[0]);
    	Integer shiftStartMinutes = Integer.parseInt(period.getFirstShiftStart().split(":")[1]);
    	
    	period = this.periodRepo.save(period);

    	Calendar from = Calendar.getInstance();
    	from.set(Calendar.ZONE_OFFSET, zoneOffset);
    	from.setTime(period.getStarts());

    	Calendar to = Calendar.getInstance();
    	to.set(Calendar.ZONE_OFFSET, zoneOffset);
    	to.setTime(period.getEnds());
    	log.log(Level.DEBUG, "create period");
    	if(from.after(to)) {
    		response.setStatus("500");
    		response.setSuccessful(false);
    	}
    	
    	Calendar serviceDay = (Calendar) from.clone();
    	List<ServiceDay> serviceDays = new ArrayList<ServiceDay>();
    	
    	
    	while(serviceDay.before(to) || serviceDay.equals(to)) {
    		this.prepareServiceDay(period, serviceDays, serviceDay.getTime());
    		serviceDay.add(Calendar.DAY_OF_MONTH, 1);
    	}

    	
    	for(ServiceDay day : serviceDays) {

    		Calendar shiftDay = Calendar.getInstance();
			shiftDay.setTime(day.getDate());
			shiftDay.set(Calendar.HOUR_OF_DAY, shiftStartHours);
			shiftDay.set(Calendar.MINUTE, shiftStartMinutes);

			for(int i=1; i <= period.getNumberOfShifts(); i++) {
    			Shift shift = new Shift();
    			shiftDay.set(Calendar.HOUR_OF_DAY, shiftDay.get(Calendar.HOUR_OF_DAY));
    			
    			shift.setStarts(shiftDay.getTime());
    			
    			shiftDay.set(Calendar.HOUR_OF_DAY, shiftDay.get(Calendar.HOUR_OF_DAY) + 2);
    			shift.setEnds(shiftDay.getTime());
    			
    			shift.setServiceDay(day);
    			day.getShifts().add(shift);
			}

    		period.getServiceDays().add(day);
    	}
    	
    	this.periodRepo.save(period);
    	return response;
    }
    
    private void prepareServiceDay (ServicePeriod period, List<ServiceDay> serviceDays, Date date) {
    	ServiceDay day = new ServiceDay();
    	day.setPeriod(period);
    	day.setDate(date);
    	serviceDays.add(day);
    }
    
    @RequestMapping(path="/{periodId}/weeks", method=RequestMethod.GET)
    public Response<ServiceWeek> loadServiceWeeks(@PathVariable(name="periodId", required=true) String periodId) {
    	
    	Response<ServiceWeek> response = new Response<ServiceWeek>();

    	ServicePeriod period = this.periodRepo.findById(periodId).get();

    	List<ServiceDay> serviceDays = this.daysRepo.findServiceDayByPeriod(period, Sort.by("date"));
    	List<ServiceWeek> serviceWeeks = groupByWeeks(serviceDays, period);
    	response.setObjects(serviceWeeks);
    	
    	return response;
    }
    
    @RequestMapping(path="/period/{periodId}/schedule/{scheduleId}/weeks", method=RequestMethod.GET)
    public Response<ServiceWeek> loadServiceWeeks(@PathVariable(name="periodId", required=true) String periodId, 
    		@PathVariable(name="scheduleId", required=true) String scheduleId) {
    	
    	Response<ServiceWeek> response = new Response<ServiceWeek>();

    	ServicePeriod period = this.periodRepo.findById(periodId).get();

    	Calendar cal = Calendar.getInstance();
    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    	cal.add(Calendar.DATE, -(dayOfWeek - 1));
    	
    	List<ServiceDay> serviceDays = this.daysRepo.findServiceDayByPeriod(period, Sort.by("date"));
    	List<ServiceWeek> serviceWeeks = groupByWeeks(serviceDays, period, scheduleId);
    	response.setObjects(serviceWeeks);
    	
    	return response;
    }

    @RequestMapping(path="/period/{periodId}/schedule/{scheduleId}/weeksToServe", method=RequestMethod.GET)
    public Response<ServiceWeek> loadServiceWeeksToServe(@PathVariable(name="periodId", required=true) String periodId, 
    		@PathVariable(name="scheduleId", required=true) String scheduleId) {
    	
    	Response<ServiceWeek> response = new Response<ServiceWeek>();

    	ServicePeriod period = this.periodRepo.findById(periodId).get();

    	Calendar cal = Calendar.getInstance();
    	cal.setFirstDayOfWeek(Calendar.MONDAY);
    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    	cal.add(Calendar.DATE, - (dayOfWeek));
    	Date after = cal.getTime();
    	cal.add(Calendar.WEEK_OF_YEAR, 2);
    	
    	if(dayOfWeek == 1) {
    		cal.add(Calendar.DAY_OF_WEEK, 1);
    	}
    	
    	Date before = cal.getTime();
    	
    	List<ServiceDay> serviceDays = this.daysRepo.findServiceDayByPeriodAndDateBetween(period, after, before, Sort.by("date"));
    	List<ServiceWeek> serviceWeeks = groupByWeeks(serviceDays, period, scheduleId);
    	response.setObjects(serviceWeeks);
    	
    	return response;
    }
	private List<ServiceWeek> groupByWeeks(List<ServiceDay> serviceDays, ServicePeriod period, final String scheduleId) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		
		List<ServiceWeek> weeks = new ArrayList<ServiceWeek>();
		ServiceWeek week = new ServiceWeek();
		week.setPeriod(period);
		
		for(ServiceDay day : serviceDays) {
			Stream<CartDelivery> cartDeliveries = day.getDeliverTo().stream();
			
			List<CartDelivery> filtered = cartDeliveries.filter(delivery -> scheduleId.equalsIgnoreCase(delivery.getSchedule().getGuid())).collect(Collectors.toList());
			day.setDeliverTo(filtered);

			for(Shift shift: day.getShifts()) {
				List<PublisherAssignment> filteredAssignments = shift.getAssignments().stream().
						filter(assignment -> this.filterAssignmentsByScheduleId(assignment, scheduleId)).
						collect(Collectors.toList());
				shift.setAssignments(filteredAssignments);
			}
			
			calendar.setTime(day.getDate());
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY || weeks.size() == 0) {
				week = new ServiceWeek();
				week.setName(calendar.get(Calendar.WEEK_OF_YEAR) + "");
				weeks.add(week);
			}
			week.getWeekDays().add(day);
		}
		return weeks;
	}
	
	
	private boolean filterAssignmentsByScheduleId(PublisherAssignment assignment, String scheduleId) {
		CartSchedule currentSchedule = assignment.getSchedule();
		if(currentSchedule == null) {
			return false;
		}
		
		return scheduleId.equalsIgnoreCase(currentSchedule.getGuid());
	}
	
	private List<ServiceWeek> groupByWeeks(List<ServiceDay> serviceDays, ServicePeriod period) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		
		List<ServiceWeek> weeks = new ArrayList<ServiceWeek>();
		ServiceWeek week = new ServiceWeek();
		week.setPeriod(period);
		
		for(ServiceDay day : serviceDays) {
			day.getShifts().isEmpty();
			calendar.setTime(day.getDate());
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY || weeks.size() == 0) {
				week = new ServiceWeek();
				week.setName(calendar.get(Calendar.WEEK_OF_YEAR) + "");
				weeks.add(week);
			}
			week.getWeekDays().add(day);
		}
		return weeks;
	}
	
    @RequestMapping(path="/delete/{periodId}", method=RequestMethod.DELETE)
    public Response<Object> deletePeriod(@PathVariable(name="periodId", required=true) String periodId) {
    	Response<Object> response = new Response<Object>();
    	try {
    	this.periodRepo.deleteById(periodId);
    	} catch (org.springframework.transaction.TransactionSystemException e) {
    		response.setSuccessful(false);
    	}
    	return response;
    }
    
    @RequestMapping(path="/read/{periodId}", method=RequestMethod.GET)
    public ServicePeriod readPeriod(@PathVariable(name="periodId", required=true) String periodId) {
    	ServicePeriod period = this.periodRepo.findById(periodId).get();
    	return period;
    }
    
    @RequestMapping(path="/share/{periodId}", method=RequestMethod.GET)
    public ServicePeriod sharePeriod(@PathVariable(name="periodId", required=true) String periodId) {
    	List<ServicePeriod> sharedPeriods = periodRepo.findServicePeriodByIsShared(true);
    	for(ServicePeriod servicePeriod : sharedPeriods) {
    		servicePeriod.setIsShared(false);
    		this.periodRepo.save(servicePeriod);	
    	}
    	ServicePeriod period = this.periodRepo.findById(periodId).get();
    	period.setIsShared(true);
    	this.periodRepo.save(period);
    	return period;
    }   
    
    @RequestMapping(path="/unshare/{periodId}", method=RequestMethod.GET)
    public ServicePeriod unshare(@PathVariable(name="periodId", required=true) String periodId) {
    	ServicePeriod period = this.periodRepo.findById(periodId).get();
    	period.setIsShared(false);
    	this.periodRepo.save(period);
    	return period;
    }
    
//    @RequestMapping(path="/readWeek/{periodId}/{from}/{to}", method=RequestMethod.GET)
//    public ServicePeriod readWeek(@PathVariable(name="periodId", required=true) String periodId,@PathVariable(name="from", required=true) String from, @PathVariable(name="to", required=true) String to){
//    	ServicePeriod period = this.periodRepo.findById(periodId).get();
//    	period.setIsShared(false);
//    	this.periodRepo.save(period);
//    	return period;
//    }
    
    @RequestMapping(path="/download/{periodId}", method=RequestMethod.GET)
    public ResponseEntity<byte[]> downloadPeriod(@PathVariable(name="periodId", required=true) String periodId) throws JsonProcessingException {
    	ServicePeriod period = this.periodRepo.findById(periodId).get();
	    ObjectMapper mapper = new ObjectMapper();
	    byte[] output = mapper.writeValueAsBytes(period);

	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("charset", "utf-8");
	    responseHeaders.setContentType(MediaType.valueOf("text/json"));
	    responseHeaders.setContentLength(output.length);
	    responseHeaders.set("Content-disposition", "attachment; filename=Period(" + period.getName() + ").json");
	    return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
    }
}
