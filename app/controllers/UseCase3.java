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
import pagelets.MiniSrpPagelet;
import pagelets.ModelDescPagelet;
import play.mvc.Controller;
import play.mvc.Result;
import ui.HtmlStream;
import ui.Pagelet;

/**
 * Demonstrates BigPipe mode only
 *
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase3 extends Controller {

    /**
     * In this example even though the ModelDescription has two slow service call invocations we can still
     * flush the response of MiniSrp pagelet stream via injecting div content into DOM.
     */
    public static Result index(final int vehicleId) {
        final HtmlStream modelDesc = ModelDescPagelet.stream(vehicleId, 4, 2, false);
        final HtmlStream miniSrp = MiniSrpPagelet.stream(vehicleId, 0, false);

        final HtmlStream modelDescPagelet = Pagelet.renderStream(modelDesc, "modelDescP");
        final HtmlStream miniSrpPagelet = Pagelet.renderStream(miniSrp, "miniSrpP");

        final HtmlStream bigPipe = HtmlStream.interleave(ImmutableList.of(modelDescPagelet, miniSrpPagelet));

        final HtmlStream out = (HtmlStream) views.stream.usecase3.render(HtmlStream.empty(), HtmlStream.empty(), bigPipe);

        return ok(out.toChunks());
    }

}

//curl -v http://localhost:9000/usecase3
//        * Hostname was NOT found in DNS cache
//        *   Trying ::1...
//        * Connected to localhost (::1) port 9000 (#0)
//        > GET /usecase3 HTTP/1.1
//        > User-Agent: curl/7.37.1
//        > Host: localhost:9000
//        > Accept: */*
//>
//< HTTP/1.1 200 OK
//< Content-Type: text/html; charset=utf-8
//< Transfer-Encoding: chunked
//<
//
//
//<html>
//    <head></head>
//    <body>
//        <div id="modelDescP"></div>
//        <hr>
//        <div id="miniSrpP"></div>
//    </body>
//</html>
//
//<script type="text/html-stream" id="miniSrpP-contents">
//    <h2>MiniSRP</h2>
//    <br>
//    FORD MONDEO 2.2 TDCI<br>
//    3680<br>
//    <ul>
//            <li>Ford Mondeo Turnier 2.0 TDCi AUTOMATIK NAVI AHK PDC : 1220 EUR: 12167 Berlin : 2005/05, 120990: 80 kW (109 PS)</li>
//            <img src="http://i.ebayimg.com/00/s/NzY4WDEwMjQ=/z/~OQAAOSwY45URsn~/$_18.JPG" />
//
//            <li>Ford Mondeo Turnier 2.0 TDCi Aut. : 9022 EUR: 72072 Landau : 2001/02, 80100: 60 kW (82 PS)</li>
//            <img src="http://i.ebayimg.com/00/s/NzY4WDEwMjQ=/z/9jkAAOSw6EhURsHB/$_18.JPG" />
//
//            <li>Ford Mondeo 2.0 Turnier TDCi Ghia Vollausstattung : 7654 EUR: 129011 Paris : 1990/09, 200000: 120 kW (163 PS)</li>
//    </ul>
//</script>
//
//<script type="text/javascript">
//  document.getElementById("miniSrpP").innerHTML = document.getElementById("miniSrpP-contents").innerHTML;
//</script>
//
//<script type="text/html-stream" id="modelDescP-contents">
//    <h2>ModelDescription Pagelet</h2>
//    <br>
//    Ford Mondeo TDCi<br>
//    <img src="http://static.classistatic.de/imagegallery/ford/mondeo/ford-mondeo-for_mon_10_kb_5.jpg"/><br>
//    <br>
//    Der Ford Mondeo 2.2 TDCI ist das Topmodell unter den Selbstzündern der erfolgreichen Baureihe. Seit dem Jahr 2012 wirkt der neue oder gebrauchte Ford Mondeo 2.2 TDCI auf seine direkten Wettbewerber wie den Opel Insignia 2.0 CDTI oder Peugeot 508 HDI FAP 200 ein. Der Ford Mondeo 2.2 TDCI Neuwagen will neben dem günstigeren Preis mit vielen weiteren Attributen punkten. So ist die Serienausstattung im Ford Mondeo 2.2 TDCI recht komplett und lässt auch als Gebrauchtwagen kaum Wünsche offen. Das Highlight des als Limousine und Turnier (Kombi) erhältlichen Ford Mondeo 2.2 TDCI ist ohne Zweifel das drehmomentstarke Triebwerk: Der Vierzylinder-Turbodiesel mit knapp 2,2 Liter Hubraum entwickelt 200 PS und verhilft dem Kölner Bestseller zu sportlichen Fahrleistungen: Über das manuelle Sechsganggetriebe spurtet der Ford Mondeo 2.2 TDCI aus dem Stand in 8,3 Sekunden auf Landstraßentempo. Mit seiner Spitzengeschwindigkeit von 230 km/h liegen Limousine wie auch der Turnier im Bereich der Konkurrenten. Trotz seiner sportlichen Leistungswerte lässt sich ein Ford Mondeo 2.2 TDCI Gebrauchtwagen sehr moderat bewegen: Im Durchschnitt konsumiert der Selbstzünder lediglich 6,0 Liter Diesel auf 100 Kilometer. Falls Sie auf der Suche nach einem kräftig motorisierten Selbstzünder sind, finden Sie den Ford Mondeo 2.2 TDCI Gebrauchtwagen in den Inseraten auf mobile.de!<br>
//</script>
//
//<script type="text/javascript">
//  document.getElementById("modelDescP").innerHTML = document.getElementById("modelDescP-contents").innerHTML;
//</script>
//* Connection #0 to host localhost left intact