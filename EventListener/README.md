# EventListener

- Spring 내부에서 event 메커니즘을 가지고 있고 Bean들 사이에서 데이터를 전달할 수 있는 방법 중 하나이다.
- [Event-Driven Architecture 참고](https://www.confluent.io/resources/event-driven-microservices/?utm_medium=sem&utm_source=google&utm_campaign=ch.sem_br.nonbrand_tp.prs_tgt.technical-research_mt.xct_rgn.apac_lng.eng_dv.all_con.event-driven-architecture&utm_term=event%20driven%20architecture&creative=&device=c&placement=&gclid=Cj0KCQjwuuKXBhCRARIsAC-gM0is_5yOoAkWBrncSv11b1jArCvmTafgnTqCYO_-z_6oODhW2mLXR_IaAsZIEALw_wcB)

## Custom Event 구현

### Event 구현

- `ApplicationEvent`를 구현한 Event 클래스 생성

```java
public class CustomSpringEvent extends ApplicationEvent {

	private String message;

	public CustomSpringEvent(Object source, String message) {
		super(source);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
```

### EventListener 구현

```java
@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {

	@Override
	public void onApplicationEvent(CustomSpringEvent event) {
		System.out.println("Received spring custom event - " + event.getMessage());
	}
}
```
### EventPublisher 구현

```java
@Component
public class CustomSpringEventPublisher {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void publishCustomEvent(String message) {
		System.out.println("Publishing custom event.");
		applicationEventPublisher.publishEvent(new CustomSpringEvent(this, message));
	}
}
```

## Asynchronized Config

- `ApplicationEventMulticaster` 빈으로 등록해 `SimpleAsyncTaskExecutor` 설정해주면 비동기로 작동한다. 

```java
@Configuration
public class AsynchronousSpringEventsConfig {

	@Bean
	public ApplicationEventMulticaster applicationEventMulticaster() {
		SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
		simpleApplicationEventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
		return simpleApplicationEventMulticaster;
	}
}
```

## `@EventListener`

- 어노테이션으로 사용하면 Event에 `ApplicationEvent`을 상속받거나 Listener에 `ApplicationListener`을 구현할 필요가 없다.

```java
@Component
public class AnnotationSpringEventListener {

	@EventListener
	public void work(CustomSpringEvent event) {
		System.out.println("Received spring annotation event - " + event.getMessage());
	}
}
```

## Event for Generic

- Generic Event는 type eraser가 발생하기 때문에 런타임 시에 타입을 알 수 없어 동작하지 않는다.
- 동작하게 하기 위해서는 일부 설정이 필요하다.
- [Solution 참고](https://stackoverflow.com/questions/71452445/eventlistener-for-generic-events-with-spring)

```java
public class GenericSpringEvent<T> implements ResolvableTypeProvider {

	private T what;
	private boolean success;

	public GenericSpringEvent(T what, boolean success) {
		this.what = what;
		this.success = success;
	}

	public T getWhat() {
		return what;
	}

	public boolean isSuccess() {
		return success;
	}

	@Override
	public ResolvableType getResolvableType() {
		return ResolvableType.forClassWithGenerics(
			getClass(),
			ResolvableType.forInstance(what)
		);
	}
}
```

## `@TransactionalEventListener`

- phase 옵션 종류
  - AFTER_COMMIT : 커밋 이후 실행 
  - AFTER_ROLLBACK : 롤백 된 이후 실행
  - AFTER_COMPLETION : 트랜잭션 성공 이후 실행
  - BEFORE_COMMIT : 커밋 이전 실행

### 사전 준비

- User Entity, Repository 구현

```java
@Entity
public class User {

	@Id @GeneratedValue
	private Long id;

	public User() {

	}

	public User(Long id) {
		this.id = id;
	}
}

public interface UserRepository extends JpaRepository<User, Long> {
}
```

- Publisher, Listener 구현

```java
@Component
public class UserSpringEventPublisher {

	@Autowired
	public ApplicationEventPublisher applicationEventPublisher;

	@Transactional
	public void publish(User user) {
		applicationEventPublisher.publishEvent(user);
	}
}

@Component
@RequiredArgsConstructor
public class UserSpringEventListener {

	private final UserRepository userRepository;
	private final EntityManager entityManager;

	@TransactionalEventListener
	public void work(User user) {
		System.out.println("Received spring user event");
	}
}
```

- UserService 회원가입 로직 구현
- publisher는 의미 없고 트랜잭션 확인용

```java
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserSpringEventPublisher userSpringEventPublisher;

	@Transactional
	public void signup() throws Exception {
		userRepository.save(new User());
		userSpringEventPublisher.publish(new User());
	}
}
```

### AFTER_COMMIT

- commit 이후에 실행되기 때문에 이전에 저장했던 유저가 존재한다.
- entityManager.clear()는 캐시에 남아있기 때문에 제거해준다.

```java
public class UserSpringEventListener {
  ...
  
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void work(User user) {
    entityManager.clear();

    if (userRepository.existsById(1L)) {
      System.out.println("user that exists");
    }
    System.out.println("Received spring user event");
  }
}
```

### AFTER_ROLLBACK

- 강제로 예외를 터트려 리스너가 동작하는지 확인한다.

```java
public class UserService {
  ...
	
  @Transactional
  public void signup() throws Exception {
    userRepository.save(new User());
    userSpringEventPublisher.publish(new User());
    throw new RuntimeException();
  }
}
```

### AFTER_COMPLETION

- 현재 로직 플로우는 signup -> publish -> listener
- 트랜잭션이 종료된 이후에 리스너가 동작하기 때문에 이를 확인하기 위해서 publish에 트랜잭션 전파옵션을 REQUIRES_NEW로 주어 트랜잭션이 새로 만들어지고 종료되도록 만들었다.
- 그러면 publish가 종료된 이후에 after signup이 찍히기 전에 리스너가 동작하게 되는 것을 확인할 수 있다.

```java
public class UserService {
  ...
	
  @Transactional
  public void signup() throws Exception {
    System.out.println("before signup");
    userRepository.save(new User());
    userSpringEventPublisher.publish(new User());
    System.out.println("after signup");
  }
}
```

```java
@Component
public class UserSpringEventPublisher {

  @Autowired
  public ApplicationEventPublisher applicationEventPublisher;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void publish(User user) {
    System.out.println("before publish");
    applicationEventPublisher.publishEvent(user);
    System.out.println("after publish");
  }
}
```

```java
public class UserSpringEventListener {
  ...
  
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
  public void work(User user) {
    System.out.println("Received spring user event");
  }
}
```

### BEFORE_COMMIT

- commit 전에 동작하기 때문에 찾고자 하는 유저가 존재하지 않을 것이다.

```java

public class UserSpringEventListener {
  ...

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void work(User user) {
    entityManager.clear();

    if (!userRepository.existsById(1L)) {
      System.out.println("user that not exists");
    }
    System.out.println("Received spring user event");
  }
}
```

## Reference

- [https://www.baeldung.com/spring-events](https://www.baeldung.com/spring-events)
- [https://stackoverflow.com/questions/71452445/eventlistener-for-generic-events-with-spring](https://stackoverflow.com/questions/71452445/eventlistener-for-generic-events-with-spring)