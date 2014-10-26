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

import com.google.common.collect.ImmutableList;
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
 * Demonstrates all rendering modes working together, change rendering mode based on request or http header
 * 0. default - http 1.1 chunked transfer (http://localhost:9000/usecase4)
 * 1. serial=true - http 1.0 request and response (http://localhost:9000/usecase4?serial=true)
 * 2. bigPipe=true - http 1.1 chunked transfer with injecting DIV content via JavaScript (http://localhost:9000/usecase4?bigPipe=true)
 *
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase4 extends Controller {

    public static F.Promise<Result> index() {
        final HtmlStream modelDescStream = ModelDescPagelet.stream(1, 2, false);
        final HtmlStream miniSrpStream = MiniSrpPagelet.stream(0, false);

        final PageRenderingMode pageRenderingMode = RequestParams.getPageRenderingMode(request());

        if (pageRenderingMode == PageRenderingMode.BIGPIPE) {
            final HtmlStream modelDescPagelet = Pagelet.renderStream(modelDescStream, "modelDescP");
            final HtmlStream miniSrpPagelet = Pagelet.renderStream(miniSrpStream, "miniSrpP");

            final HtmlStream bigPipe = HtmlStream.interleave(ImmutableList.of(modelDescPagelet, miniSrpPagelet));

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
