package advisor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Service implements IService {
	@Override
	public List<Playlist> getFeatured() {
		return Arrays.asList(
				new Playlist("Mellow Morning"),
				new Playlist("Wake Up and Smell the Coffee"),
				new Playlist("Monday Motivation"),
				new Playlist("Songs to Sing in the Shower")
		);
	}
	
	@Override
	public List<Album> getNew() {
		return Arrays.asList(
				new Album(
						"Mountains",
						Arrays.asList(
								new Artist("Sia"),
								new Artist("Diplo"),
								new Artist("Labrinth"))
				),
				new Album("Runaway", new Artist("Lil Peep")),
				new Album("The Greatest Show", new Artist("Panic! At The Disco")),
				new Album("All Out Life", new Artist("Slipknot"))
		);
	}
	
	@Override
	public List<Category> getCategories() {
		return Arrays.asList(
				new Category("Top Lists"),
				new Category("Pop"),
				new Category("Mood"),
				new Category("Latin")
		);
	}
	
	@Override
	public List<Playlist> getPlaylists(Category category) {
		if (!"Mood".equals(category.getName())) {return new ArrayList<>();}
		
		return Arrays.asList(
				new Playlist("Walk Like A Badass"),
				new Playlist("Rage Beats"),
				new Playlist("Arab Mood Booster"),
				new Playlist("Sunday Stroll")
		);
	}
}
