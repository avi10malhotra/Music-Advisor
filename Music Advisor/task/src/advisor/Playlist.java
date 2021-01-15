package advisor;

public class Playlist {
	private final String name;
	
	public Playlist(String name) { this.name = name; }
	
	public String getName() { return name; }
	
	@Override
	public String toString() { return name; }
}
