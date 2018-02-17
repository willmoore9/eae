package com.eae.schedule.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eae.schedule.model.CartSchedule;
import com.eae.schedule.model.Publisher;
import com.eae.schedule.model.ServicePeriod;
import com.eae.schedule.repo.CartScheduleRepository;
import com.eae.schedule.repo.ServicePeriodRepository;
import com.eae.schedule.ui.model.LandingDTO;

@RestController
@RequestMapping("/landing")
public class Landing {
	
	@Autowired
	private ServicePeriodRepository periodRepo;
	
	@Autowired
	private CartScheduleRepository cartScheduleRepo;
	
    @RequestMapping(name="/", method=RequestMethod.GET)
    public LandingDTO getLandPublisher() {
    	LandingDTO landingData = new LandingDTO();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object currentPrincipal = authentication.getPrincipal();
		
		List<ServicePeriod> periods = this.periodRepo.findServicePeriodByIsShared(true);
		
		if(periods.size() > 0) {
			landingData.setCurrentPeriod(periods.get(0));
		}
		
		if(currentPrincipal instanceof  Publisher) {
			landingData.setPublisher((Publisher)currentPrincipal);
		} else {
			Publisher annonymous = new Publisher();
			annonymous.setEmail("ANONYMOUS");
			landingData.setPublisher(annonymous);
		}
		
		List<CartSchedule> sharedSchedules = cartScheduleRepo.findCartScheduleByIsShared(true);
		
		landingData.setSharedSchedules(sharedSchedules);
		return landingData; 
    }
    
}
