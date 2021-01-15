package advisor;

import java.util.List;

public interface IService {
	List<Playlist> getFeatured();
	List<Album> getNew();
	List<Category> getCategories();
	List<Playlist> getPlaylists(Category category);
	
}
