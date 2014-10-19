package controllers;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
import ui.HtmlStream;
import utils.PageRenderingMode;
import utils.RequestParams;

import java.util.concurrent.TimeUnit;

/**
 * This example demonstrates stream composition and streaming (http 1.1).
 *
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase1 extends Controller {

    public static F.Promise <Result> index() {
        final HtmlStream stream1 = HtmlStream.apply(delayed("hello", 1));
        final HtmlStream stream2 = HtmlStream.apply(delayed("world", 2));

        final HtmlStream composedStream = stream1.andThen(HtmlStream.apply(" ")).andThen(stream2);

        return render(composedStream);
    }

    //if first response is fast we flush this immediately
    public static F.Promise <Result> index_a() {
        final HtmlStream stream1 = HtmlStream.apply(delayed("hello", 0));
        final HtmlStream stream2 = HtmlStream.apply(delayed("world", 2));

        final HtmlStream composedStream = stream1.andThen(HtmlStream.apply(" ")).andThen(stream2);

        return render(composedStream);
    }

    //if second response is fast we cannot flush this and we have to wait until first response completes
    public static F.Promise <Result> index_b() {
        final HtmlStream stream1 = HtmlStream.apply(delayed("hello", 2));
        final HtmlStream stream2 = HtmlStream.apply(delayed("world", 0));

        final HtmlStream composedStream = stream1.andThen(HtmlStream.apply(" ")).andThen(stream2);

        return render(composedStream);
    }

    private static F.Promise<Html> delayed(final String txt, final int delayInSecs) {
        return F.Promise.timeout(txt, delayInSecs, TimeUnit.SECONDS).map(str -> Html.apply(str));
    }

    private static F.Promise<Result> render(final HtmlStream stream) {
        if (RequestParams.getPageRenderingMode(request()) == PageRenderingMode.CLASSIC) {
            return stream.foldJ().map(html -> ok(html));
        }

        return F.Promise.pure(ok(stream.toChunks()));
    }


}
