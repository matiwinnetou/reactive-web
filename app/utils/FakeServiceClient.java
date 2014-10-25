/**
 * Apache 2
 * Copyright 2014 The Apache Software Foundation
 *
 * This product includes software developed at
 * The Apache Software Foundation (http://www.apache.org/).
 */
package utils;

import domain.SearchResults;
import domain.VehicleData;
import domain.VehicleImage;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;

public class FakeServiceClient {

    public static F.Promise<SearchResults> callSearchResults(final int vehicleId) {
        return WS.url(createUrl("searchResults", vehicleId)).setTimeout(timeoutInSecs()).get()
                .map(response -> response.getBody())
                .map(str -> Json.fromJson(Json.parse(str), SearchResults.class))
                .recover(t -> new SearchResults());
    }

    public static F.Promise<VehicleData> callVehicleData(final int vehicleId) {
        return WS.url(createUrl("vehicleData", vehicleId)).setTimeout(timeoutInSecs()).get()
                .map(response -> response.getBody())
                .map(str -> Json.fromJson(Json.parse(str), VehicleData.class))
                .recover(t -> new VehicleData());
    }

    public static F.Promise<VehicleImage> callVehicleImage(final int vehicleId) {
        return WS.url(createUrl("vehicleImage", vehicleId)).setTimeout(timeoutInSecs()).get()
                .map(response -> response.getBody())
                .map(str -> Json.fromJson(Json.parse(str), VehicleImage.class))
                .recover(t -> new VehicleImage());
    }

    private static String createUrl(final String name, final int vehicleId) {
        return String.format("http://localhost:9000/mock/%s/%d", name, vehicleId);
    }

    private static int timeoutInSecs() {
        return 10 * 1000;
    }

}
