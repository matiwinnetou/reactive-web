package pagelets;

import play.libs.F;
import play.mvc.Controller;
import ui.HtmlStream;

/**
 * Created by mati on 19/10/14.
 */
public class MiniSrpPagelet extends Controller {

    public static F.Promise<MiniSrpPageletModel> createModel() {
        return F.Promise.pure(MiniSrpPageletModel.EMPTY);
    }

    public static HtmlStream stream() {
        return HtmlStream.apply(createModel().map(model -> views.html.modelDesc.modelDescPagelet.render(model.getTitle(), model.getCount(), model.getItems())));
    }

}
