package com.eae.schedule.ui.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eae.schedule.model.ServiceDay;
import com.eae.schedule.model.ServicePeriod;
import com.eae.schedule.model.Shift;
import com.eae.schedule.repo.ServiceDayRepository;
import com.eae.schedule.repo.ServicePeriodRepository;
import com.eae.schedule.repo.ShiftRepository;
import com.eae.schedule.ui.model.Response;
import com.eae.schedule.ui.model.ServiceWeek;

@RestController
@RequestMapping("/periods")
public class SerivePeriodsController {

	@Autowired
	private ServicePeriodRepository periodRepo;

	@Autowired
	private ServiceDayRepository daysRepo;
	

	@Autowired
	private ShiftRepository shiftRepo;
	
	@Autowired
	private org.springframework.orm.jpa.JpaTransactionManager transactionManager;
	
    @RequestMapping(name="/", method=RequestMethod.GET)
    public Response<ServicePeriod> getAll() {
    	Response<ServicePeriod> response = new Response<ServicePeriod>();
    	Sort sortByFromDate = new Sort(Sort.Direction.ASC, "starts");
    	List<ServicePeriod> periods = (List<ServicePeriod>) this.periodRepo.findAll(sortByFromDate);
    	response.setObjects(periods);
        return response; 
    }
    
    @RequestMapping(path="/create", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
    public Response<ServicePeriod> save(@RequestBody ServicePeriod period) {
    	Response<ServicePeriod> response = new Response<ServicePeriod>();
    	period = this.periodRepo.save(period);
    	Calendar from = Calendar.getInstance();
    	from.setTime(period.getStarts());

    	Calendar to = Calendar.getInstance();
    	to.setTime(period.getEnds());
    	
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
//    	period.setServiceDays(serviceDays);
    	
//    	this.daysRepo.saveAll(serviceDays);
    	for(ServiceDay day : serviceDays) {
    		this.daysRepo.save(day);	
    	}
    	
    	this.daysRepo.flush();
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
    	List<ServiceWeek> serviceWeeks = groupByWeeks(serviceDays);
    	response.setObjects(serviceWeeks);
    	
    	return response;
    }

	private List<ServiceWeek> groupByWeeks(List<ServiceDay> serviceDays) {
		Calendar calendar = Calendar.getInstance();
		
		List<ServiceWeek> weeks = new ArrayList<ServiceWeek>();
		ServiceWeek week = new ServiceWeek();
		
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
    	this.periodRepo.deleteById(periodId);
    	return response;
    }
    
    

}
