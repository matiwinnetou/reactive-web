/**
 * Apache 2
 * Copyright 2014 The Apache Software Foundation
 *
 * This product includes software developed at
 * The Apache Software Foundation (http://www.apache.org/).
 */
package utils;

import com.google.common.collect.Lists;
import domain.SearchResults;
import domain.VehicleData;
import domain.VehicleImage;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

/**
 * Created by Mateusz Szczap on 19/10/14.
 */
public class FakeServiceClient {

    public static F.Promise<SearchResults> callSearchResults(final int vehicleId, final int secsDelayed, boolean boom) {
        return wsCallGet("searchResults", vehicleId, secsDelayed, boom)
                .map(response -> response.getBody())
                .map(str -> Json.fromJson(Json.parse(str), SearchResults.class))
                .recover(t -> new SearchResults("No results found!", 0, Lists.newArrayList()));
    }

    public static F.Promise<VehicleData> callVehicleData(final int vehicleId, final int secsDelayed, boolean boom) {
        return wsCallGet("vehicleData", vehicleId, secsDelayed, boom)
                .map(response -> response.getBody())
                .map(str -> Json.fromJson(Json.parse(str), VehicleData.class))
                .recover(t -> new VehicleData());
    }

    public static F.Promise<VehicleImage> callVehicleImage(final int vehicleId, final int secsDelayed, boolean boom) {
        return wsCallGet("vehicleImage", vehicleId, secsDelayed, boom)
                .map(response -> response.getBody())
                .map(str -> Json.fromJson(Json.parse(str), VehicleImage.class))
                .recover(t -> new VehicleImage());
    }

    private static F.Promise<WSResponse> wsCallGet(final String type, final int vehicleId, final int secsDelayed, final boolean boom) {
        return WS.url(createUrl(type, vehicleId))
                .setQueryParameter("secsDelayed", String.valueOf(secsDelayed))
                .setQueryParameter("boom", String.valueOf(boom))
                .setTimeout(timeoutInSecs())
                .get();
    }

    private static String createUrl(final String name, final int vehicleId) {
        return String.format("http://localhost:9000/mock/%s/%d", name, vehicleId);
    }

    private static int timeoutInSecs() {
        return 10 * 1000;
    }

}
