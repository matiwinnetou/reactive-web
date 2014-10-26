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

package pagelets;

import domain.VehicleData;
import domain.VehicleImage;
import play.libs.F;
import play.mvc.Controller;
import ui.HtmlStream;
import utils.FakeServiceClient;
import utils.Promises;

/**
 * Created by Mateusz Szczap on 19/10/14.
 */
public class ModelDescPagelet extends Controller {

    public static F.Promise<ModelDescPageletModel> createModel(final int vehicleDataCallDelayedInSecs,
                                                               final int vehicleImageCallDelayedInSecs,
                                                               final boolean boom) {
        final F.Promise<VehicleData> vehicleDataP = FakeServiceClient.callVehicleData(vehicleDataCallDelayedInSecs, vehicleImageCallDelayedInSecs, boom);
        final F.Promise<VehicleImage> vehicleImageP = FakeServiceClient.callVehicleImage(vehicleImageCallDelayedInSecs, vehicleImageCallDelayedInSecs, boom);

        return Promises.zip(vehicleDataP, vehicleImageP, (vehicleData, vehicleImage) -> createModel2(vehicleData, vehicleImage));
    }

    private static ModelDescPageletModel createModel2(final VehicleData vehicleData, final VehicleImage vehicleImage) {
        final String title = String.format("%s %s %s", vehicleData.getMake(), vehicleData.getModel(), vehicleData.getVariant());
        final String imageUrl = vehicleImage.getImageUrl();
        final String modelDesc = vehicleData.getModelDescription();

        return new ModelDescPageletModel(title, imageUrl, modelDesc);
    }

    public static HtmlStream stream(final int vehicleDataCallDelayedInSecs,
                                    final int vehicleImageCallDelayedInSecs,
                                    final boolean boom) {
        final F.Promise<ModelDescPageletModel> miniSrpModelP = createModel(vehicleDataCallDelayedInSecs, vehicleImageCallDelayedInSecs, boom);

        return HtmlStream.apply(miniSrpModelP.map(m -> views.html.modelDescPagelet.render(m.isEnabled(), m.getTitle(), m.getImageUrl(), m.getModelDescription())));
    }

}
