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

import domain.SearchResults;
import play.libs.F;
import play.mvc.Controller;
import ui.HtmlStream;
import utils.FakeServiceClient;
import views.html.miniSrpPagelet;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mateusz Szczap on 19/10/14.
 */
public class MiniSrpPagelet extends Controller {

    /**
     * Invokes a fake service to return search results and creates a promise of MiniSrpPageletModel
     */
    public static F.Promise<MiniSrpPageletModel> createModel(final int vehicleId, final int searchDelayedInSecs, final boolean boom) {
        return FakeServiceClient.callSearchResults(vehicleId, searchDelayedInSecs, boom)
                .map(r -> new MiniSrpPageletModel(r.getTitle(), r.getCount(), convertItems(r.getItems())));
    }

    /**
     * Converts from search results specific items to MiniSRP items
     */
    private static List<MiniSrpPageletModel.Item> convertItems(final List<SearchResults.Item> items) {
        return items.stream().map(item -> convertItem(item)).collect(Collectors.toList());
    }

    /**
     * Converts from one item format to another
     */
    private static MiniSrpPageletModel.Item convertItem(final SearchResults.Item item) {
        final String title = item.getTitle();
        final int price = item.getPrice();
        final String imageUrl = item.getImageUrl();
        final String line1 = String.format("%s %s", item.getPostCode(), item.getCity());
        final String line2 = String.format("%s, %d", item.getFirstReg(), item.getMileage());
        final String line3 = String.format("%d kW (%d PS)", item.getPowerKw(), kwToHp(item.getPowerKw()));

        return new MiniSrpPageletModel.Item(title, price, imageUrl, line1, line2, line3, "");
    }

    /**
     * Returns a html stream representation of MiniSrp pagelet
     */
    public static HtmlStream stream(final int vehicleId, final int searchDelayedInSecs, final boolean boom) {
        return HtmlStream
                .apply(createModel(vehicleId, searchDelayedInSecs, boom)
                .map(m -> miniSrpPagelet.render(m.isEnabled(), m.getTitle(), m.getCount(), m.getItems())));
    }

    /**
     * Converts between kilowatts and horse power
     *
     * @param kw
     * @return horse power equivalent
     */
    private static long kwToHp(final long kw) {
        return Math.round(kw * 1.359);
    }

}
