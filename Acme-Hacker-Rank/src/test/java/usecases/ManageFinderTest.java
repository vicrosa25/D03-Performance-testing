
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.FinderService;
import services.HackerService;
import services.PositionService;
import utilities.AbstractTest;
import domain.Finder;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManageFinderTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private FinderService	finderService;

	// Supporting systems ------------------------------------------------------
	@Autowired
	private HackerService	hackerService;
	@Autowired
	private PositionService	positionService;


	// Test ------------------------------------------------------
	/*
	 * An actor who is authenticated as a hacker must be able to manage his/her finder
	 * This test consists in update the finder, check the results, clear the finder and check the results again
	 */

	@Test
	public void testFinder() {
		super.authenticate("hacker1");

		Finder finder = this.hackerService.findByPrincipal().getFinder();

		finder.setKeyword("aoiethjsbipogjasket09ihopsrt"); // keyword that no position has, so the results must be empty

		finder = this.finderService.checkChanges(finder);
		Assert.isTrue(finder.getPositions().size() == 0);

		finder = this.finderService.clear(finder);
		Assert.isTrue(finder.getPositions().size() == this.positionService.findAllFinal().size());

		this.finderService.clear(finder);
	}
}
