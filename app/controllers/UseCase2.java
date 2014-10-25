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
import play.mvc.Controller;
import play.mvc.Result;
import ui.HtmlStream;

/**
 * Demonstrates example of using http 1.1 streaming with views and pagelets
 *
 * Created by Mateusz Szczap on 19/10/14.
 */
public class UseCase2 extends Controller {

    /**
     * Streaming best case scenario, model description pagelet responds and is flushed, then MiniSrp is flushed
     */
    public static Result index_a() {
        final HtmlStream modelDesc = ModelDescPagelet.stream(1, 1, false);
        final HtmlStream miniSrp = MiniSrpPagelet.stream(3, false);

        final HtmlStream stream = (HtmlStream) views.stream.usecase2.render(modelDesc, miniSrp);

        return ok(stream.toChunks());
    }

    /**
     * Streaming worst case scenario, ModelDescription pagelet responds late and CANNOT be flushed, MiniSrp is flushed late
     * even though it could be flushed earlier since web service responded very fast
     */
    public static Result index_b() {
        final HtmlStream modelDesc = ModelDescPagelet.stream(1, 1, false);
        final HtmlStream miniSrp = MiniSrpPagelet.stream(0, false);

        final HtmlStream stream = (HtmlStream) views.stream.usecase2.render(modelDesc, miniSrp);

        return ok(stream.toChunks());
    }

}

//curl -v http://localhost:9000/usecase2a
//        * Hostname was NOT found in DNS cache
//        *   Trying ::1...
//        * Connected to localhost (::1) port 9000 (#0)
//        > GET /usecase2a HTTP/1.1
//        > User-Agent: curl/7.37.1
//        > Host: localhost:9000
//        > Accept: */*
//>
//< HTTP/1.1 200 OK
//< Content-Type: text/html; charset=utf-8
//< Transfer-Encoding: chunked
//<
//
//<html>
//    <head></head>
//    <body>
//
//    <h2>ModelDescription Pagelet</h2>
//    <br>
//    Ford Mondeo TDCi<br>
//    <img src="http://static.classistatic.de/imagegallery/ford/mondeo/ford-mondeo-for_mon_10_kb_5.jpg"/><br>
//    <br>
//    Der Ford Mondeo 2.2 TDCI ist das Topmodell unter den Selbstzündern der erfolgreichen Baureihe. Seit dem Jahr 2012 wirkt der neue oder gebrauchte Ford Mondeo 2.2 TDCI auf seine direkten Wettbewerber wie den Opel Insignia 2.0 CDTI oder Peugeot 508 HDI FAP 200 ein. Der Ford Mondeo 2.2 TDCI Neuwagen will neben dem günstigeren Preis mit vielen weiteren Attributen punkten. So ist die Serienausstattung im Ford Mondeo 2.2 TDCI recht komplett und lässt auch als Gebrauchtwagen kaum Wünsche offen. Das Highlight des als Limousine und Turnier (Kombi) erhältlichen Ford Mondeo 2.2 TDCI ist ohne Zweifel das drehmomentstarke Triebwerk: Der Vierzylinder-Turbodiesel mit knapp 2,2 Liter Hubraum entwickelt 200 PS und verhilft dem Kölner Bestseller zu sportlichen Fahrleistungen: Über das manuelle Sechsganggetriebe spurtet der Ford Mondeo 2.2 TDCI aus dem Stand in 8,3 Sekunden auf Landstraßentempo. Mit seiner Spitzengeschwindigkeit von 230 km/h liegen Limousine wie auch der Turnier im Bereich der Konkurrenten. Trotz seiner sportlichen Leistungswerte lässt sich ein Ford Mondeo 2.2 TDCI Gebrauchtwagen sehr moderat bewegen: Im Durchschnitt konsumiert der Selbstzünder lediglich 6,0 Liter Diesel auf 100 Kilometer. Falls Sie auf der Suche nach einem kräftig motorisierten Selbstzünder sind, finden Sie den Ford Mondeo 2.2 TDCI Gebrauchtwagen in den Inseraten auf mobile.de!<br>
//
//    <hr>
//
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
//            <img src="http://i.ebayimg.com/00/s/NTc2WDEwMjQ=/z/GM8AAOSwU9xURsLl/$_18.JPG" />
//    </ul>
//
//    </body>
//</html>
//* Connection #0 to host localhost left intact