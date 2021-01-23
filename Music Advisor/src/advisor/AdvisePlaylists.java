package advisor;

import com.google.gson.JsonArray;

public class AdvisePlaylists implements IAdvisor {
	
	@Override
	public void advise(JsonArray items) {
		try {
			items.forEach(item -> {
				var jo = item.getAsJsonObject();
				var name = jo.get("name").getAsString();
				var url = jo.get("external_urls").getAsJsonObject().get("spotify").getAsString();
				System.out.println(name);
				System.out.println(url);
				System.out.println();
			});
		} catch (NullPointerException e) {
			System.out.println("Unknown category name.");
		}
	}
	
	
}
