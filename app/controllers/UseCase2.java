package controllers;

import pagelets.MiniSrpPagelet;
import pagelets.ModelDescPagelet;
import play.mvc.Controller;
import play.mvc.Result;
import ui.HtmlStream;

/**
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase2 extends Controller {

    public static Result index() {
        final HtmlStream modelDesc = ModelDescPagelet.stream();
        final HtmlStream miniSrp = MiniSrpPagelet.stream();

        final HtmlStream stream = (HtmlStream) views.stream.usecase2.render(modelDesc, miniSrp);

        return ok(stream.toChunks());
    }

}
