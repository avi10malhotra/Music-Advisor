package advisor;

import com.google.gson.JsonArray;

public class AdviseCategories implements IAdvisor {
	
	@Override
	public void advise(JsonArray items) {
		items.forEach(item -> {
			var category = item.getAsJsonObject();
			var categoryName = category.get("name").getAsString();
			System.out.println(categoryName);
		});
	}
}