package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Procession;
import services.ChapterService;
import services.ProcessionService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChapterApproveParadeTest extends AbstractTest {
	
	// System under test ---------------------------------------------------------------------------
	@Autowired
	private ChapterService 		chapterService;
	
	@Autowired
	private ProcessionService	processionService;
	
	
	// Tests -----------------------------------------------------------------------------------------
	
	
	/**
	 * Requirement: An actor who is authenticated as a chapter must be able to:  "Approve a Parade"
	 *  
	 * 	1. Positive test.
	 *  
	 **/
	@Test
	public void selfAssignaAnAreaPositive(){
		int processionId;
		Procession procession;
		
		processionId = super.getEntityId("procession2");
		procession = this.processionService.findOne(processionId);
		
		super.authenticate("chapter2");
		this.chapterService.aproveProcession(procession);
		
		super.unauthenticate();
		
	}
	
	
	/**
	 * Requirement: An actor who is authenticated as a chapter must be albe to:  "Approve a Parade"
	 *  
	 * 1. Negative test.
	 * 2. Business rule that is intended to broke: The actor is not authenticated as a chapter
	 *  
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void selfAssignaAnAreaNegative(){
		int processionId;
		Procession procession;
		
		// Get the Parade
		processionId = super.getEntityId("procession2");
		procession = this.processionService.findOne(processionId);
		
		// Chapter is not authenticate
		this.authenticate(null);
		
		this.chapterService.aproveProcession(procession);
		
		this.unauthenticate();
	}
	

}
