/**
 * 
 */
package com.hehua.item.model;

import com.google.common.base.Function;

/**
 * @author zhihua
 *
 */
public class ItemLite {

    public static Function<ItemLite, Integer> idExtractor = new Function<ItemLite, Integer>() {

        @Override
        public Integer apply(ItemLite input) {
            return (int) input.getId();
        }
    };

    public static Function<ItemLite, Integer> flashIdExtractor = new Function<ItemLite, Integer>() {

        @Override
        public Integer apply(ItemLite input) {
            return input.getFlashid();
        }
    };

    protected int id;

    protected String name;

    protected double originprice;

    protected double realprice;

    protected String image;

    private int sales;

    private int flashid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOriginprice() {
        return originprice;
    }

    public void setOriginprice(double originprice) {
        this.originprice = originprice;
    }

    public double getRealprice() {
        return realprice;
    }

    public void setRealprice(double realprice) {
        this.realprice = realprice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getFlashid() {
        return flashid;
    }

    public void setFlashid(int flashid) {
        this.flashid = flashid;
    }

}
