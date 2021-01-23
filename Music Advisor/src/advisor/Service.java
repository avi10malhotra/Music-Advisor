package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

public class Service implements IService {
	private final Interaction httpInteraction = Interaction.getInstance();
	private IAdvisor advisor;
	private String requestedFeature;
	
	@Override
	public void getFeatured() throws IOException, InterruptedException {
		requestedFeature = httpInteraction.createRequest(Info.FEATURED_URL);
		JsonArray featuredLists = JsonParser.parseString(requestedFeature).getAsJsonObject()
				                          .get("playlists").getAsJsonObject()
				                          .get("items").getAsJsonArray();
		
		advisor = new AdviseFeatured();
		advisor.advise(featuredLists);
	}
	
	@Override
	public void getNew() throws IOException, InterruptedException {
		requestedFeature = httpInteraction.createRequest(Info.RELEASE_URL);
		JsonArray albums = JsonParser.parseString(requestedFeature).getAsJsonObject()
				                   .get("albums").getAsJsonObject()
				                   .get("items").getAsJsonArray();
		advisor = new AdviseAlbums();
		advisor.advise(albums);
	}
	
	@Override
	public void getCategories() throws IOException, InterruptedException {
		requestedFeature = httpInteraction.createRequest(Info.CATEGORIES_URL);
		JsonArray categories = JsonParser.parseString(requestedFeature).getAsJsonObject().getAsJsonObject()
				                       .get("categories").getAsJsonObject()
				                       .get("items").getAsJsonArray();
		
		advisor = new AdviseCategories();
		advisor.advise(categories);
	}
	
	
	@Override
	public void getPlaylists(String categoryName) throws IOException, InterruptedException {
		String categoryID = getIdByName(categoryName);
		String playlistsURL = Info.CATEGORIES_URL + "/" + categoryID + "/playlists";
		requestedFeature = httpInteraction.createRequest(playlistsURL);
		
		var playlists = JsonParser.parseString(requestedFeature).getAsJsonObject();
		var items = playlists.get("playlists").getAsJsonObject().get("items").getAsJsonArray();
		
		advisor = new AdvisePlaylists();
		advisor.advise(items);
	}
	
	private String getIdByName(String categoryName) throws IOException, InterruptedException {
		String categories = httpInteraction.createRequest(Info.CATEGORIES_URL);
		JsonArray items = JsonParser.parseString(categories).getAsJsonObject()
				                  .get("categories").getAsJsonObject()
				                  .get("items").getAsJsonArray();
		String name;
		for (JsonElement item : items) {
			name = item.getAsJsonObject().get("name").getAsString();
			if (categoryName.equals(name))
				return item.getAsJsonObject().get("id").getAsString();
			
		}
		throw new NullPointerException();
	}
}
