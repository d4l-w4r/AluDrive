package driveTools;

import java.util.HashMap;

public class MetaData extends HashMap<String, String> {
	private static final long serialVersionUID = 4511906380651846535L;
	
	public MetaData(String title, String description, String mime) {
		super();
		this.put("title", title);
		this.put("description", description);
		this.put("mime", mime);
	}

}
