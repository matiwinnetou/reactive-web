package pagelets;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by mati on 19/10/14.
 */
public class MiniSrpPageletModel {

    public static MiniSrpPageletModel EMPTY = new MiniSrpPageletModel("", 0, Lists.newArrayList());

    private String title = "";
    private int count = 0;
    private List<Item> items = Lists.newArrayList();

    public MiniSrpPageletModel(String title, int count, final List<Item> items) {
        this.title = title;
        this.count = count;
        this.items = items;
    }

    public boolean isEnabled() {
        return !title.equalsIgnoreCase("");
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return count;
    }

    public List<MiniSrpPageletModel.Item> getItems() {
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
        private int price = 0;
        private String imageUrl = "";
        private String line1 = "";
        private String line2 = "";
        private String line3 = "";
        private String line4 = "";

        public Item(String title, int price, String imageUrl, String line1, String line2, String line3, String line4) {
            this.title = title;
            this.price = price;
            this.imageUrl = imageUrl;
            this.line1 = line1;
            this.line2 = line2;
            this.line3 = line3;
            this.line4 = line4;
        }

        public String getTitle() {
            return title;
        }

        public int getPrice() {
            return price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getLine1() {
            return line1;
        }

        public String getLine2() {
            return line2;
        }

        public String getLine3() {
            return line3;
        }

        public String getLine4() {
            return line4;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "title='" + title + '\'' +
                    ", price=" + price +
                    ", imageUrl=" + imageUrl +
                    ", line1='" + line1 + '\'' +
                    ", line2='" + line2 + '\'' +
                    ", line3='" + line3 + '\'' +
                    ", line4='" + line4 + '\'' +
                    '}';
        }

    }

}

