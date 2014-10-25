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
import utils.Promises;

import java.util.concurrent.TimeUnit;

/**
 * First most basic example with Play framework (Java)
 *
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase0 extends Controller {

    /**
     * In this example we are invoking two fake services and we are 'blocking' web page load until a response comes from both.
     * We will return a response as fast as the slowest service invocation. This method uses pure http 1.0
     * and content-length is known at the beginning of the request from client's point of view.
     *
     * Return a promise of a result based on invocation of two 'fake time delayed services', each returning just a string
     */
    public static F.Promise<Result> index() {
        final F.Promise<String> helloP = delayedInSecs("hello", 1);
        final F.Promise<String> worldP = delayedInSecs("world", 2);

        return Promises.zip(helloP, worldP, (hello, world) -> String.format("%s %s", hello, world))
                .map(str -> ok(str));
    }

    /**
     * returns delayed in seconds invocation based on type T
     *
     * @param t
     * @param delayInSecs
     * @return
     */
    private static <T> F.Promise<T> delayedInSecs(final T t, final int delayInSecs) {
        return F.Promise.timeout(t, delayInSecs, TimeUnit.SECONDS);
    }

}

//time curl -v http://localhost:9000/usecase0
//        * Hostname was NOT found in DNS cache
//        *   Trying ::1...
//        * Connected to localhost (::1) port 9000 (#0)
//        > GET /usecase0 HTTP/1.1
//        > User-Agent: curl/7.37.1
//        > Host: localhost:9000
//        > Accept: */*
//>
//< HTTP/1.1 200 OK
//< Content-Type: text/plain; charset=utf-8
//< Content-Length: 11
//<
//* Connection #0 to host localhost left intact
//hello world
//real	0m2.016s
//user	0m0.003s
//sys	0m0.003s