package controllers;

import com.google.common.collect.Lists;
import pagelets.MiniSrpPagelet;
import pagelets.ModelDescPagelet;
import play.mvc.Controller;
import play.mvc.Result;
import ui.HtmlStream;
import ui.Pagelet;

/**
 * Demonstrates BigPipe mode only
 *
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase3 extends Controller {

    public static Result index() {
        final HtmlStream modelDesc = Pagelet.renderStream(ModelDescPagelet.stream().foldJ(), "modelDescP");
        final HtmlStream miniSrp = Pagelet.renderStream(MiniSrpPagelet.stream().foldJ(), "miniSrpP");

        final HtmlStream bigPipe = HtmlStream.interleave(Lists.newArrayList(modelDesc, miniSrp));

        final HtmlStream out = (HtmlStream) views.stream.usecase3.render(HtmlStream.empty(), HtmlStream.empty(), bigPipe);

        return ok(out.toChunks());
    }

}
