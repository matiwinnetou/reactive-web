/**
 * Apache 2
 * Copyright 2014 The Apache Software Foundation
 *
 * This product includes software developed at
 * The Apache Software Foundation (http://www.apache.org/).
 */
package pagelets;

/**
 * Created by Mateusz Szczap on 19/10/14.
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

    public boolean isEnabled() {
        return !title.equalsIgnoreCase("");
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
