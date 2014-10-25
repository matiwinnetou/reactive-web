/**
 * Apache 2
 * Copyright 2014 The Apache Software Foundation
 *
 * This product includes software developed at
 * The Apache Software Foundation (http://www.apache.org/).
 */
package controllers;

import com.google.common.collect.Lists;
import pagelets.MiniSrpPagelet;
import pagelets.ModelDescPagelet;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ui.HtmlStream;
import ui.Pagelet;
import utils.PageRenderingMode;
import utils.RequestParams;

/**
 * Demonstrates all rendering modes working together
 *
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase4 extends Controller {

    public static F.Promise<Result> index() {
        final HtmlStream modelDescStream = ModelDescPagelet.stream();
        final HtmlStream miniSrpStream = MiniSrpPagelet.stream();

        final PageRenderingMode pageRenderingMode = RequestParams.getPageRenderingMode(request());

        if (pageRenderingMode == PageRenderingMode.BIGPIPE) {
            final HtmlStream modelDescPagelet = Pagelet.renderStream(modelDescStream, "modelDescP");
            final HtmlStream miniSrpPagelet = Pagelet.renderStream(miniSrpStream, "miniSrpP");

            final HtmlStream bigPipe = HtmlStream.interleave(Lists.newArrayList(modelDescPagelet, miniSrpPagelet));

            final HtmlStream out = (HtmlStream) views.stream.usecase4.render(HtmlStream.empty(), HtmlStream.empty(), bigPipe);

            return F.Promise.pure(ok(out.toChunks()));
        }

        final HtmlStream stream = (HtmlStream) views.stream.usecase4.render(modelDescStream, miniSrpStream, HtmlStream.empty());

        if (pageRenderingMode == PageRenderingMode.CLASSIC) {
            return stream.foldJ().map(html -> ok(html));
        }

        if (pageRenderingMode == PageRenderingMode.CHUNKED) {
            return F.Promise.pure(ok(stream.toChunks()));
        }

        return F.Promise.pure(ok(HtmlStream.empty().toChunks()));
    }

}
