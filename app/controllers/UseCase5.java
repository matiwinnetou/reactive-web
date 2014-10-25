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
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ui.HtmlStream;

/**
 * Demonstrates reactive fallback - based on failed or timed out response
 *
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase5 extends Controller {

    /**
     * Reactive fallback - timeout case
     */
    public static F.Promise<Result> index_a() {
        final HtmlStream modelDescStream = ModelDescPagelet.stream(1, 0, false);
        final HtmlStream miniSrpStream = MiniSrpPagelet.stream(15, false);

        final HtmlStream stream = (HtmlStream) views.stream.usecase5.render(modelDescStream, miniSrpStream);

        return F.Promise.pure(ok(stream.toChunks()));
    }

    /**
     * Reactive fallback - exception case
     */
    public static F.Promise<Result> index_b() {
        final HtmlStream modelDescStream = ModelDescPagelet.stream(1, 0, false);
        final HtmlStream miniSrpStream = MiniSrpPagelet.stream(1, true);

        final HtmlStream stream = (HtmlStream) views.stream.usecase5.render(modelDescStream, miniSrpStream);

        return F.Promise.pure(ok(stream.toChunks()));
    }

}
