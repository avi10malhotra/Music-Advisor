package advisor;

public class Main {
    public static void main(String[] args) {
        IService service = new Service();
        
        if (args.length > 0 && "-access".equals(args[0]))
            Info.AUTH_SERVER_PATH = args[1];
        else
            Info.AUTH_SERVER_PATH = "https://accounts.spotify.com";
        
        if (args.length > 2 && "-resource".equals(args[2]))
            Info.API_SERVER_PATH = args[3];
        else
            Info.API_SERVER_PATH = "https://api.spotify.com";
        
        // Instantiates and manages user interaction
        IUserInteractionManager manager = ConsoleUserInteractionManager.createInstance(service);
        manager.startInteraction();
        
    }
}
