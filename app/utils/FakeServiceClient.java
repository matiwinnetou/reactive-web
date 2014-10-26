/*
 * Copyright (c) 2014 Mateusz Szczap
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package utils;

import com.google.common.collect.ImmutableList;
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
                .recover(t -> new SearchResults("keine Ergebnisse gefunden!", 0, ImmutableList.of()));
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
