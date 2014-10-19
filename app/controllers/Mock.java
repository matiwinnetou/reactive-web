package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import domain.SearchResults;
import domain.VehicleData;
import domain.VehicleImage;
import play.libs.F;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.time.YearMonth;
import java.util.List;

/**
 */
public class Mock extends Controller {

    public static F.Promise<Result> mock(final String serviceName, int vehicleId) {
        switch(serviceName) {
            case "vehicleData": return respond(Json.toJson(fakeVehicleData()), 1);
            case "vehicleImage": return respond(Json.toJson(fakeVehicleImage()), 5);
            case "searchResults": return respond(Json.toJson(fakeSearchResults()), 2);
            default: return F.Promise.pure(badRequest(String.format("serviceName %s not supported!", serviceName)));
        }
    }

    private static F.Promise<Result> respond(final JsonNode json, final int delayInSeconds) {
        return F.Promise.timeout(ok(json), delayInSeconds);
    }

    private static VehicleData fakeVehicleData() {
        return new VehicleData(1,
                "Ford",
                "Mondeo",
                "TDCi",
                "2.2",
                "Der Ford Mondeo 2.2 TDCI ist das Topmodell unter den" +
                        " Selbstzündern der erfolgreichen Baureihe. Seit dem Jahr 2012 wirkt der neue oder gebrauchte Ford Mondeo 2.2 TDCI " +
                        "auf seine direkten Wettbewerber wie den Opel Insignia 2.0 CDTI oder Peugeot 508 HDI FAP 200 ein. Der Ford Mondeo 2.2 TDCI " +
                        "Neuwagen will neben dem günstigeren Preis mit vielen weiteren Attributen punkten. So ist die Serienausstattung im Ford Mondeo 2.2 TDCI " +
                        "recht komplett und lässt auch als Gebrauchtwagen kaum Wünsche offen. Das Highlight des als Limousine und Turnier (Kombi) erhältlichen Ford Mondeo 2.2 TDCI " +
                        "ist ohne Zweifel das drehmomentstarke Triebwerk: Der Vierzylinder-Turbodiesel mit knapp 2,2 Liter Hubraum entwickelt 200 PS und verhilft dem Kölner Bestseller zu" +
                        " sportlichen Fahrleistungen: Über das manuelle Sechsganggetriebe spurtet der Ford Mondeo 2.2 TDCI aus dem Stand in 8,3 Sekunden auf Landstraßentempo." +
                        " Mit seiner Spitzengeschwindigkeit von 230 km/h liegen Limousine wie auch der Turnier im Bereich der Konkurrenten. Trotz seiner sportlichen Leistungswerte lässt sich ein" +
                        " Ford Mondeo 2.2 TDCI Gebrauchtwagen sehr moderat bewegen: Im Durchschnitt konsumiert der Selbstzünder lediglich 6,0 Liter Diesel auf 100 Kilometer." +
                        " Falls Sie auf der Suche nach einem kräftig motorisierten Selbstzünder sind, finden Sie den Ford Mondeo 2.2 TDCI Gebrauchtwagen in den Inseraten auf mobile.de!"
        );
    }

    private static VehicleImage fakeVehicleImage() {
        return new VehicleImage(1, "http://static.classistatic.de/imagegallery/ford/mondeo/ford-mondeo-for_mon_10_kb_5.jpg");
    }

    private static SearchResults fakeSearchResults() {
        final List<SearchResults.Item> items = Lists.newArrayList(car1(), car2(), car3());

        return new SearchResults("FORD MONDEO 2.2 TDCI", 3680, items);
    }

    private static SearchResults.Item car1() {
        return new SearchResults.Item(
                "Ford Mondeo Turnier 2.0 TDCi AUTOMATIK NAVI AHK PDCFord Mondeo Turnier 2.0 TDCi AUTOMATIK NAVI AHK PDC",
                "DE",
                "12167",
                "Berlin",
                YearMonth.of(2005, 05),
                120990,
                80,
                8.7F,
                149.0F);
    }

    private static SearchResults.Item car2() {
        return new SearchResults.Item(
                "Ford Mondeo Turnier 2.0 TDCi Aut.Ford Mondeo Turnier 2.0 TDCi Aut.",
                "DE",
                "72072",
                "Landau",
                YearMonth.of(2001, 05),
                80100,
                60,
                7.7F,
                119.0F);
    }

    private static SearchResults.Item car3() {
        return new SearchResults.Item(
                "Ford Mondeo 2.0 Turnier TDCi Ghia VollausstattungFord Mondeo 2.0 Turnier TDCi Ghia Vollausstattung",
                "FR",
                "129011",
                "Paris",
                YearMonth.of(1990, 05),
                200000,
                120,
                10.7F,
                150.5F);
    }

}
