package jx.com.day1.bean;

public class GoodsBean {
    public String code;
    public Data data;

    public String getCode() {
        return code;
    }
    public boolean hasSuccess(){
        return code.equals("0");
    }

    public Data getData() {
        return data;
    }

    public static class Data{
        public double bargainPrice;
        public double price;
        public String title;
        public String images;

        public double getBargainPrice() {
            return bargainPrice;
        }

        public double getPrice() {
            return price;
        }

        public String getTitle() {
            return title;
        }

        public String getImages() {
            return images;
        }
    }
}
