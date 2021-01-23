package advisor;

import java.io.IOException;
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
	
	@Override
	public void startInteraction() {
		
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
				authorize();
				authorized = true;
			} else if (!authorized) 
				System.out.println("Please, provide access for application.");
			// Executes the 'service' corresponding to user's command
			else {
				try { executeService(option, inputs); }
				catch (IOException | InterruptedException e) { e.printStackTrace(); }
			}
		} while (option != EOption.Exit);
	}
	
	@Override
	public void executeService(EOption option, String[] inputs) throws IOException, InterruptedException{
		switch (option) {
			case New:
				System.out.println("---NEW RELEASES---");
				service.getNew();
				break;
			
			case Featured:
				System.out.println("---FEATURED---");
				service.getFeatured();
				break;
			
			case Categories:
				System.out.println("---CATEGORIES---");
				service.getCategories();
				break;
			
			case Playlists:
				if (inputs.length < 2 ) {
					System.out.println("---NO CATEGORY---");
					return;
				}
				String playlistName = formatName(inputs);
				
				System.out.println("---" + playlistName.toUpperCase() + "PLAYLISTS---");
				service.getPlaylists(playlistName.trim());
				break;
			
			case Exit:
				System.out.println("---GOODBYE!---");
				break;
			
			default: System.out.println("---INVALID OPTION---");
		}
		
	}
	
	private void authorize() {
		Authorization auth = new Authorization();
		auth.createHttpServer();
		auth.authorizeRequest();
	}
	
	private String formatName(String[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < arr.length; i++)
			sb.append(arr[i]).append(" ");
		return sb.toString();
	}
}
