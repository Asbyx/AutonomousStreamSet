package io.github.asbyx.autonomousstreamset.queries;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class GraphQLManager {
	private static final String API_URL = "https://api.start.gg/gql/alpha";
	private static String API_TOKEN = null;
	private static final OkHttpClient client = new OkHttpClient();

	public static void setApiToken(String apiToken) {
		API_TOKEN = apiToken;
	}

	public static String executeGraphQLQuery(String query, Map<String, String> variables) throws IOException {
		assert API_TOKEN != null;

		// Create JSON object for the query and variables
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("query", query);

		// Create JSON object for variables
		JSONObject variablesJson = new JSONObject(variables);

		// Add variables to the main JSON object
		jsonObject.put("variables", variablesJson);

		// Create request body
		RequestBody body = RequestBody.create(
				jsonObject.toString(),
				MediaType.get("application/json; charset=utf-8")
		);

		// Create the request with headers
		Request request = new Request.Builder()
				.url(API_URL)
				.post(body)
				.addHeader("Authorization", "Bearer " + API_TOKEN)
				.build();

		// Execute the request and get the response
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				JSONObject error = new JSONObject(response.body().string());
				String errorMsg = error.getString("message");

				throw new IOException("Error: " + errorMsg);
			}

			// Return the response body as a string
			return response.body().string();
		}
	}
}

