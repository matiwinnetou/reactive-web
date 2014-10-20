package domain;

/**
 * Created by mati on 19/10/14.
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
