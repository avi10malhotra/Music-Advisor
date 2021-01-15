package advisor;

public interface IUserInteractionManager {
	void startInteraction(String accessPoint);
	void executeService(EOption option, String[] inputs);
}
