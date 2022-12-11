package me.hajoo.async.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SnackShop {

    private final Agency agency;

    public CompletableFuture<Snack> makeSnack(String name) {
        // 다른 업체에 요리를 맡김
        try {
            return agency.cooking(name);
        } catch (Exception e) {}
        return null;
    }
}
