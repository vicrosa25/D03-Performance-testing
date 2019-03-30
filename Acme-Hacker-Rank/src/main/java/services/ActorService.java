
package services;

import java.io.FileOutputStream;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import domain.Actor;
import domain.Brotherhood;
import domain.Chapter;

@Service
@Transactional
public class ActorService {

	// Manage Repository
	@Autowired
	private ActorRepository	actorRepository;


	// CRUD methods

	public Actor findOne(final int actorId) {
		final Actor result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Actor> findAll() {
		final Collection<Actor> result = this.actorRepository.findAll();
		Assert.notNull(result);
		Assert.notEmpty(result);

		return result;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		final Actor result = this.actorRepository.save(actor);

		return result;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);

		this.actorRepository.delete(actor);
	}

	// Other business methods
	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Actor result;

		result = this.actorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Actor findOneByUsername(final String username) {
		Assert.notNull(username);

		return this.actorRepository.findByUserName(username);
	}
	
	public Collection<Actor> findSpammers() {
		return this.actorRepository.findSpammers();
	}

	public Document generatePersonalInformationPDF(Actor actor, String path) {
		Document doc = null;
		try {
			// Creating a Document        
			doc = new Document();
			PdfWriter.getInstance(doc, new FileOutputStream(path));
			doc.open();

			// Creating a table       
			float[] pointColumnWidths = {
				150F, 150F
			};
			PdfPTable table = new PdfPTable(pointColumnWidths);

			// Adding cells to the table
			PdfPCell cell = new PdfPCell(new Phrase("Personal information of " + actor.getName()));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(2);
			table.addCell(cell);

			table.addCell(new PdfPCell(new Phrase("Name")));
			table.addCell(new PdfPCell(new Phrase(actor.getName())));
			table.addCell(new PdfPCell(new Phrase("Username")));
			table.addCell(new PdfPCell(new Phrase(actor.getUsername())));
			table.addCell(new PdfPCell(new Phrase("Middle Name")));
			table.addCell(new PdfPCell(new Phrase(actor.getMiddleName())));
			table.addCell(new PdfPCell(new Phrase("Surname")));
			table.addCell(new PdfPCell(new Phrase(actor.getSurname())));
			table.addCell(new PdfPCell(new Phrase("Photo")));
			table.addCell(new PdfPCell(new Phrase(actor.getPhoto())));
			table.addCell(new PdfPCell(new Phrase("Email")));
			table.addCell(new PdfPCell(new Phrase(actor.getEmail())));
			table.addCell(new PdfPCell(new Phrase("Phone number")));
			table.addCell(new PdfPCell(new Phrase(actor.getPhoneNumber())));
			table.addCell(new PdfPCell(new Phrase("Address")));
			table.addCell(new PdfPCell(new Phrase(actor.getAddress())));

			if (actor instanceof Brotherhood) {
				Brotherhood bro = (Brotherhood) actor;
				table.addCell(new PdfPCell(new Phrase("Brotherhood title")));
				table.addCell(new PdfPCell(new Phrase(bro.getTitle())));
				table.addCell(new PdfPCell(new Phrase("Area")));
				table.addCell(new PdfPCell(new Phrase(bro.getArea().getName())));
				table.addCell(new PdfPCell(new Phrase("Establishment Date")));
				table.addCell(new PdfPCell(new Phrase(bro.getEstablishment().toString())));
			}

			if (actor instanceof Chapter) {
				Chapter chapter = (Chapter) actor;
				table.addCell(new PdfPCell(new Phrase("Chapter title")));
				table.addCell(new PdfPCell(new Phrase(chapter.getTitle())));
			}

			// Adding Table to document        
			doc.add(table);

			// Closing the document    
			doc.close();
		} catch (Exception e) {
			System.out.println(e.getClass());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			doc.close();
		}

		return doc;
	}
}
