package advisor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

public class Authorization {
	private final Interaction httpInteraction = Interaction.getInstance();
	
	public void createHttpServer() throws NullPointerException {
		String URI = Info.AUTH_SERVER_PATH + "/authorize"
				+ "?client_id=" + Info.CLIENT_ID
				+ "&redirect_uri=" + Info.REDIRECT_URI
				+ "&response_type=code";
		System.out.println("use this link to request the access code:\n" + URI);
		
		try {
			httpInteraction.serverInteraction();
		} catch (IOException | InterruptedException e) {
			System.out.println("Server error");
		}
		
	}
	
	public void authorizeRequest() {
		System.out.println("making http request for access_token...");
		System.out.println("response:");
		
		// Builds HTTP request using authenticated instance fields
		HttpRequest request = HttpRequest.newBuilder()
                      .header("Content-Type", "application/x-www-form-urlencoded")
                      .uri(URI.create(Info.AUTH_SERVER_PATH + "/api/token"))
                      .POST(HttpRequest.BodyPublishers.ofString(
                      		"grant_type=authorization_code"
		                      + "&code=" + Info.AUTH_CODE
		                      + "&client_id=" + Info.CLIENT_ID
		                      + "&client_secret=" + Info.CLIENT_SECRET
		                      + "&redirect_uri=" + Info.REDIRECT_URI ))
                      .build();
		
		try {
			httpInteraction.handleRequest(request);
			System.out.println("\n--SUCCESS---");
		} catch (InterruptedException | IOException e) {
			System.out.println("Error response");
		}
	}
	
}
