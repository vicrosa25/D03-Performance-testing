package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Application;
import domain.Position;
import services.ApplicationService;
import services.PositionService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HackerCreateAppTest extends AbstractTest {
	
	// System under test ---------------------------------------------------------------------------
	@Autowired
	private ApplicationService	applicationService;
	
	@Autowired
	private PositionService		positionService;
	
	
	// Tests -----------------------------------------------------------------------------------------
	
	
	/**
	 * Requirement: An actor who is authenticated as a hacker must be able to:  "Create an Application"
	 *  
	 * 	1. Positive test.
	 *  
	 **/
	@Test
	public void createAppPositive(){
		int 		positionId;
		int 		applicationsNumber;
		int 		finalApplicationsNumber;
		Position 	position;
		Application	app;
		
		

		applicationsNumber = this.applicationService.findAll().size();
		
		super.authenticate("hacker1");
		app = this.applicationService.create();
		
		
		positionId = super.getEntityId("position3");
		position = this.positionService.findOne(positionId);
		app.setPosition(position);
		
		
		this.applicationService.save(app);
		finalApplicationsNumber = this.positionService.findAll().size();
		
		
		Assert.isTrue(applicationsNumber < finalApplicationsNumber);
		
		
		
		super.unauthenticate();
		
	}
	
	
	/**
	 * Requirement: An actor who is authenticated as a hacker must be albe to:  "Create an Application"
	 *  
	 * 1. Negative test.
	 * 2. Business rule that is intended to broke: The actor is not authenticated as a hacker
	 *  
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void createAppNegative(){
		int 		positionId;
		Position 	position;
		Application	app;
		
		super.authenticate(null);
		app = this.applicationService.create();
		positionId = super.getEntityId("position3");
		position = this.positionService.findOne(positionId);
		app.setPosition(position);
		this.applicationService.save(app);
	}
}
