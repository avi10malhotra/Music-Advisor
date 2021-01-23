package advisor;

import java.io.IOException;

public interface IService {
	void getFeatured() throws IOException, InterruptedException;
	void getNew() throws IOException, InterruptedException;
	void getCategories() throws IOException, InterruptedException;
	void getPlaylists(String category) throws IOException, InterruptedException;
	
}
