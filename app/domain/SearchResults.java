package domain;

import com.google.common.collect.Lists;

import java.time.YearMonth;
import java.util.List;

/**
 * Created by mati on 19/10/14.
 */
public class SearchResults {

    private String title = "";
    private int count = 0;
    private List<Item> items = Lists.newArrayList();

    public SearchResults(String title, int count, final List<Item> items) {
        this.title = title;
        this.count = count;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return count;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "SearchModel{" +
                "title='" + title + '\'' +
                ", count=" + count +
                ", items=" + items +
                '}';
    }

    public static class Item {

        private String title = "";
        private String countryCode = "";
        private String postCode = "";
        private String city = "";
        private YearMonth firstReg = YearMonth.of(1900, 1);
        private int mileage  = 0;
        private int powerKw = 0;
        private float fuelConsumptionCombined = 0.0F;
        private float co2Combined = 0.0F;

        public Item(String title,
                    String countryCode,
                    String postCode,
                    String city,
                    YearMonth firstReg,
                    int mileage,
                    int powerKw,
                    float fuelConsumptionCombined,
                    float co2Combined) {
            this.title = title;
            this.countryCode = countryCode;
            this.postCode = postCode;
            this.city = city;
            this.firstReg = firstReg;
            this.mileage = mileage;
            this.powerKw = powerKw;
            this.fuelConsumptionCombined = fuelConsumptionCombined;
            this.co2Combined = co2Combined;
        }

        public String getTitle() {
            return title;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public String getPostCode() {
            return postCode;
        }

        public String getCity() {
            return city;
        }

        public YearMonth getFirstReg() {
            return firstReg;
        }

        public int getMileage() {
            return mileage;
        }

        public int getPowerKw() {
            return powerKw;
        }

        public float getFuelConsumptionCombined() {
            return fuelConsumptionCombined;
        }

        public float getCo2Combined() {
            return co2Combined;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "title='" + title + '\'' +
                    ", countryCode='" + countryCode + '\'' +
                    ", postCode='" + postCode + '\'' +
                    ", city='" + city + '\'' +
                    ", firstReg=" + firstReg +
                    ", mileage=" + mileage +
                    ", powerKw=" + powerKw +
                    ", fuelConsumptionCombined=" + fuelConsumptionCombined +
                    ", co2Combined=" + co2Combined +
                    '}';
        }

    }

}
