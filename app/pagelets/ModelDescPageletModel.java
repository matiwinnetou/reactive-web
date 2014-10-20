package pagelets;

/**
 * Created by mati on 19/10/14.
 */
public class ModelDescPageletModel {

    private String title = "";
    private String imageUrl = "";
    private String modelDescription = "";

    public static final ModelDescPageletModel EMPTY = new ModelDescPageletModel();

    public ModelDescPageletModel(String title, String imageUrl, String modelDescription) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.modelDescription = modelDescription;
    }

    public ModelDescPageletModel() {
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    @Override
    public String toString() {
        return "ModelDescPageletModel{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", modelDescription='" + modelDescription + '\'' +
                '}';
    }

}
