package advisor;

public class Main {
    public static void main(String[] args) {
        IService service = new Service();
        String accessPoint;
        if (args.length > 0 && "-access".equals(args[0]))
            accessPoint = args[1];
        else
            accessPoint = "https://accounts.spotify.com";
        
        
        // Instantiates and manages user interaction
        IUserInteractionManager manager = ConsoleUserInteractionManager.createInstance(service);
        manager.startInteraction(accessPoint);
        
    }
}
