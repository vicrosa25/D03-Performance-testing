package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Area;
import domain.Chapter;
import services.AreaService;
import services.ChapterService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SelfAssignAnAreaTest extends AbstractTest {
	
	// System under test ---------------------------------------------------------------------------
	@Autowired
	private ChapterService 		chapterService;
	
	@Autowired
	private AreaService			areaService;
	
	
	// Tests -----------------------------------------------------------------------------------------
	
	
	/**
	 * Requirement: An actor who is authenticated as a chapter must be albe to:  self-assign an area to coordinate
	 *  
	 * Positive test.
	 *  
	 **/
	@Test
	public void selfAssignaAnArea(){
		int areaId;
		Chapter principal;
		Chapter chapter;
		Area area;
		BindingResult binding = null;
		
		
		
		areaId = super.getEntityId("area2");
		super.authenticate("chapter2");
		
		principal = this.chapterService.findByPrincipal();
		area = this.areaService.findOne(areaId);
		principal.setArea(area);
		
		chapter = this.chapterService.addArea(principal, binding);
		
		this.chapterService.save(chapter);
		
		Assert.isTrue(chapter.getArea().equals(area));
		super.unauthenticate();
	}
	
	
	/**
	 * Requirement: An actor who is authenticated as a chapter must be albe to:  self-assign an area to coordinate
	 *  
	 * 1. Negative test.
	 * 2. Business rule that is intended to broke: The actor is not authenticated as a chapter
	 *  
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void testBanRequestNotAadmin(){
		int areaId;
	
		areaId = super.getEntityId("area2");
		
		this.authenticate(null);
		
		this.chapterService.addAreaPrincipal(areaId);
		
		this.unauthenticate();
	}
	

}
