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
        return !modelDescription.equalsIgnoreCase("");
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
