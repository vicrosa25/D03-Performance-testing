package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChapterRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Area;
import domain.Chapter;
import domain.MessageBox;
import domain.Procession;
import domain.SocialIdentity;
import forms.ChapterForm;

@Service
@Transactional
public class ChapterService {
	
	// Manage Repository
	@Autowired
	private ChapterRepository		chapterRepository;
	
	
	// Supporting services
	@Autowired
	private MessageBoxService		messageBoxService;
	
	@Autowired
	private ConfigurationsService 	configurationsService;
	
	@Autowired
	private Validator				validator;
	
	@Autowired
	private ProcessionService		processionService;
	
	@Autowired
	private ActorService			actorService;
	
	@Autowired
	private AreaService 			areaService;

	@Autowired
	private SocialIdentityService	socialIdentityService;
	
	
	/*************************************
	 * CRUD methods
	 *************************************/
	public Chapter create() {
		
		Chapter result;
		
		// UserAccount
		UserAccount userAccount = new UserAccount();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority(Authority.CHAPTER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		// MessageBox
		Collection<MessageBox> boxes = this.messageBoxService.createSystemMessageBox();

		
		result = new Chapter();
		
		// Default settings
		result.setUserAccount(userAccount);
		result.setMessageBoxes(boxes);

		return result;
	}
	
	public Collection<Chapter> findAll() {
		Collection<Chapter> result = this.chapterRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	
	public Chapter findOne(int chapterID) {
		Chapter result = this.chapterRepository.findOne(chapterID);
		Assert.notNull(result);
		
		return result;
	}
	
	public Chapter save(Chapter chapter) {
		Assert.notNull(chapter);
		UserAccount userAccount;

		if (chapter.getId() == 0) {
			chapter.setMessageBoxes(this.messageBoxService.createSystemMessageBox());
			if (!chapter.getPhoneNumber().startsWith("+")) {
				String countryCode = this.configurationsService.getConfiguration().getCountryCode();
				String phoneNumer = chapter.getPhoneNumber();
				chapter.setPhoneNumber(countryCode.concat(phoneNumer));
			}
		} else {
			if (!chapter.getPhoneNumber().startsWith("+")) {
				String countryCode = this.configurationsService.getConfiguration().getCountryCode();
				String phoneNumer = chapter.getPhoneNumber();
				chapter.setPhoneNumber(countryCode.concat(phoneNumer));
			}
			
			userAccount = LoginService.getPrincipal();
			Assert.isTrue(userAccount.equals(chapter.getUserAccount()));
		}
		
		
		return this.chapterRepository.save(chapter);
	}
	
	public void delete(Chapter chapter) {
		Assert.notNull(chapter);
		Assert.isTrue(this.findByPrincipal() == chapter);
		
		chapter.getArea().setChapter(null);

		Iterator<SocialIdentity> socialIs = new ArrayList<SocialIdentity>(chapter.getSocialIdentities()).iterator();
		while (socialIs.hasNext()) {
			SocialIdentity si = socialIs.next();
			this.socialIdentityService.delete(si);
			chapter.getSocialIdentities().remove(si);
			socialIs.remove();
		}
		chapter.setMessageBoxes(new ArrayList<MessageBox>());
		this.messageBoxService.deleteAll(chapter);
		
		this.chapterRepository.delete(chapter);
	}
	
	
	
	/****************************************************************** 
	 * Reconstruct form object, check validity and update binding 
	 * ***************************************************************/
	public Chapter reconstruct(ChapterForm form, BindingResult binding) {
		Chapter chapter = this.create();

		chapter.getUserAccount().setPassword(form.getUserAccount().getPassword());
		chapter.getUserAccount().setUsername(form.getUserAccount().getUsername());

		chapter.setAddress(form.getAddress());
		chapter.setEmail(form.getEmail());
		chapter.setMiddleName(form.getMiddlename());
		chapter.setName(form.getName());
		chapter.setPhoneNumber(form.getPhoneNumber());
		chapter.setPhoto(form.getPhoto());
		chapter.setSurname(form.getSurname());
		chapter.setTitle(form.getTitle());
		chapter.setArea(form.getArea());


		// Default attributes from Actor
		chapter.setUsername(form.getUserAccount().getUsername());

		chapter.setIsBanned(false);

		this.validator.validate(chapter, binding);

		return chapter;
	}
	
	/****************************************************************** 
	 * Reconstruct pruned object, check validity and update binding 
	 * ***************************************************************/
	public Chapter reconstruct(Chapter chapter, BindingResult binding) {
		Chapter result = this.create();
		Chapter temp = this.findOne(chapter.getId());

		Assert.isTrue(this.findByPrincipal().getId() == chapter.getId());

		// Updated attributes
		result.setTitle(chapter.getTitle());
		result.setAddress(chapter.getAddress());
		result.setEmail(chapter.getEmail());
		result.setMiddleName(chapter.getMiddleName());
		result.setName(chapter.getName());
		result.setPhoneNumber(chapter.getPhoneNumber());
		result.setPhoto(chapter.getPhoto());
		result.setSurname(chapter.getSurname());
		

		// Not updated attributes
		result.setId(temp.getId());
		result.setVersion(temp.getVersion());
		result.setUsername(temp.getUsername());
		result.setIsSpammer(temp.getIsSpammer());
		result.setIsBanned(temp.getIsBanned());
		result.setScore(temp.getScore());

		// Relantionships
		result.setArea(temp.getArea());
		result.setUserAccount(temp.getUserAccount());
		result.setSocialIdentities(temp.getSocialIdentities());

		
		this.validator.validate(result, binding);

		return result;
	}
	
	
	/****************************************************************** 
	 * Reconstruct pruned object, Add Area
	 * ***************************************************************/
	public Chapter addArea(Chapter prune, BindingResult binding) {
		Chapter result = this.create();
		Chapter temp = this.findOne(prune.getId());

		Assert.isTrue(this.findByPrincipal().getId() == prune.getId());

		// Updated attributes
		result.setArea(prune.getArea());

		// Not updated attributes
		result.setId(temp.getId());
		result.setVersion(temp.getVersion());
		result.setUsername(temp.getUsername());
		result.setTitle(temp.getTitle());
		result.setAddress(temp.getAddress());
		result.setEmail(temp.getEmail());
		result.setMiddleName(temp.getMiddleName());
		result.setName(temp.getName());
		result.setPhoneNumber(temp.getPhoneNumber());
		result.setPhoto(temp.getPhoto());
		result.setSurname(temp.getSurname());
		result.setIsSpammer(temp.getIsSpammer());
		result.setIsBanned(temp.getIsBanned());
		result.setScore(temp.getScore());

		// Relantionships
		result.setUserAccount(temp.getUserAccount());
		result.setSocialIdentities(temp.getSocialIdentities());

		
		this.validator.validate(result, binding);

		return result;
	}
	
	
	
	
	// AddArea Principal
	public void addAreaPrincipal(int areaId) {
		Assert.isTrue(areaId != 0);
		
		Chapter chapter;
		Area area;
		
		chapter = this.findByPrincipal();
		Assert.notNull(chapter);
		
		area = this.areaService.findOne(areaId);
		Assert.notNull(area);
		
		chapter.setArea(area);
		area.setChapter(chapter);
		
		this.chapterRepository.save(chapter);
		this.areaService.save(area);
		
	}
		

	/*************************************
	 * Aprove/Reject Parade
	 *************************************/
	
	public Procession aproveProcession(Procession procession) {
		
		// Check principal is a Chapter
		Actor principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Chapter.class, principal);
		
		
		procession.setStatus("APPROVED");
		
		return this.processionService.save(procession);
	}
	
	public Procession rejectParade(Procession procession) {
		
		// Check principal is a Chapter
		Actor principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Chapter.class, principal);
		
		
		procession.setStatus("REJECTED");
		
		return this.processionService.save(procession);
	}
	
	
	




	/*************************************
	 * Other business methods
	*************************************/
	public Chapter findByPrincipal() {
		Chapter result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Chapter findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);

		Chapter result;

		result = this.chapterRepository.findByUserAccountId(userAccount.getId());

		return result;
	}
	
	public Collection<Procession> findAllProcessionByChapter(int chapterId) {
		return this.chapterRepository.findProccesionsByChapter(chapterId);
	}

}
