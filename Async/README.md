# 비동기 처리 여러가지 방법 - ExecutorService, CompletableFuture, @Async

## 준비 작업

### 분식

```java
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
```

### 분식점

```java
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
        System.out.println(Thread.currentThread() + " " + snack.getName() + " 음식을 만드는 데 "+ snack.getProductionWaitTime() + "ms 가 걸렸습니다.");
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
```

### 주방 요리사 설정

```java
@Configuration
public class ThreadPoolConfig {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 25;
    private static final int QUEUE_CAPACITY = 10;
    private static final String THREAD_NAME_PREFIX = "Custom";

    @Bean("threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        taskExecutor.initialize();
        return taskExecutor;
    }
}
```

## 동기(Sync)

```java
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
}
```

```shell
Thread[Test worker,5,main] 김밥 음식을 만드는 데 1000ms 가 걸렸습니다.
Thread[Test worker,5,main] 우동 음식을 만드는 데 2000ms 가 걸렸습니다.
Thread[Test worker,5,main] 순대 음식을 만드는 데 3000ms 가 걸렸습니다.
Thread[Test worker,5,main] 라볶이 음식을 만드는 데 5000ms 가 걸렸습니다.
Thread[Test worker,5,main] 떡볶이 음식을 만드는 데 4000ms 가 걸렸습니다.
걸린 시간 : 15024
```

## 비동기(Future)

```java
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
```

```shell
Thread[pool-1-thread-1,5,main] 김밥 음식을 만드는 데 1000ms 가 걸렸습니다.
Thread[pool-2-thread-1,5,main] 우동 음식을 만드는 데 2000ms 가 걸렸습니다.
Thread[pool-3-thread-1,5,main] 순대 음식을 만드는 데 3000ms 가 걸렸습니다.
Thread[pool-5-thread-1,5,main] 떡볶이 음식을 만드는 데 4000ms 가 걸렸습니다.
Thread[pool-4-thread-1,5,main] 라볶이 음식을 만드는 데 5000ms 가 걸렸습니다.
걸린 시간 : 5007
```

## 비동기(CompletableFuture)

```java
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
```

```shell
Thread[ForkJoinPool.commonPool-worker-1,5,main] 김밥 음식을 만드는 데 1000ms 가 걸렸습니다.
Thread[ForkJoinPool.commonPool-worker-2,5,main] 우동 음식을 만드는 데 2000ms 가 걸렸습니다.
Thread[ForkJoinPool.commonPool-worker-3,5,main] 순대 음식을 만드는 데 3000ms 가 걸렸습니다.
Thread[ForkJoinPool.commonPool-worker-5,5,main] 떡볶이 음식을 만드는 데 4000ms 가 걸렸습니다.
Thread[ForkJoinPool.commonPool-worker-4,5,main] 라볶이 음식을 만드는 데 5000ms 가 걸렸습니다.
```

## 비동기(CompletableFuture), Custom ThreadPool 이용

```java
@Autowired
private Executor executor;

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
```

```shell
Thread[Custom1,5,main] 김밥 음식을 만드는 데 1000ms 가 걸렸습니다.
Thread[Custom2,5,main] 우동 음식을 만드는 데 2000ms 가 걸렸습니다.
Thread[Custom3,5,main] 순대 음식을 만드는 데 3000ms 가 걸렸습니다.
Thread[Custom5,5,main] 떡볶이 음식을 만드는 데 4000ms 가 걸렸습니다.
Thread[Custom4,5,main] 라볶이 음식을 만드는 데 5000ms 가 걸렸습니다.
```
