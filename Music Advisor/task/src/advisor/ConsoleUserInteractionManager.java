package advisor;

import java.util.Scanner;

public class ConsoleUserInteractionManager implements IUserInteractionManager {
	private static ConsoleUserInteractionManager instance;
	private static final Scanner sc = new Scanner(System.in);
	
	private final IService service;
	
	private ConsoleUserInteractionManager(IService service) {
		this.service = service;
	}
	
	// The user-program interaction is implemented via a singleton
	public static ConsoleUserInteractionManager createInstance(IService service) {
		
		if (instance == null) {
			synchronized (ConsoleUserInteractionManager.class) {
				if (instance == null) {
					instance = new ConsoleUserInteractionManager(service);
				}
			}
		}
		return instance;
	}
	
	public static ConsoleUserInteractionManager getInstance() {return instance;}
	
	@Override
	public void startInteraction() {
		
		EOption option;
		
		do {
			// Fetches user input
			String[] inputs = sc.nextLine().split(" ");
			option = EOption.fromName(inputs[0]);
			
			// Base checking for proper usage
			if (option == null) {
				System.out.println("---INVALID OPTION---");
				continue;
			}
			
			// Executes the 'service' corresponding to user's command
			switch (option) {
				case New:
					System.out.println("---NEW RELEASES---");
					service.getNew().forEach(System.out::println);
					break;
					
				case Featured:
					System.out.println("---FEATURED---");
					service.getFeatured().forEach(System.out::println);
					break;
					
				case Categories:
					System.out.println("---CATEGORIES---");
					service.getCategories().forEach(System.out::println);
					break;
				
				case Playlists:
					if (inputs.length < 2 ) {
						System.out.println("---NO CATEGORY---");
						continue;
					}
				
					System.out.println("---" + inputs[1].toUpperCase() + "PLAYLISTS---");
					service.getPlaylists(new Category(inputs[1])).forEach(System.out::println);
					break;
				
				case Exit:
					System.out.println("---GOODBYE!---");
					break;
				
				default: System.out.println("---INVALID OPTION---");
			}
		} while (option != EOption.Exit);
		
	}
}
