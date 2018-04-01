package com.eae.schedule.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.eae.schedule.model.CartDelivery;
import com.eae.schedule.model.CartSchedule;
import com.eae.schedule.model.PublisherAssignment;
import com.eae.schedule.model.ServiceDay;
import com.eae.schedule.model.ServicePeriod;
import com.eae.schedule.model.Shift;
import com.eae.schedule.ui.model.ServiceWeek;

public class DtoUtils {
	
	public static List<ServiceWeek> groupByWeeks(List<ServiceDay> serviceDays, ServicePeriod period, final String scheduleId) {
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
						filter(assignment -> filterAssignmentsByScheduleId(assignment, scheduleId)).
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

	public static List<ServiceWeek> groupByWeeks(List<ServiceDay> serviceDays, ServicePeriod period) {
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

	private static boolean filterAssignmentsByScheduleId(PublisherAssignment assignment, String scheduleId) {
		CartSchedule currentSchedule = assignment.getSchedule();
		if(currentSchedule == null) {
			return false;
		}
		
		return scheduleId.equalsIgnoreCase(currentSchedule.getGuid());
	}		
}
