/**
 * Apache 2
 * Copyright 2014 The Apache Software Foundation
 *
 * This product includes software developed at
 * The Apache Software Foundation (http://www.apache.org/).
 */
package utils;

import play.mvc.Http;

import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Created by Mateusz Szczap on 19/10/14.
 */
public class RequestParams {

    public static final String BIG_PIPE_QUERY_PARAMETER = "bigPipe";
    public static final String SERIAL_QUERY_PARAMETER = "serial";

    /**
     * A helper function that will check http params and headers to see if there is bigPipe and serial parameter present
     *
     * @param request
     *
     * @return PageRenderingMode
     */
    public static PageRenderingMode getPageRenderingMode(final Http.Request request) {
        final Optional<String> bigPipeQueryParamOpt = ofNullable(request.getQueryString(BIG_PIPE_QUERY_PARAMETER));
        final Optional<String> bigPipeHeaderOpt = ofNullable(request.getHeader(BIG_PIPE_QUERY_PARAMETER));

        if (bigPipeQueryParamOpt.isPresent() || bigPipeHeaderOpt.isPresent()) {
            return PageRenderingMode.BIGPIPE;
        }

        final Optional<String> serialParamOpt = ofNullable(request.getQueryString(SERIAL_QUERY_PARAMETER));
        final Optional<String> serialHeaderOpt = ofNullable(request.getHeader(SERIAL_QUERY_PARAMETER));

        if (serialParamOpt.isPresent() || serialHeaderOpt.isPresent()) {
            return PageRenderingMode.CLASSIC;
        }

        return PageRenderingMode.CHUNKED;
    }

}
