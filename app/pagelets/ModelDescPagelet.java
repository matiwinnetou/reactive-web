package pagelets;

import play.mvc.Controller;
import ui.HtmlStream;

/**
 * Created by mati on 19/10/14.
 */
public class ModelDescPagelet extends Controller {

    public static HtmlStream stream() {
        return HtmlStream.empty();
    }

}
