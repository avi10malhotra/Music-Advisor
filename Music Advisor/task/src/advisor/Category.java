package advisor;

public class Category {
	private final String name;
	
	Category(String name) {this.name = name;}
	
	public String getName() { return name; }
	
	@Override
	public String toString() {return this.name;}
}
