package me.hajoo.async;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
class SnackShopTest {

    @Autowired
    private Executor executor;

    static final List<String> orders = Arrays.asList(
            "김밥",
            "우동",
            "순대",
            "라볶이",
            "떡볶이"
    );

    @Test
    void sync() throws Exception {
        // given
        SnackShop snackShop = SnackShop.open();

        // when
        long startTime = System.currentTimeMillis();
        orders.forEach(snackShop::makeSnack);

        // then
        System.out.println("걸린 시간 : " + (System.currentTimeMillis() - startTime));
    }

    @Test
    void asyncByFuture() throws Exception {
        // given
        SnackShop snackShop = SnackShop.open();

        // when
        long startTime = System.currentTimeMillis();
        List<Future<Snack>> snacks = orders.stream()
                .map(order -> {
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    return executor.submit(() -> snackShop.makeSnack(order));
                })
                .toList();

        for (Future<Snack> snack : snacks) {
            snack.get();
        }

        // then
        System.out.println("걸린 시간 : " + (System.currentTimeMillis() - startTime));
    }
    
    @Test
    void asyncByCompletableFuture() throws Exception {
        // given
        SnackShop snackShop = SnackShop.open();
        
        // when
        long startTime = System.currentTimeMillis();
        List<CompletableFuture<Snack>> snacks = orders.stream()
                .map(order -> CompletableFuture.supplyAsync(() -> snackShop.makeSnack(order)))
                .toList();

        for (CompletableFuture<Snack> snack : snacks) {
            snack.get();
        }

        // then
        System.out.println("걸린 시간 : " + (System.currentTimeMillis() - startTime));
    }

    @Test
    void asyncByCompletableFutureFromCustomThreadPool() throws Exception {
        // given
        SnackShop snackShop = SnackShop.open();

        // when
        long startTime = System.currentTimeMillis();
        List<CompletableFuture<Snack>> snacks = orders.stream()
                .map(order -> CompletableFuture.supplyAsync(() -> snackShop.makeSnack(order), executor))
                .toList();

        for (CompletableFuture<Snack> snack : snacks) {
            snack.get();
        }

        // then
        System.out.println("걸린 시간 : " + (System.currentTimeMillis() - startTime));
    }
}