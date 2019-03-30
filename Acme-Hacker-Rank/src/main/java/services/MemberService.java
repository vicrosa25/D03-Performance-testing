
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

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Dropout;
import domain.Enrol;
import domain.Member;
import domain.MessageBox;
import domain.Request;
import domain.SocialIdentity;
import forms.MemberForm;

@Service
@Transactional
public class MemberService {

	// Managed repository
	// -------------------------------------------------------------
	@Autowired
	private MemberRepository	memberRepository;

	// Supporting services
	// -------------------------------------------------------------

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private DropoutService			dropoutService;

	@Autowired
	private EnrolService			enrolService;

	@Autowired
	private SocialIdentityService	socialIdentityService;

	@Autowired
	private RequestService			requestService;


	// CRUD methods
	// ------------------------------------------------------------------

	public Member create() {
		Member result;

		result = new Member();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.MEMBER);

		final Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		final UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(authorities);

		final Collection<MessageBox> boxes = this.messageBoxService.createSystemMessageBox();

		result.setFinder(this.finderService.create());
		result.setDropouts(new ArrayList<Dropout>());
		result.setEnrols(new ArrayList<Enrol>());
		result.setRequests(new ArrayList<Request>());
		result.setUserAccount(userAccount);
		result.setMessageBoxes(boxes);

		return result;

	}
	public Member findOne(final int memberId) {
		final Member result = this.memberRepository.findOne(memberId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Member> findAll() {
		final Collection<Member> result = this.memberRepository.findAll();
		Assert.notEmpty(result);
		Assert.notNull(result);

		return result;
	}

	public Member save(final Member member) {
		Assert.notNull(member);
		final Member result = this.memberRepository.save(member);

		return result;
	}

	public void delete(final Member member) {
		Assert.isTrue(this.findByPrincipal() == member);
		Assert.notNull(member);

		Iterator<Dropout> drops				= new ArrayList<Dropout>(member.getDropouts()).iterator();
		Iterator<Enrol> enrols				= new ArrayList<Enrol>(member.getEnrols()).iterator();
		Iterator<Request> requests = new ArrayList<Request>(member.getRequests()).iterator();
		Iterator<SocialIdentity> socialIs 	= new ArrayList<SocialIdentity>
												(member.getSocialIdentities()).iterator();

		while (drops.hasNext()) {
			Dropout next = drops.next();
			this.dropoutService.delete(next);
			member.getDropouts().remove(next);
			drops.remove();
		}
		while (enrols.hasNext()) {
			Enrol next = enrols.next();
			this.enrolService.delete(next);
			member.getEnrols().remove(next);
			enrols.remove();
		}
		while (socialIs.hasNext()) {
			SocialIdentity si = socialIs.next();
			this.socialIdentityService.delete(si);
			member.getSocialIdentities().remove(si);
			socialIs.remove();
		}
		while (requests.hasNext()) {
			Request request = requests.next();
			this.requestService.deleteMember(request.getId());
			member.getRequests().remove(request);
			requests.remove();
		}
		member.setMessageBoxes(new ArrayList<MessageBox>());
		this.messageBoxService.deleteAll(member);
		
		this.memberRepository.delete(member);

	}


	// Other methods
	// -----------------------------------------------------------------

	@Autowired
	private Validator	validator;

	/*** Reconstruct object, check validity and update binding ***/
	
	/** Form Object**/
	public Member reconstruct(MemberForm memberForm, BindingResult binding) {
		Member result = new Member();
		try {
			result = this.findByPrincipal();
		} catch (final IllegalArgumentException a) {
			result = this.create();
		}

		result.getUserAccount().setPassword(memberForm.getUserAccount().getPassword());
		result.getUserAccount().setUsername(memberForm.getUserAccount().getUsername());
		result.setUsername(memberForm.getUserAccount().getUsername());

		result.setAddress(memberForm.getAddress());

		result.setEmail(memberForm.getEmail());

		result.setMiddleName(memberForm.getMiddleName());
		result.setName(memberForm.getName());
		result.setPhoneNumber(memberForm.getPhoneNumber());
		result.setPhoto(memberForm.getPhoto());

		result.setSurname(memberForm.getSurname());
		
		// Default attributes from Actor
		result.setUsername(memberForm.getUserAccount().getUsername());
		//result.setIsSpammer(false);
		result.setIsBanned(false);

		this.validator.validate(result, binding);

		return result;
	}
	
	
	/** Pruned Object **/
	public Member reconstruct(Member member, BindingResult binding) {
		Member result = this.create();
		Member temp = this.findOne(member.getId());

		// Check the principal is updating his own data.
		Assert.isTrue(this.findByPrincipal().getId() == member.getId());

		// Updated attributes
		result.setAddress(member.getAddress());
		result.setEmail(member.getEmail());
		result.setMiddleName(member.getMiddleName());
		result.setName(member.getName());
		result.setPhoneNumber(member.getPhoneNumber());
		result.setPhoto(member.getPhoto());
		result.setSurname(member.getSurname());
		result.setName(member.getName());

		// Not updated attributes
		result.setId(temp.getId());
		result.setVersion(temp.getVersion());
		result.setUsername(temp.getUsername());
		result.setIsSpammer(temp.getIsSpammer());
		result.setIsBanned(temp.getIsBanned());
		result.setScore(temp.getScore());
		
		// Relantionships from Member
		result.setFinder(temp.getFinder());
		result.setEnrols(temp.getEnrols());
		result.setDropouts(temp.getDropouts());
		result.setRequests(temp.getRequests());
		
		// Relatienships from Actor
		result.setUserAccount(temp.getUserAccount());
		result.setMessageBoxes(temp.getMessageBoxes());
		result.setSocialIdentities(temp.getSocialIdentities());
		

		this.validator.validate(result, binding);

		return result;
	}
	
	
	public Member findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Member result;

		result = this.memberRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Member findByPrincipal() {
		Member result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}
	public Member findOneByUsername(final String username) {
		Assert.notNull(username);

		return this.memberRepository.findByUserName(username);
	}

	public Collection<Member> findByBrotherhood(final Brotherhood brotherhood) {
		final Collection<Member> result = this.memberRepository.findByBrotherhood(brotherhood.getId());
		Assert.notNull(result);
		return result;
	}
}
