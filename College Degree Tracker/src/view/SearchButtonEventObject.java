package view;

import java.util.EventObject;

public class SearchButtonEventObject extends EventObject {

	private String idSearch;
	
	public SearchButtonEventObject(Object source) {
		super(source);
	}
	
	public SearchButtonEventObject(Object source, String idSearch) {
		super(source);
		this.idSearch = idSearch;
	}
	
	public String getIdSearch() {
		return idSearch;
	}
}
