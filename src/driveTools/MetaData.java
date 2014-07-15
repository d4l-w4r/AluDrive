package driveTools;

import java.util.HashMap;
import java.util.Set;

public class MetaData extends HashMap<String, String> {
	private static final long serialVersionUID = 4511906380651846535L;
	
	public MetaData(String title, String description, String mime) {
		super();
		this.put("title", title);
		this.put("description", description);
		this.put("mime", mime);
		debugEntries();
	}
	
	public MetaData(String title, String mime) {
		super();
		this.put("title", title);
		this.put("mime", mime);
		debugEntries();
	}

	private void debugEntries() {
		Set<String> keys = this.keySet();
		for (String key: keys) {
			System.out.println("DEBUG (driveTools.MetaData): " + key + " = " + this.get(key));
		}
	}
}
