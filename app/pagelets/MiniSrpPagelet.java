/**
 * Apache 2
 * Copyright 2014 The Apache Software Foundation
 *
 * This product includes software developed at
 * The Apache Software Foundation (http://www.apache.org/).
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
     * Creates a promise of a MiniSrpPageletModel
     *
     * @return a promise of model
     */
    public static F.Promise<MiniSrpPageletModel> createModel() {
        //invoke a fake client to get search results for vehicle id = 1
        return FakeServiceClient.callSearchResults(1).map(r -> new MiniSrpPageletModel(r.getTitle(), r.getCount(), convert(r.getItems())));
    }

    private static List<MiniSrpPageletModel.Item> convert(final List<SearchResults.Item> items) {
        return items.stream().map(item -> {
            final String title = item.getTitle();
            final int price = item.getPrice();
            final String imageUrl = item.getImageUrl();
            final String line1 = String.format("%s %s", item.getPostCode(), item.getCity());
            final String line2 = String.format("%s, %d", item.getFirstReg(), item.getMileage());
            final String line3 = String.format("%d kW (%d PS)", item.getPowerKw(), kwToHp(item.getPowerKw()));

            return new MiniSrpPageletModel.Item(title, price, imageUrl, line1, line2, line3, "");
        }).collect(Collectors.toList());
    }

    /**
     * Returns a html stream representation of model
     * @return
     */
    public static HtmlStream stream() {
        return HtmlStream.apply(createModel()
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
