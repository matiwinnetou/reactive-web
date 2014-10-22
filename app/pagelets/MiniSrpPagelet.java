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
 * Created by mati on 19/10/14.
 */
public class MiniSrpPagelet extends Controller {

    public static F.Promise<MiniSrpPageletModel> createModel() {
        return FakeServiceClient
                .callSearchResults(1)
                .map(results -> {
                    final String title = results.getTitle();
                    final int count = results.getCount();
                    final List<MiniSrpPageletModel.Item> items = convert(results.getItems());

                    return new MiniSrpPageletModel(title, count, items);
                });
    }

    private static List<MiniSrpPageletModel.Item> convert(final List<SearchResults.Item> items) {
        return items.stream()
                .map(item -> {
                    final String title = item.getTitle();
                    final int price = item.getPrice();
                    final String imageUrl = item.getImageUrl();
                    final String line1 = String.format("%s %s", item.getPostCode(), item.getCity());
                    final String line2 = String.format("%s, %d", item.getFirstReg(), item.getMileage());
                    final String line3 = String.format("%d kW (%d PS)", item.getPowerKw(), Math.round(item.getPowerKw() * 1.359));

                    return new MiniSrpPageletModel.Item(title, price, imageUrl, line1, line2, line3, "");
                })
                .collect(Collectors.toList());
    }

    public static HtmlStream stream() {
        return HtmlStream.apply(createModel().map(model -> {
            final String title = model.getTitle();
            final int count = model.getCount();
            final List<MiniSrpPageletModel.Item> items = model.getItems();

            return miniSrpPagelet.render(model.isEnabled(), title, count, items);
        }));
    }

}
