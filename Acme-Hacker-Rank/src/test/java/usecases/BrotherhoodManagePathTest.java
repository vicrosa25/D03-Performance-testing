
package usecases;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import services.BrotherhoodService;
import services.PathService;
import services.SegmentService;
import utilities.AbstractTest;
import domain.Path;
import domain.Procession;
import domain.Segment;
import forms.PathForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BrotherhoodManagePathTest extends AbstractTest {

	// Systems under test ------------------------------------------------------
	@Autowired
	private PathService			pathService;

	@Autowired
	private SegmentService		segmentService;

	// Supporting services
	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Test ------------------------------------------------------

	/**
	 * Manage the paths of their parades, which includes listing, showing, creating, updating,
	 * and deleting them.
	 * **/

	/*
	 * Brotherhoods can manage paths: Create a path
	 * 
	 * 01- Create a path; -> Correct
	 * 02- Destination time before origin time; -> Error
	 * 03- Null user; -> Error
	 * 04- Null origin time; -> Error
	 */

	@Test
	public void driverPath() {
		final Object testingData[][] = {
			// exception, user, originTime, destinationTime
			{
				null, "brotherhood1", "10/07/2019 17:00", "10/07/2019 18:00"
			}, {
				NullPointerException.class, "brotherhood1", "10/07/2019 19:00", "10/07/2019 18:00"	// Is null because it is trying to add the error to the bindingResult
			}, {
				IllegalArgumentException.class, null, "10/07/2019 17:00", "10/07/2019 18:00"
			}, {
				NullPointerException.class, "brotherhood1", null, "10/07/2019 18:00"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePath((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3]);
	}

	/*
	 * Brotherhoods can manage paths: Create a segment
	 * 
	 * 01- Delete a segment; -> Correct
	 * 02- Delete a segment of other brotherhood -> Error
	 */
	@Test
	public void driverSegment() {
		final Object testingData[][] = {
			// exception, user
			{
				null, "brotherhood1"
			}, {
				IllegalArgumentException.class, "brotherhood2"	// Only brotherhood1 has segments in our populate
			}, {
				IllegalArgumentException.class, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSegment((Class<?>) testingData[i][0], (String) testingData[i][1]);
	}

	// TEMPLATES ------------------------------------------------------------------------------------------------------------------

	protected void templatePath(Class<?> expected, String user, String originTime, String destinationTime) {
		Class<?> caught;
		caught = null;
		BindingResult binding = null;

		try {
			// Authentication
			authenticate(user);

			// Creating a form
			PathForm form = new PathForm();

			// Filling the form
			form.setProcession(new ArrayList<Procession>(this.brotherhoodService.findByPrincipal().getProcessions()).get(0));
			form.setDestinationLatitude(20.0);
			form.setOriginLatitude(20.0);
			form.setDestinationLongitude(20.0);
			form.setOriginLongitude(20.0);
			form.setDestinationTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(destinationTime));
			form.setOriginTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(originTime));

			// Reconstruct the path
			Path path = this.pathService.reconstruct(form, binding);

			path = this.pathService.save(path);
			final Segment segment = this.pathService.reconstructSegment(form, binding);
			segment.setPath(path);
			this.segmentService.save(segment);

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateSegment(Class<?> expected, String user) {
		Class<?> caught;
		caught = null;
		Segment segment = null;

		try {
			// Authentication
			authenticate(user);

			// Get the second segment of the path
			for (Segment seg : this.segmentService.findAll()) {
				if (seg.getNumber() == 1) {
					segment = seg;
				}
			}
			this.segmentService.delete(segment);
			this.segmentService.flush();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}