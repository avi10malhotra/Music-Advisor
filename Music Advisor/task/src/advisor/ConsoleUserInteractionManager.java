package advisor;

import java.util.Scanner;

import static advisor.EOption.Auth;

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
	public void startInteraction(String accessPoint) {
		
		EOption option;
		boolean authorized = false;
		
		do {
			// Fetches user input
			String[] inputs = sc.nextLine().split(" ");
			option = EOption.fromName(inputs[0]);
			
			// Base checking for proper usage
			if (option == null) {
				System.out.println("---INVALID OPTION---");
				continue;
			}
			
			// Checks for OAuth protocol
			if (!authorized && option.equals(Auth)) {
				authorize(accessPoint);
				authorized = true;
			} else if (!authorized) 
				System.out.println("Please, provide access for application.");
			// Executes the 'service' corresponding to user's command
			else 
				executeService(option, inputs);
		} while (option != EOption.Exit);
	}
	
	@Override
	public void executeService(EOption option, String[] inputs) {
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
					return;
				}
				
				System.out.println("---" + inputs[1].toUpperCase() + "PLAYLISTS---");
				service.getPlaylists(new Category(inputs[1])).forEach(System.out::println);
				break;
			
			case Exit:
				System.out.println("---GOODBYE!---");
				break;
			
			default: System.out.println("---INVALID OPTION---");
		}
		
	}
	
	private void authorize(String accessPoint) {
		Authorization auth = new Authorization(accessPoint);
		auth.createHttpServer();
		auth.authorizeRequest();
	}
}
