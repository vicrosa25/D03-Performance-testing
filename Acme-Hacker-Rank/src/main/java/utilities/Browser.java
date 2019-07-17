
package utilities;


public class Browser {

	private String		keyword;


	// Getters and Setters
	public String getKeyword() {
		return this.keyword;
	}
	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "Browser [Keyword=" + this.keyword + "]";
	}
}
