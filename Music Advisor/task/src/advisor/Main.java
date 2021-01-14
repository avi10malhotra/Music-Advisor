package advisor;

public class Main {
    public static void main(String[] args) {
        IService service = new Service();
        
        // Instantiates and manages user interaction
        IUserInteractionManager manager = ConsoleUserInteractionManager.createInstance(service);
        manager.startInteraction();
        
    }
}
