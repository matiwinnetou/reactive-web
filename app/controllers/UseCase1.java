/**
 * Apache 2
 * Copyright 2014 The Apache Software Foundation
 *
 * This product includes software developed at
 * The Apache Software Foundation (http://www.apache.org/).
 */
package controllers;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
import ui.HtmlStream;

import java.util.concurrent.TimeUnit;

/**
 * This example demonstrates stream composition and streaming (http 1.1).
 *
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase1 extends Controller {

    /**
     * produces two streams delayed hello and world respectively, composes those streams together and flushes to the
     * browser one after another, i.e. first hello stream and then world stream.
     *
     * @return
     */
    public static Result index() {
        final HtmlStream helloStream = HtmlStream.apply(delayed("hello", 1));
        final HtmlStream worldStream = HtmlStream.apply(delayed("world", 2));

        final HtmlStream composedStream = helloStream.andThen(HtmlStream.apply(" ")).andThen(worldStream);

        return ok(composedStream.toChunks());
    }

    //if first response is fast we flush this immediately
    public static Result index_a() {
        final HtmlStream stream1 = HtmlStream.apply(delayed("hello", 0));
        final HtmlStream stream2 = HtmlStream.apply(delayed("world", 2));

        final HtmlStream composedStream = stream1.andThen(HtmlStream.apply(" ")).andThen(stream2);

        return ok(composedStream.toChunks());
    }

    //if second response is fast we cannot flush this and we have to wait until first response completes
    public static Result index_b() {
        final HtmlStream stream1 = HtmlStream.apply(delayed("hello", 2));
        final HtmlStream stream2 = HtmlStream.apply(delayed("world", 0));

        final HtmlStream composedStream = stream1.andThen(HtmlStream.apply(" ")).andThen(stream2);

        return ok(composedStream.toChunks());
    }

    private static F.Promise<Html> delayed(final String txt, final int delayInSecs) {
        return F.Promise.timeout(txt, delayInSecs, TimeUnit.SECONDS).map(str -> Html.apply(str));
    }

}
