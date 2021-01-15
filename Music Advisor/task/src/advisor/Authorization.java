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

public class Authorization {
	private static String SERVER_PATH = "";
	
	private static final String REDIRECT_URI = "http://localhost:8080";
	
	private static final String CLIENT_ID = "b5c588b20d9840faa9cd29b449188f7b";
	private static final String CLIENT_SECRET = "92eab706498245bcba8bf3a484e25b78";
	
	private static String ACCESS_TOKEN = "";
	private static String AUTH_CODE = "";
	
	public Authorization(String accessPoint) {
		SERVER_PATH = accessPoint;
	}
	
	public void createHttpServer() throws NullPointerException {
		String URI = SERVER_PATH + "/authorize"
				+ "?client_id=" + CLIENT_ID
				+ "&redirect_uri=" + REDIRECT_URI
				+ "&response_type=code";
		System.out.println("use this link to request the access code:\n" + URI);
		
		try {
			HttpServer server = HttpServer.create();
			
			// Binds server with port 8080 and backlog 0
			server.bind(new InetSocketAddress(8080), 0);
			
			// Gets authorization code via HTTP handler
			server.createContext("/",
					exchange -> {
						String query = exchange.getRequestURI().getQuery();
						
						String request;
						if (query != null && query.contains("code")) {
							AUTH_CODE = query;//.substring(5);
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
			while (AUTH_CODE.isEmpty())
				Thread.sleep(10);
			server.stop(10);
		} catch (IOException | InterruptedException e) {
			System.out.println("Server error");
		}
		
	}
	
	void authorizeRequest() {
		System.out.println("making http request for access_token...");
		System.out.println("response:");
		
		// Builds HTTP request using authenticated instance fields
		HttpRequest request = HttpRequest.newBuilder()
                      .header("Content-Type", "application/x-www-form-urlencoded")
                      .uri(URI.create(SERVER_PATH + "/api/token"))
                      .POST(HttpRequest.BodyPublishers.ofString(
                      		"grant_type=authorization_code"
		                      + "&code=" + AUTH_CODE
		                      + "&client_id=" + CLIENT_ID
		                      + "&client_secret=" + CLIENT_SECRET
		                      + "&redirect_uri=" + REDIRECT_URI ))
                      .build();
		
		try {
			HttpClient client = HttpClient.newBuilder().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			if (response != null && response.body().contains("access_token"))
				parseAccessToken(response.body());
			
			assert response != null;
			System.out.println(response.body());
			System.out.println("\n--SUCCESS---");
		} catch (InterruptedException | IOException e) {
			System.out.println("Error response");
		}
	}
	
	private void parseAccessToken(String body) {
		JsonObject jo = JsonParser.parseString(body).getAsJsonObject();
		ACCESS_TOKEN = jo.get("access_token").getAsString();
	}
}
