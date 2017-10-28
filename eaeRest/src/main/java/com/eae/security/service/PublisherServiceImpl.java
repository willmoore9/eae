package com.eae.security.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eae.schedule.model.Publisher;

@Service
public class PublisherServiceImpl implements PublisherService {

	@Autowired
	private DataSource dataSource;

	@Override
	public List<Publisher> findPublisherByEmail(String email) {
	
		List<Publisher> publishers = (List<Publisher>) this.getEntityManagerFactory().createEntityManager().createQuery("SELECT p FROM Publisher p WHERE p.email LIKE :email").
				setParameter("email", email).getResultList();
		
		return publishers;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected EntityManagerFactory getEntityManagerFactory()
	{
		EntityManagerFactory retVal = null;
		
		try
		{
			Map properties = new HashMap();
			
			DataSource ds = dataSource;
			
	        properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
	        
	        retVal = Persistence.createEntityManagerFactory("eae", properties);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return retVal;
	}
}
