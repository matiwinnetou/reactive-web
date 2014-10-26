/*
 * Copyright (c) 2014 Mateusz Szczap
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package controllers;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
import ui.HtmlStream;

import java.util.concurrent.TimeUnit;

/**
 * This example demonstrates stream composition and most basic streaming (http 1.1) of strings.
 *
 * Produces two time delayed streams - hello and world respectively, composes those streams together and flushes to the
 * browser one after another, i.e. first hello stream and then world stream
 *
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase1 extends Controller {

    /**
     * Best case scenario - hello stream is fast
     */
    public static Result index_a() {
        final HtmlStream helloStream = HtmlStream.apply(delayed("hello", 1));
        final HtmlStream worldStream = HtmlStream.apply(delayed("world", 3));

        final HtmlStream composedStream = helloStream.andThen(HtmlStream.apply(" ")).andThen(worldStream);

        return ok(composedStream.toChunks());
    }

    /**
     * Worst case scenario - hello is slow, world is fast.
     * @return
     */
    public static Result index_b() {
        final HtmlStream helloStream = HtmlStream.apply(delayed("hello", 3));
        final HtmlStream worldStream = HtmlStream.apply(delayed("world", 1));

        final HtmlStream composedStream = helloStream.andThen(HtmlStream.apply(" ")).andThen(worldStream);

        return ok(composedStream.toChunks());
    }

    private static F.Promise<Html> delayed(final String txt, final int delayInSecs) {
        return F.Promise.timeout(txt, delayInSecs, TimeUnit.SECONDS).map(str -> Html.apply(str));
    }

}

//curl -v http://localhost:9000/usecase1a
//        * Hostname was NOT found in DNS cache
//        *   Trying ::1...
//        * Connected to localhost (::1) port 9000 (#0)
//        > GET /usecase1a HTTP/1.1
//        > User-Agent: curl/7.37.1
//        > Host: localhost:9000
//        > Accept: */*
//>
//< HTTP/1.1 200 OK
//< Content-Type: text/html; charset=utf-8
//< Transfer-Encoding: chunked
//<
//hello world
//* Connection #0 to host localhost left intact