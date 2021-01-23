package advisor;

import com.google.gson.JsonArray;

public class AdviseFeatured implements IAdvisor {
	@Override
	public void advise(JsonArray items) {
		items.forEach(item -> {
			var jo = item.getAsJsonObject();
			var name = jo.get("name").getAsString();
			var info = jo.get("external_urls").getAsJsonObject()
					           .get("spotify").getAsString();
			System.out.println(name);
			System.out.println(info);
			System.out.println();
		});
	}
}
