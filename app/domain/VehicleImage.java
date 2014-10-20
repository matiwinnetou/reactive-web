package domain;

/**
 * Created by mati on 19/10/14.
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
