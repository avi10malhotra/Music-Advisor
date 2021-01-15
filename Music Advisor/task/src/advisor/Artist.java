package advisor;

public class Artist {
	private final String name;
	
	Artist(String name) {this.name = name;}
	
	public String getName() {return this.name;}
	
	@Override
	public String toString() {return this.name;}
}
