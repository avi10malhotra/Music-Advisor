package advisor;

import java.io.IOException;

public interface IUserInteractionManager {
	void startInteraction();
	void executeService(EOption option, String[] inputs) throws IOException, InterruptedException;
}
