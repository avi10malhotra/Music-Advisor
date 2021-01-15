package advisor;

public enum EOption {
	// Enum constants
	Auth("auth"),
	Categories("categories"),
	Exit("exit"),
	Featured("featured"),
	New("new"),
	Playlists("playlists");
	
	private final String name;
	
	EOption(String name) { this.name = name; }
	
	public String getName() { return this.name; }
	
	public static EOption fromName(String name) {
		for (EOption option : EOption.class.getEnumConstants()) {
			if (option.getName().contentEquals(name))
				return option;
		}
		return null;
	}
}
