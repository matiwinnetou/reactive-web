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
public class VehicleData {

    private int vehicleId = 0;
    private String make = "";
    private String model = "";
    private String variant = "";
    private String cubicCapacity = "";
    private String modelDescription = "";

    public VehicleData() {
    }

    public VehicleData(final int vehicleId, String make, String model, final String variant, String cubicCapacity, String modelDescription) {
        this.vehicleId = vehicleId;
        this.make = make;
        this.model = model;
        this.variant = variant;
        this.cubicCapacity = cubicCapacity;
        this.modelDescription = modelDescription;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getVariant() {
        return variant;
    }

    public String getCubicCapacity() {
        return cubicCapacity;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    @Override
    public String toString() {
        return "VehicleData{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", cubicCapacity='" + cubicCapacity + '\'' +
                ", modelDescription='" + modelDescription + '\'' +
                '}';
    }

}
