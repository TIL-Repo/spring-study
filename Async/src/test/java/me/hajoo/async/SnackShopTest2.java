package me.hajoo.async;

import me.hajoo.async.v2.Snack;
import me.hajoo.async.v2.SnackShop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@SpringBootTest
class SnackShopTest2 {

    @Autowired
    private SnackShop snackShop;

    static final List<String> orders = Arrays.asList(
            "김밥",
            "우동",
            "순대",
            "라볶이",
            "떡볶이"
    );

    @Test
    void async() throws Exception {
        // when
        long startTime = System.currentTimeMillis();
        List<CompletableFuture<Snack>> snacks = orders.stream().map(order -> snackShop.makeSnack(order)).toList();

        for (CompletableFuture<Snack> snack : snacks) {
            snack.get();
        }

        // then
        System.out.println("걸린 시간 : " + (System.currentTimeMillis() - startTime));
    }
}