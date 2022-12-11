package me.hajoo.async.v2;

public class Snack {

    private final String name;
    private final long productionWaitTime;

    private Snack(String name, int productionWaitTime) {
        this.name = name;
        this.productionWaitTime = productionWaitTime;
    }

    public static Snack makeMenu(String name, int productionWaitTime) {
        return new Snack(name, productionWaitTime);
    }

    public String getName() {
        return name;
    }

    public long getProductionWaitTime() {
        return productionWaitTime;
    }
}
