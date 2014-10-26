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

package domain;

/**
 * Created by Mateusz Szczap on 19/10/14.
 */
public class VehicleImage {

    private int vehicleId;
    private String imageUrl;

    public VehicleImage() {
    }

    public VehicleImage(int vehicleId, String imageUrl) {
        this.vehicleId = vehicleId;
        this.imageUrl = imageUrl;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "VehicleImage{" +
                "vehicleId=" + vehicleId +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

}
