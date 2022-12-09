package me.hajoo.async;

import java.util.*;

public class SnackShop {

    private static SnackShop snackShop;

    private SnackShop() {}

    public static synchronized SnackShop open() {
        if (snackShop == null) {
            synchronized (SnackShop.class) {
                if (snackShop == null) {
                    snackShop = new SnackShop();
                }
            }
        }
        return snackShop;
    }

    private final Map<String, Snack> snacks = new HashMap<>() {{
        put("김밥", Snack.makeMenu("김밥", 1000));
        put("우동", Snack.makeMenu("우동", 2000));
        put("순대", Snack.makeMenu("순대", 3000));
        put("떡볶이", Snack.makeMenu("떡볶이", 4000));
        put("라볶이", Snack.makeMenu("라볶이", 5000));
    }};

    public Snack makeSnack(String name) {
        // 메뉴 선택
        Snack snack = snacks.get(name);
        // 조리중
        cooking(snack.getProductionWaitTime());
        // 완료
        System.out.println(snack.getName() + " 음식을 만드는 데 "+ snack.getProductionWaitTime() + "ms 가 걸렸습니다.");
        return snack;
    }

    private void cooking(long productionWaitTime) {
        try {
            Thread.sleep(productionWaitTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
