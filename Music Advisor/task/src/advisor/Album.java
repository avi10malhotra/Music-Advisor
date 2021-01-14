package advisor;

import java.util.Collections;
import java.util.List;

public class Album {
	private final String title;
	private final List<Artist> artists;
	
	Album(String title, List<Artist> artists) {
		this.title = title;
		this.artists = artists;
	}
	
	// Second constructor for cases without any co-artists
	Album(String title, Artist artist) {
		this.title = title;
		this.artists = Collections.singletonList(artist);
	}
	
	public String getTitle() {return title;}
	
	public List<Artist> getArtists() {return artists;}
	
	// Appropriate formatting as per the requirement
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(" [");
		
		if (artists != null) {
			int i = 1;
			for (Artist artist : artists) {
				str.append(artist);
				if (i++ < artists.size())
					str.append(", ");
			}
		}
		
		return str.append(']').toString();
	}
}

