package me.hajoo.async.v2;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class Agency {

    private final Map<String, Snack> snacks = new HashMap<>() {{
        put("김밥", Snack.makeMenu("김밥", 1000));
        put("우동", Snack.makeMenu("우동", 2000));
        put("순대", Snack.makeMenu("순대", 3000));
        put("떡볶이", Snack.makeMenu("떡볶이", 4000));
        put("라볶이", Snack.makeMenu("라볶이", 5000));
    }};

    @Async("threadPoolTaskExecutor2")
    public CompletableFuture<Snack> cooking(String name) {
        Snack snack = snacks.get(name);
        try {
            Thread.sleep(snack.getProductionWaitTime());
            System.out.println(Thread.currentThread() + " " + snack.getName() + " 음식을 만드는 데 "+ snack.getProductionWaitTime() + "ms 가 걸렸습니다.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(snack);
    }
}
