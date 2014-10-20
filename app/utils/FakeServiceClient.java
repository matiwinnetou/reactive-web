package utils;

import domain.SearchResults;
import domain.VehicleData;
import domain.VehicleImage;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;

public class FakeServiceClient {

    public static F.Promise<SearchResults> callSearchResults(final int vehicleId) {
        return WS.url(createUrl("searchResults", vehicleId)).get()
                .map(response -> response.getBody())
                .map(str -> Json.fromJson(Json.parse(str), SearchResults.class));
    }

    public static F.Promise<VehicleData> callVehicleData(final int vehicleId) {
        return WS.url(createUrl("vehicleData", vehicleId)).get()
                .map(response -> response.getBody())
                .map(str -> Json.fromJson(Json.parse(str), VehicleData.class));
    }

    public static F.Promise<VehicleImage> callVehicleImage(final int vehicleId) {
        return WS.url(createUrl("vehicleImage", vehicleId)).get()
                .map(response -> response.getBody())
                .map(str -> Json.fromJson(Json.parse(str), VehicleImage.class));
    }

    private static String createUrl(final String name, final int vehicleId) {
        return String.format("http://localhost:9000/mock/%s/%d", name, vehicleId);
    }

}
