package forms;

import org.hibernate.validator.constraints.NotBlank;


public class FilterString {
	
	public FilterString(){
		super();
	}

	private String filter;
	
	@NotBlank
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}
	

}