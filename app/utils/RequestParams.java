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
