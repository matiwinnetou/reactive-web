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
    public static F.Promise<Result> index_a(final int vehicleId) {
        final HtmlStream modelDescStream = ModelDescPagelet.stream(vehicleId, 1, 0, false);
        final HtmlStream miniSrpStream = MiniSrpPagelet.stream(vehicleId, 15, false);

        final HtmlStream stream = (HtmlStream) views.stream.usecase5.render(modelDescStream, miniSrpStream);

        return F.Promise.pure(ok(stream.toChunks()));
    }

    /**
     * Reactive fallback - exception case
     */
    public static F.Promise<Result> index_b(final int vehicleId) {
        final HtmlStream modelDescStream = ModelDescPagelet.stream(vehicleId, 1, 0, false);
        final HtmlStream miniSrpStream = MiniSrpPagelet.stream(vehicleId, 1, true);

        final HtmlStream stream = (HtmlStream) views.stream.usecase5.render(modelDescStream, miniSrpStream);

        return F.Promise.pure(ok(stream.toChunks()));
    }

}
