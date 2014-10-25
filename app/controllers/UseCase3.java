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
        final HtmlStream modelDesc = ModelDescPagelet.stream();
        final HtmlStream miniSrp = MiniSrpPagelet.stream();

        final HtmlStream modelDescPagelet = Pagelet.renderStream(modelDesc, "modelDescP");
        final HtmlStream miniSrpPagelet = Pagelet.renderStream(miniSrp, "miniSrpP");

        final HtmlStream bigPipe = HtmlStream.interleave(Lists.newArrayList(modelDescPagelet, miniSrpPagelet));

        final HtmlStream out = (HtmlStream) views.stream.usecase3.render(HtmlStream.empty(), HtmlStream.empty(), bigPipe);

        return ok(out.toChunks());
    }

}
