/**
 * Apache 2
 * Copyright 2014 The Apache Software Foundation
 *
 * This product includes software developed at
 * The Apache Software Foundation (http://www.apache.org/).
 */
package controllers;

import pagelets.MiniSrpPagelet;
import pagelets.ModelDescPagelet;
import play.mvc.Controller;
import play.mvc.Result;
import ui.HtmlStream;

/**
 * Demonstrates example of using http 1.1 streaming with views and pagelets
 *
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase2 extends Controller {

    /**
     * Streaming best case scenario, model description pagelet responds and is flushed, then MiniSrp is flushed
     */
    public static Result index_a() {
        final HtmlStream modelDesc = ModelDescPagelet.stream(1, 1, false);
        final HtmlStream miniSrp = MiniSrpPagelet.stream(3, false);

        final HtmlStream stream = (HtmlStream) views.stream.usecase2.render(modelDesc, miniSrp);

        return ok(stream.toChunks());
    }

    /**
     * Streaming worst case scenario, ModelDescription pagelet responds late and CANNOT be flushed, MiniSrp is flushed late
     * even though it could be flushed earlier since web service responded very fast
     */
    public static Result index_b() {
        final HtmlStream modelDesc = ModelDescPagelet.stream(1, 1, false);
        final HtmlStream miniSrp = MiniSrpPagelet.stream(0, false);

        final HtmlStream stream = (HtmlStream) views.stream.usecase2.render(modelDesc, miniSrp);

        return ok(stream.toChunks());
    }

}
