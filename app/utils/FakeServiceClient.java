package utils;

import domain.SearchResults;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;

public class FakeServiceClient {

    public static F.Promise<String> call(final String serviceName) {
        return WS.url("http://localhost:9000/mock/" + serviceName).get().map(response -> response.getBody());
    }

    public static F.Promise<SearchResults> callSearchResults(final int vehicleId) {
        return WS.url("http://localhost:9000/mock/searchResults/" + String.valueOf(vehicleId)).get()
                .map(response -> response.getBody())
                .map(str -> Json.fromJson(Json.parse(str), SearchResults.class));
    }

}
