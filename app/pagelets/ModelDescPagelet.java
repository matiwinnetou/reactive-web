/**
 * Apache 2
 * Copyright 2014 The Apache Software Foundation
 *
 * This product includes software developed at
 * The Apache Software Foundation (http://www.apache.org/).
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

    public static F.Promise<ModelDescPageletModel> createModel() {
        final F.Promise<VehicleData> vehicleDataP = FakeServiceClient.callVehicleData(1);
        final F.Promise<VehicleImage> vehicleImageP = FakeServiceClient.callVehicleImage(1);

        return Promises.zip(vehicleDataP, vehicleImageP, (vehicleData, vehicleImage) -> {
            final String title = String.format("%s %s %s", vehicleData.getMake(), vehicleData.getModel(), vehicleData.getVariant());
            final String imageUrl = vehicleImage.getImageUrl();
            final String modelDesc = vehicleData.getModelDescription();

            return new ModelDescPageletModel(title, imageUrl, modelDesc);
        });
    }

    public static HtmlStream stream() {
        return HtmlStream.apply(createModel().map(model
                -> views.html.modelDescPagelet.render(model.isEnabled(), model.getTitle(), model.getImageUrl(), model.getModelDescription())));
    }

}
