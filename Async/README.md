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

### 주방 요리사

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

- Future
  - Java 1.5에서 나온 인터페이스로 비동기적 연산의 처리 결과를 표현하기 위해 사용
  - 멀티 스레드 환경에서 처리된 어떤 데이터를 다른 스레드에 전달할 수 있고, Future는 내부적으로 Thread-safe하게 구현되어 있다.
- ExecutorService
  - 작업의 효율적인 병렬 처리를 위해 제공되는 Java 라이브러리이다.
  - 원하는 작업을 할당하기 위해 `Runnable`, `Callable`를 생성하여 Task를 전달한다.
  - Runnable : 결과값이 없는 작업
  - Callable : 결과값이 있는 작업

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

- CompletableFuture
  - Java 8에서 Future에서 처리하기 힘든 비동기 작업을 위해 등장

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

## 비동기(CompletableFuture) - Custom ThreadPool 이용

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

## 비동기(@Async) - Custom ThreadPool 이용

### 주방 요리사

```java
@Configuration
public class ThreadPoolConfig2 {


    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 25;
    private static final int QUEUE_CAPACITY = 10;
    private static final String THREAD_NAME_PREFIX = "SnackV2-";

    @Bean("threadPoolTaskExecutor2")
    public Executor threadPoolTaskExecutor2() {
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

### 요리 대리 업체

```java
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
```

### 분식점

```java
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
```

### 테스트

```java
@Test
void sync() throws Exception {
    // when
    long startTime = System.currentTimeMillis();
    List<CompletableFuture<Snack>> snacks = orders.stream().map(order -> snackShop.makeSnack(order)).toList();

    for (CompletableFuture<Snack> snack : snacks) {
        snack.get();
    }

    // then
    System.out.println("걸린 시간 : " + (System.currentTimeMillis() - startTime));
}
```

```shell
Thread[SnackV2-1,5,main] 김밥 음식을 만드는 데 1000ms 가 걸렸습니다.
Thread[SnackV2-2,5,main] 우동 음식을 만드는 데 2000ms 가 걸렸습니다.
Thread[SnackV2-3,5,main] 순대 음식을 만드는 데 3000ms 가 걸렸습니다.
Thread[SnackV2-5,5,main] 떡볶이 음식을 만드는 데 4000ms 가 걸렸습니다.
Thread[SnackV2-4,5,main] 라볶이 음식을 만드는 데 5000ms 가 걸렸습니다.
걸린 시간 : 5009
```