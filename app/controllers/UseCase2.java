package controllers;

import pagelets.MiniSrpPagelet;
import pagelets.ModelDescPagelet;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ui.HtmlStream;
import utils.PageRenderingMode;
import utils.RequestParams;

/**
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase2 extends Controller {

    public static F.Promise<Result> index() {
        final HtmlStream modelDesc = ModelDescPagelet.stream();
        final HtmlStream miniSrp = MiniSrpPagelet.stream();

        return render((HtmlStream) views.stream.usecase2.render(modelDesc, miniSrp));
    }

    private static F.Promise<Result> render(final HtmlStream stream) {
        if (RequestParams.getPageRenderingMode(request()) == PageRenderingMode.CLASSIC) {
            return stream.foldJ().map(html -> ok(html));
        }

        return F.Promise.pure(ok(stream.toChunks()));
    }


}
