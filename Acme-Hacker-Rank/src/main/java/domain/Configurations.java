
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configurations extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Integer				cacheTime;
	private Integer				finderMaxResult;
	private String				spanishMessage;
	private String				englishMessage;
	private Collection<String>	spamWords;
	private String				countryCode;
	private String				title;
	private String				logo;

	@NotNull
	@Range(min = 1, max = 24)
	public Integer getCacheTime() {
		return this.cacheTime;
	}

	public void setCacheTime(Integer cacheTime) {
		this.cacheTime = cacheTime;
	}

	@NotNull
	@Range(min = 10, max = 100)
	public Integer getFinderMaxResult() {
		return this.finderMaxResult;
	}

	public void setFinderMaxResult(Integer finderMaxResult) {
		this.finderMaxResult = finderMaxResult;
	}

	@NotNull
	@NotEmpty
	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotBlank
	@Pattern(regexp = "([+]?\\d{1,2})")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@NotEmpty
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotEmpty
	@URL
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@NotBlank
	public String getSpanishMessage() {
		return this.spanishMessage;
	}

	public void setSpanishMessage(String spanishMessage) {
		this.spanishMessage = spanishMessage;
	}

	@NotBlank
	public String getEnglishMessage() {
		return this.englishMessage;
	}

	public void setEnglishMessage(String englishMessage) {
		this.englishMessage = englishMessage;
	}
}
