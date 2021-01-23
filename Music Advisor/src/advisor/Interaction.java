package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Interaction {
	private static final Interaction instance = new Interaction();

	private Interaction(){}

	public static Interaction getInstance() { return instance; }
	
	public void serverInteraction() throws IOException, InterruptedException {
		HttpServer server = HttpServer.create();
		
		// Binds server with port 8080 and backlog 0
		server.bind(new InetSocketAddress(8080), 0);
		
		// Gets authorization code via HTTP handler
		server.createContext("/",
				exchange -> {
					String query = exchange.getRequestURI().getQuery();
					
					String request;
					if (query != null && query.contains("code")) {
						Info.AUTH_CODE = query.substring(5);
						System.out.println("code received");
						request = "Got the code. Return back to your program.";
					} else
						request = "Authorization code not found. Try again.";
					
					exchange.sendResponseHeaders(200, request.length());
					exchange.getResponseBody().write(request.getBytes());
					exchange.getResponseBody().close();
				});
		
		server.start();
		System.out.println("\nwaiting for code...");
		while (Info.AUTH_CODE.isEmpty())
			Thread.sleep(10);
		server.stop(10);
	}
	
	public String createRequest(String requestedURL) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.header("Authorization", "Bearer " + Info.ACCESS_TOKEN)
				.uri(URI.create(Info.API_SERVER_PATH + requestedURL))
				.GET()
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
		
	}
	
	public void handleRequest(HttpRequest request) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newBuilder().build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		if (response != null && response.body().contains("access_token"))
			parseAccessToken(response.body());
		
		assert response != null;
		System.out.println(response.body());
	}
	
	private void parseAccessToken(String body) {
		JsonObject jo = JsonParser.parseString(body).getAsJsonObject();
		Info.ACCESS_TOKEN = jo.get("access_token").getAsString();
	}
}
