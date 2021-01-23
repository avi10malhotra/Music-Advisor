package advisor;

import com.google.gson.JsonArray;

import java.util.ArrayList;

public class AdviseAlbums implements IAdvisor {
	@Override
	public void advise(JsonArray items) {
		items.forEach(item -> {
			var artists = new ArrayList<>();
			var album = item.getAsJsonObject();
			var name = album.get("name").getAsString();
			var url = album.get("external_urls").getAsJsonObject()
					          .get("spotify").getAsString();
			
			album.get("artists").getAsJsonArray().forEach(artist -> {
				artists.add(artist.getAsJsonObject().get("name").getAsString());
				System.out.println(name);
				System.out.println(artists);
				System.out.println(url);
				System.out.println();
			});
		});
		
	}
}