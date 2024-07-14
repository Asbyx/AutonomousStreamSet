package queries;

import io.github.asbyx.autonomousstreamset.queries.GraphQLManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GraphQLManagerTest {
	@Test
	public void testExpiredToken() {
		GraphQLManager.setApiToken("86631a4b3565f496986d3136b4f63549");

		String query = "query getEventId($tournament: String){\n" +
				"          tournament(slug: $tournament){\n" +
				"            id\n" +
				"            name\n" +
				"            events {\n" +
				"                slug\n" +
				"                id\n" +
				"            }\n" +
				"          }\n" +
				"        }";

		Map<String, String> variables = Map.of("tournament", "pound-2022");

		try {
			String response = GraphQLManager.executeGraphQLQuery(query, variables);
			System.out.println(response);
			assert false;
		} catch (IOException e) {
			e.printStackTrace();
			assertEquals("Error: Token has expired.", e.getMessage());
		}
	}

	@Test
	public void testInvalidToken() {
		GraphQLManager.setApiToken("000000000000000000000000000000");

		String query = "query getEventId($tournament: String){\n" +
				"          tournament(slug: $tournament){\n" +
				"            id\n" +
				"            name\n" +
				"            events {\n" +
				"                slug\n" +
				"                id\n" +
				"            }\n" +
				"          }\n" +
				"        }";

		Map<String, String> variables = Map.of("tournament", "pound-2022");

		try {
			String response = GraphQLManager.executeGraphQLQuery(query, variables);
			System.out.println(response);
			assert false;
		} catch (IOException e) {
			e.printStackTrace();
			assertEquals("Error: Invalid authentication token", e.getMessage());
		}
	}

	@Test
	public void testCorrect() {
		GraphQLManager.setApiToken("239d4a6cf1f26bb860e673f023dc27b9");

		String query = "query getEventId($tournament: String){\n" +
				"          tournament(slug: $tournament){\n" +
				"            id\n" +
				"            name\n" +
				"            events {\n" +
				"                slug\n" +
				"                id\n" +
				"            }\n" +
				"          }\n" +
				"        }";

		Map<String, String> variables = Map.of("tournament", "pound-2022");

		try {
			String response = GraphQLManager.executeGraphQLQuery(query, variables);
			assertEquals("{\"data\":{\"tournament\":{\"id\":419719,\"name\":\"Pound 2022\",\"events\":[{\"slug\":\"tournament\\/pound-2022\\/event\\/ultimate-singles-2\",\"id\":679057},{\"slug\":\"tournament\\/pound-2022\\/event\\/melee-singles-2\",\"id\":679049},{\"slug\":\"tournament\\/pound-2022\\/event\\/ultimate-squad-strike-1\",\"id\":680312},{\"slug\":\"tournament\\/pound-2022\\/event\\/melee-teams\",\"id\":679050}]}},\"extensions\":{\"cacheControl\":{\"version\":1,\"hints\":[{\"path\":[\"tournament\"],\"maxAge\":300,\"scope\":\"PRIVATE\"}]},\"queryComplexity\":5},\"actionRecords\":[]}", response);
		} catch (IOException e) {
			e.printStackTrace();
			// make the test fail
			assert false;
		}
	}
}
