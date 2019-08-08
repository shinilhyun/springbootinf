스프링 부트 Practice
===================
* 스프링 부트가 제공하는 주요 기능 사용 연습
* 스프링 부트를 사용하여 웹 애플리케이션을 개발
* 스프링 부트를 사용하여 여러 데이터 기술과 연동
* 스프링 부트 애플리케이션의 운영 정보를 관리하고 모니터링 
* Spring Boot, Maven, JPA, MySQL, PostgreSQL, redis, mongoDB, newo4j
---
 
* [스프링 부트 핵심 기능](#스프링-부트-핵심-기능) 
    * [자동 설정 만들기](#자동-설정-만들기)
    * [SpringApplication](#SpringApplication) 
    * [외부설정](#외부설정) 
    * [프로파일](#프로파일) 
    * [로깅](#로깅) 
    * [테스트](#테스트)
    * [Spring-Boot-Devtools](#spring-boot-devtools) 
* [각종 기술 연동](#각종-기술-연동) 
    * [스프링 웹 MVC](#스프링-웹-mvc) 
    * [스프링 데이터](#스프링-데이터) 
    * 스프링 시큐리티 
    * REST 클라이언트 
* 스프링 부트 운영 
    * 엔드포인트 
    * 메트릭스 
    * 모니터링
---
스프링 부트 핵심 기능
===

자동 설정 만들기
-------------------
* @ConfigurationProperties
* 덮어쓰기 방지하기
    * @ConditioanlOnMissingBean
<p>

* 빈 재정의 수고 덜기
    * @ConfigurationProperties("shin ")
    * @EnableConfigurationProperties("ShinProperties)
    * 프로퍼티 키값 자동 완성

###### 자동설정을 위한 Dependency 
~~~html
<dependency>
    <groupId>​org.springframework.boot​</groupId>
    <artifactId>​spring-boot-configuration-processor​</artifactId>    
    <optional>​true​</optional> 
</dependency> 
~~~
---
SpringApplication
---
Reference :  <https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config>

* ApplicationEvent 등록
    * ApplicationContext를 만들기 전에 사용하는 리스너는 @Bean으로 등록할 수 없다
        * SpringApplication.addListners()
    * WebApplicationType 설정
    *애플리케이션 아규먼트 사용하기
        * ApplicationArguments를 빈으로 등록해주니까 가져다 쓰면 됨.
    * 애플리케이션 실행한 뒤 뭔가 실행하고 싶을 때
        * ApplicationRunner (추천) 또는 CommandLineRunner
        * 순서지정 : @Order

---     
외부설정
----------------
Reference : <https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config> 
 
 
#### 사용할 수 있는 외부 설정
* properties 
* YAML 
* 환경 변수 
* 커맨드 라인 아규먼트   
 
 
### 프로퍼티 우선 순위

1. 유저 홈 디렉토리에 있는 spring-boot-dev-tools.properties 
2. 테스트에 있는 @TestPropertySource 
3. @SpringBootTest 애노테이션의 properties 애트리뷰트 
4. 커맨드 라인 아규먼트 
5. SPRING_APPLICATION_JSON (환경 변수 또는 시스템 프로티) 에 들어있는 프로퍼티 
6. ServletConfig 파라미터 
7. ServletContext 파라미터 
8. java:comp/env JNDI 애트리뷰트 
9. System.getProperties() 자바 시스템 프로퍼티 
10.OS 환경 변수 
11.RandomValuePropertySource 
12.JAR 밖에 있는 특정 프로파일용 application properties 
13.JAR 안에 있는 특정 프로파일용 application properties 
14.JAR 밖에 있는 application properties 
15.JAR 안에 있는 application properties 
16.@PropertySource 
17.기본 프로퍼티 (SpringApplication.setDefaultProperties)   
  
  
### application.properties 우선 순위 
(높은게 낮은걸 덮어 쓴다.) 
1. file:./config/ 
2. file:./ 
3. classpath:/config/ 
4. classpath:/ 
 
* 랜덤값 설정하기 
    * ${random.*} 
 
 
* 플레이스 홀더 
     * name = ilhyun 
     * fullName = ${name} shin
 
 
#### 타입-세이프 프로퍼티 @ConfigurationProperties  
* 여러 프로퍼티를 묶어서 읽어올 수 있음 
* 빈으로 등록해서 다른 빈에 주입할 수 있음 
    * @EnableConfigurationProperties 
    * @Component ○ @Bean 
    * 융통성 있는 바인딩 
    * context-path (케밥) 
    * context_path (언드스코어) 
    * contextPath (캐멀) 
    * CONTEXTPATH 
* 프로퍼티 타입 컨버전 
    * @DurationUnit
* 프로퍼티 값 검증 
    * @Validated 
    * JSR-303 (@NotNull, ...) 
* 메타 정보 생성 
* @Value 
    * SpEL 을 사용할 수 있지만... 
    * 위에 있는 기능들은 전부 사용 못함. 
 
  
--- ---
프로파일 
---

#### @Profile 애노테이션은 어디에? 
* @Configuration 
* @Component 

#### 어떤 프로파일을 활성화 할 것인가? 
* spring.profiles.active 어떤 프로파일을 추가할 것인가? 
* spring.profiles.include 프로파일용 프로퍼티 
* application-{profile}.properties 

--- ---
로깅
------

#### 로깅 퍼사드 VS 로거
* Commons Logging, SLF4j
* JUL, Log4J2, Logback  
_최종적으론 Logback이 로그를 찍게됨(Commons Logging, SLF4j 통해서)_
    

#### 스프링 5에 로거 관련 변경 사항
* https://docs.spring.io/spring/docs/5.0.0.RC3/spring-framework-reference/overview.html#overview-logging
* Spring-JCL
    * Commons Logging -> SLF4j or Log4j2
    * pom.xml에 exclusion 안해도 됨.


#### 스프링 부트 로깅
* 기본 포맷
* --debug (일부 핵심 라이브러리만 디버깅 모드로)
* --trace (전부 다 디버깅 모드로)
* 컬러 출력: spring.output.ansi.enabled
* 파일 출력: logging.file 또는 logging.path
* 로그 레벨 조정: logging.level.패지키 = 로그 레벨

<br>

#### 커스텀 로그 설정 파일 사용하기 
https://docs.spring.io/spring-boot/docs/current/reference/html/howto-logging.html 
* Logback: logback-spring.xml(이거 추천.. Logback extention 사용가능)
    ~~~html
    <?xml version="1.0" encoding="UTF-8"?>
    <configuration>
        <include resource="org/springframework/boot/logging/logback/base.xml"/>
        <logger name="com.shin" level="DEBUG"/>
    </configuration>
    ~~~
* Log4J2: log4j2-spring.xml
* JUL (비추): logging.properties
* Logback extension
    * 프로파일 <springProfile name=”프로파일”>
    * Environment 프로퍼티 <springProperty>
    
<br>

#### 로거를 Log4j2로 변경하기
* https://docs.spring.io/spring-boot/docs/current/reference/html/howto-logging.html#howto-configure-log4j-for-logging

###### 의존성 exclusion 및 추가
~~~html
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>org.springframework.boot:spring-boot-starter-logging:2.1.6.RELEASE</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>
~~~

-------
테스트
---

시작은 일단 spring-boot-starter-test를 추가하는 것 부터  
    
   
##### Test 의존성 스코프로 추가
```html
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
</dependency>
```


---  

**@SpringBootTest**    
  ```@RunWith(SpringRunner.class)```랑 같이 써야 함.  
  빈 설정 파일은 설정을 안해주나? 알아서 찾음. ```(@SpringBootApplication)```  
 * __webEnvironment__
    * ```MOCK```: mock servlet environment. 내장 톰캣 구동 안 함.
        * 서블릿 구동한 것처럼 할 수 있는데 ```MOCK MVC``` 를 이용해야함
    * ```RANDON_PORT```, ```DEFINED_PORT```: 내장 톰캣 사용 함.
    * ```NONE```: 서블릿 환경 제공 안 함.
        
        
- **@MockBean**
    * ```ApplicationContext```에 들어있는 빈을 ```Mock```으로 만든 객체로 교체 함.
    * 모든 ```@Test``` 마다 자동으로 리셋.


---


##### MockMvc를 사용하여 테스트
```java
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SamplecontrollerTest {

    //서버를 구동하지 않고 mockMvc를 사용하여 테스트
    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
            .andExpect(status().isOk())
            .andExpect(content().string("hello ilhyun"))
            .andDo(print());
    }

}
```


##### RANDOM_PORT와 @MockBean을 사용하여 controller test
```java
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SamplecontrollerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    //서비스는 목으로 대체하여 controller만 테스트
    @MockBean
    SampleService mockSampleService;

    @Test
    public void hello() {

        //controller만 테스트 하고싶다!! 서비스는 목으로 대체!! 이제 서비스는 shinilhyun을 리턴!
        when(mockSampleService.getName()).thenReturn("shinilhyun");

        String result = testRestTemplate.getForObject("/hello", String.class);
        assertThat(result).isEqualTo("hello shinilhyun");
    }

}
```

---

### WebTestClient 사용
 
 - 장점 
    - async 이므로 기다리지 않아도 됨 _(RestTamplate 는 sync)_
    - api가 ```RestTamplate```에 비해 쓰기 편함 (추천)
    
    
##### WebTestClient 의존성 추가
```html
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```


##### WebTestClient 사용 예
```java
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SamplecontrollerTest {

    //webTestClient 사용
    // async 이므로 기다리지 않아도 됨 (RestTamplate = sync)
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    SampleService mockSampleService;

    @Test
    public void hello() {
        when(mockSampleService.getName()).thenReturn("shinilhyun");
        webTestClient.get().uri("/hello").exchange()
            .expectStatus().isOk()
            .expectBody(String.class).isEqualTo("hello shinilhyun");
    }

}
```
 
---

### 슬라이스 테스트

###### 레이어 별로 잘라서 테스트하고 싶을 때 *(난 테스트 하고 싶은 것만 등록하고 싶다.)*
- @JsonTest
- @WebMvcTest
- @WebFluxTest
- @DataJpaTest
- ...

---

### 기타 테스트 유틸  
- OutputCapture
- TestPropertyValues
- TestRestTemplate
- ConfigFileApplicationContextInitializer

#### OutputCaptur  
로그를 비롯해서 콘솔에 찍이는 모든 것 (기록)검사 가능  

##### OutputCapture 사용 예  
```java
@RunWith(SpringRunner.class)
@WebMvcTest(Samplecontroller.class)
public class SamplecontrollerTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @MockBean
    SampleService mockSampleService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {

        when(mockSampleService.getName()).thenReturn("shinilhyun");

        mockMvc.perform(get("/hello"))
            .andExpect(content().string("hello shinilhyun"));

        assertThat(outputCapture.toString())
            .contains("shinilhyun")
            .contains("skip");
    }

}
```


---

Spring-Boot-Devtools
---

### Spring Boot Devtools?

>**스프링 부트가 제공하는 optional 한 tool**  
주로 캐쉬 기능을 꺼놓거나 자동 재구동 등의 기능을 제공한다

---

##### 의존성 추가
```html
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

### 주요기능 

- 캐시 설정을 개발 환경에 맞게 변경.(가장 기본적인 기능)  


- 클래스패스에 있는 파일이 변경 될 때마다 자동으로 재시작
    - 직접 껐다 켜는거 (cold starts)보다 빠른다. 왜?  
    - 릴로딩 보다는 느리다. (```JRebel``` 같은건 아님)  
    - 리스타트 하고 싶지 않은 리소스는? ```spring.devtools.restart.exclude```  
    - 리스타트 기능 끄려면? ```spring.devtools.restart.enabled = false```  


- 라이브 릴로드? 리스타트 했을 때 브라우저 자동 리프레시 하는 기능
    - 브라우저 플러그인 설치해야 함.  
    - 라이브 릴로드 서버 끄려면? ```spring.devtools.liveload.enabled = false```  


- 글로벌 설정  
    - Dev-Tools 가 의존성 추가되어 있으면 아래의 설정이 1순위
    - ```~/.spring-boot-devtools.properties```

      
- 리모트 애플리케이션(비추)

> 가끔 Reload가 실패하거나 껏다 자동으로 리로드하는 것을 선호하지 않아서 자주 쓰진 않을 것 같다.
> 원래 FrontEnd 쪽에서 많이 쓰는 기능이 Spring Boot에도 있기에 작성해 봄

---
각종 기술 연동
===
   
스프링 웹 MVC
---

Reference : https://docs.spring.io/spring/docs/5.0.7.RELEASE/spring-framework-reference/web.html#spring-web  

스프링 부트 MVC의 자동 설정으로 제공하는 여러 기본 기능을 알아보자
  
#### 스프링 MVC 확장
스프링 MVC가 제공하는 기능을 사용하면서 추가적으로 더 설정하고 싶다      
@Configuration + WebMvcConfigurer

#### 스프링 MVC 재정의  
@Configuration + @EnableWebMvc
만약 ```@EnableWebMvc``` 사용하여 설정하면 스프링 웹 MVC가 설정한 것들을 사용안함(직접 다 설정해야함)
이런 경우는 거의 사용 안할듯..  


#### HttpMessageConverters
Reference : https://docs.spring.io/spring/docs/5.0.7.RELEASE/spring-framework-reference/web.html#mvc-config-message-converters  
 
 스프링에서 제공하는 스프링 MVC의 인터페이스 중 하나  
 HTTP 요청 본문을 객체로 변경하거나, 객체를 HTTP 응답 본문으로 변경할 때 사용   
_{“username”:”keesun”, “password”:”123”} <-> User_  

- @ReuqestBody
- @ResponseBody
  
---

### ViewResolve

> 뷰 영역 구현

컨트롤러는 최종적으로 결과를 출력할 뷰와 뷰에 전달할 객체를 담고 있는 ```ModelAndView``` 객체를 리턴한다.  
```DispatherServlet```은 ```ViewResolver```를 사용하여 결과를 출력할 ```View 객체```를 구하고, 구한 View 객체를 이용하여 내용을 생성한다.

스프링 MVC의 ```Contents NgotiationgViewResolver```가 
요청이 들어오면 요청의 응답을 만들 수 있는 모든 뷰를 찾아내고  
최종적으로 헤더가 원하는 ```Accept view```를 찾아서 응답한다.  
  
```Accept header``` 가 있으면 이걸 토대로 응답타입을 찾는다.  
_ex) 만약 없으면 ```/path?format=pdf``` 와 같은 정보로 파악_


- ViewResolve 설정 제공
- HttpMessageConvertersAutoConfiguration


### 응답이 JSON 형실일 때 Test Case
```java
@Test
public void createUser_JSON() throws Exception {

    String userJson = "{\n" +
        "\"username\":\"ilhyun\",\n" +
        "  \"password\" : \"123\"\n" +
        "}";

    mockMvc.perform(post("/users/create")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .content(userJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", is(equalTo("ilhyun"))))
        .andExpect(jsonPath("$.password", is(equalTo("123"))));
}

//////////////// controller///////////////////////

@PostMapping("/users/create")
public User create(@RequestBody User user) {
    return user;
}

```

### 응답이 XML 형실일 때 Test Case
```jackson-dataformat-xml 의존성 필요```
```java
@Test
public void createUser_XML() throws Exception {

    String userJson = "{\n" +
        "\"username\":\"ilhyun\",\n" +
        "  \"password\" : \"123\"\n" +
        "}";
  
    mockMvc.perform(post("/users/create")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_XML)
        .content(userJson))
        .andExpect(status().isOk())
        .andExpect(xpath("/User/username").string("ilhyun"))
        .andExpect(xpath("/User/password").string("123"));
}

//////////////// controller///////////////////////
@PostMapping("/users/create")
public User create(@RequestBody User user) {
    return user;
}
```

---

### 정적 리소스 지원

정적 리소스 맵핑 `````“ /**”`````  
- 기본 리소스 위치  
    - ```classpath:/static```  
    - ```classpath:/public```
    - ```classpath:/resources/```
    - ```classpath:/META-INF/resources```
    - _예) `````“/hello.html”`````요청이 들어오면  => ````/static/hello.html````  
    - ```spring.mvc.static-path-pattern```: 맵핑 설정 변경 가능
    - ```spring.mvc.static-locations```: 리소스 찾을 위치 변경 가능
- Last-Modified 헤더를 보고 304 응답을 보냄.
- ```ResourceHttpRequestHandler```가 처리함.
    - ```WebMvcConfigurer```의 ```addRersourceHandlers```로 커스터마이징 할 수 있음  


#### /m/으로 시작하는 경로 설정
~~~java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/m/**")
            .addResourceLocations("classpath:/m/")
            .setCachePeriod(20);
    }
}
~~~

---
### 웹 JAR

>```웹 JAR```란 클라이언트에서 사용하는 ```js 라이브러리```를 ```jar```로 제공하는 기능이다.  
스프링부트는 웹 JAR에 대한 기본 매핑도 제공한다. 템플릿을 이용하여 동적 컨텐츠를 생성하거나 정적 리소스에서도 ```웹 JAR에 있는 css나 js를 참조```할 수 있다.

- 웹JAR 맵핑 ```“ /webjars/**”```  

---

#### jQuery를 webjars로 추가하기

###### 메이븐 중앙저장소에서 제공하는 코드를 복사하여 의존성을 추가한다.
```html
<!-- https://mvnrepository.com/artifact/org.webjars.bower/jquery -->
<dependency>
    <groupId>org.webjars.bower</groupId>
    <artifactId>jquery</artifactId>
    <version>3.2.1</version>
</dependency>
```

###### 의존성을 추가한 뒤, html 파일에 제이쿼리를 추가
```html
<script src="/webjars/jquery/dist/jquery.min.js"></script>
<script>
   $(function() {
       console.log("ready!");
   });
</script>
```

---

#### 버전 생략하고 사용하려면  

```webjars-locator-core``` 의존성을 추가하면 리소스에서 webjars를 사용할 때 버전을 생략할 수 있다.

--- 

### index 페이지와 파비콘

웰컴 페이지
- index.html 찾아 보고 있으면 제공.
- index.템플릿 찾아 보고 있으면 제공.
- 둘 다 없으면 에러 페이지.
파비콘
- favicon.ico
- 파이콘 만들기 https://favicon.io/
- 파비콘이 안 바뀔 때?
    - https://stackoverflow.com/questions/2208933/how-do-i-force-a-favicon-refresh

---

### Thymeleaf

#### 스프링 부트가 자동 설정을 지원하는 템플릿 엔진  
- FreeMarker
- Groovy
- __Thymeleaf__
- Mustache
    
#### JSP를 권장하지 않는 이유  
- JAR 패키징 할 때는 동작하지 않고, WAR 패키징 해야 함.
- Undertow는 JSP를 지원하지 않음.
- https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-jsp-limitations

#### Thymeleaf 사용하기
- https://www.thymeleaf.org/
- https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html
- 의존성 추가: ```spring-boot-starter-thymeleaf```
- 템플릿 파일 위치: ```/src/main/resources/template/```
- 예제: https://github.com/thymeleaf/thymeleafexamples-stsm/blob/3.0-master/src/main/webapp/WEB-INF/templates/seedstartermng.html

---

### HtmlUnit

> HTML 템플릿 뷰 테스트를 보다 전문적으로 하자.
> HTML을 테스트하기 위한 UNIT
- http://htmlunit.sourceforge.net/
- http://htmlunit.sourceforge.net/gettingStarted.html

##### 의존성 추가
```html
<dependency>
   <groupId>org.seleniumhq.selenium</groupId>
   <artifactId>htmlunit-driver</artifactId>
   <scope>test</scope>
</dependency>
<dependency>
   <groupId>net.sourceforge.htmlunit</groupId>
   <artifactId>htmlunit</artifactId>
   <scope>test</scope>
</dependency>
```

@Autowire WebClient

#### HtmlUnit을 활용한 테스트
```java
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    WebClient webClient;

    @Autowired
    MockMvc mockMvc;

    //htmlunit의 webClient를 이용한 테스트
    @Test
    public void hello() throws Exception {
        HtmlPage page = webClient.getPage("/hello");
        HtmlHeading1 h1 = page.getFirstByXPath("//h1");
        assertThat(h1.getTextContent()).isEqualToIgnoringCase("ilhyun");
    }

    //MockMVC를 이용한 테스트
    @Test
    public void helloTestByMock() throws Exception {
        mockMvc.perform(get("/hello"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("ilhyun")));
    }

}
```

--- 

### ExceptionHandler

스프링 @MVC 예외 처리 방법  
- @ControllerAdvice
- @ExceptionHandler

스프링 부트가 제공하는 기본 예외 처리기  
- BasicErrorController
    - HTML과 JSON 응답 지원
- 커스터마이징 방법
    - ErrorController 구현

커스텀 에러 페이지
- 상태 코드 값에 따라 에러 페이지 보여주기
- src/main/resources/static|template/error/
- 404.html
- 5xx.html
- ErrorViewResolver 구현

---

### Hypermedia As The Engine Of Application State

- 서버: 현재 리소스와 연관된 링크 정보를 클라이언트에게 제공한다.
- 클라이언트: 연관된 링크 정보를 바탕으로 리소스에 접근한다.
- 연관된 링크 정보
    - Relation
    - Hypertext Reference)
- spring-boot-starter-hateoas 의존성 추가
- https://spring.io/understanding/HATEOAS
- https://spring.io/guides/gs/rest-hateoas/
- https://docs.spring.io/spring-hateoas/docs/current/reference/html/

ObjectMapper 제공
- spring.jackson.*
- Jackson2ObjectMapperBuilder

LinkDiscovers 제공
- 클라이언트 쪽에서 링크 정보를 Rel 이름으로 찾을때 사용할 수 있는 XPath 확장 클래스

---

#### SOP과 CORS
- Single-Origin Policy
- Cross-Origin Resource Sharing


- Origin?
    - URI 스키마 (http, https)
    - hostname (whiteship.me, localhost)
    - 포트 (8080, 18080)

스프링 MVC @CrossOrigin
- <https://docs.spring.io/spring/docs/5.0.7.RELEASE/spring-framework-reference/web.html#mvc-cors>
- @Controller나 @RequestMapping에 추가하거나
```java
@GetMapping("/hello")
    @CrossOrigin(origins = "http://localhost:8180")
    public Resource<Hello> hello() {
        Hello hello = new Hello();
        hello.setPrefix("Heym,");
        hello.setName("ilhyun");

        //HATEOAS 를 만족하는 api ("_links":{"self":{"href":"localhost:8080/hello"}}} 추가
        Resource<Hello> helloResource = new Resource<>(hello);
        helloResource.add(linkTo(methodOn(SampleController.class).hello()).withSelfRel());
        return helloResource;
    }
```
- WebMvcConfigurer 사용해서 글로벌 설정
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:8081");
    }
}
```

---

스프링 데이터
---

### 인메모리 데이터 베이스

지원하는 인-메모리 데이터베이스  

- H2 (추천, 콘솔 때문에...)
- HSQL
- Derby
  
  Spring-JDBC가 클래스패스에 있으면 자동 설정이 필요한 빈을 설정 해줍니다.
    - DataSource
    - JdbcTemplate

인-메모리 데이터베이스 기본 연결 정보 확인하는 방법
- URL: “testdb”
- username: “sa”
- password: “”

H2 콘솔 사용하는 방법
- ```spring-boot-devtools```를 추가하거나...
- ```spring.h2.console.enabled=true``` 만 추가.
- /h2-console로 접속 (이 path도 바꿀 수 있음)
실습 코드

- ```CREATE TABLE USER (ID INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))```
- ```INSERT INTO USER VALUES (1, ‘keesun’)```

---

### 지원하는 DBCP

1. HikariCP (기본)
    - <https://github.com/brettwooldridge/HikariCP#frequently-used>
2. Tomcat CP
3. Commons DBCP2

#### DBCP 설정

- spring.datasource.hikari.*
- spring.datasource.tomcat.*
- spring.datasource.dbcp2.*

#### MySQL 커넥터 의존성 추가
```html
<dependency>
   <groupId>mysql</groupId>
   <artifactId>mysql-connector-java</artifactId>
</dependency>
```

#### MySQL 추가 (도커 사용)

```shell script
docker run -p 3306:3306 --name mysql_boot -e MYSQL_ROOT_PASSWORD=1 -e MYSQL_DATABASE=springboot -e MYSQL_USER=keesun -e MYSQL_PASSWORD=pass -d mysql
docker exec -i -t mysql_boot bash
mysql -u root -p
```

#### MySQL용 Datasource 설정
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/springboot?useSSL=false
spring.datasource.username=keesun
spring.datasource.password=pass
```

---
### PostgreSQL

####의존성 추가
```html
<dependency>
   <groupId>org.postgresql</groupId>
   <artifactId>postgresql</artifactId>
</dependency>
```

PostgreSQL 설치 및 서버 실행 (docker)
```shell script
docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=keesun -e POSTGRES_DB=springboot --name postgres_boot -d postgres

docker exec -i -t postgres_boot bash

su - postgres

psql springboot
```


데이터베이스 조회
\list

테이블 조회
\dt

쿼리
SELECT * FROM account;

---

### 스프링 데이터 JPA

> ORM(Object-Relational Mapping)과 JPA (Java Persistence API)
> - 객체와 릴레이션을 맵핑할 때 발생하는 개념적 불일치를 해결하는 프레임워크
> - <http://hibernate.org.orm/what-si-an-orm/>
> - JPA: ORM을 위한 자바 (EE) 표준

스프링 데이터 JPA
 - Repository 빈 자동 생성
 - 쿼리 메소드 자동 구현
 - ```@EnableJpaRepositories``` (스프링 부트가 자동으로 설정)

스프링 데이터 JPA 의존성 추가
```html
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

스프링 데이터 JPA 사용하기
- @Entity 클래스 만들기
- Repository 만들기

스프링 데이터 리파지토리 테스트 만들기
- H2 DB를 테스트 의존성에 추가하기
- @DataJpaTest (슬라이스 테스트) 작성

---

### 데이터 베이스 초기화

JPA를 사용한 데이터베이스 초기화
- ```spring.jpa.hibernate.ddl-auto```
    - ```create```	기존에 생성되 있던 테이블들을 삭제하고 새로 만듭니다.
    - ```create-drop```	create와 같은 동작을 하나 종료시에 DROP합니다.
    - ```update```	변경된 부분만 반영합니다.
    - ```validate```	테이블과 Entity가 매핑되는지 유효성 검사를 실행합니다.
    - ```none```	초기화 동작을 사용하지 않습니다.
- ```spring.jpa.generate-dll=true```로 설정 해줘야 동작함.

>운영에서는 ```dll-auto=validate``` 로하고 ```generate-ddl=false``` 가 안전함

SQL 스크립트를 사용한 데이터베이스 초기화
- schema.sql 또는 schema-${platform}.sql
- data.sql 또는 data-${platform}.sql
- ${platform} 값은 spring.datasource.platform 으로 설정 가능.

> 테스트 후 운영에 반영할 때는 스키마 파일을 통해 설정해주면 깔끔함
> dll-auto=update는 개발할 때는 편하지만 운영 시에는 위험(변경된 컬럼을 삭제하지 않고 추가만 될 수 있음)

---

### 데이터베이스 마이그레이션

> Flyway와 Liquibase가 대표적인데, Flyway를 사용
> <https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/reference/htmlsingle/#howto-execute-flyway-database-migrations-on-startup>

의존성 추가
- ```org.flywaydb:flyway-core```


마이그레이션 디렉토리

- ```db/migration``` 또는 ```db/migration/{vendor}```
- ```spring.flyway.locations```로 변경 가능

마이그레이션 파일 이름
- V숫자__이름.sql
- V는 꼭 대문자로.
- 숫자는 순차적으로 (타임스탬프 권장)
- 숫자와 이름 사이에 언더바 두 개.
- 이름은 가능한 서술적으로.
- 에러시 properties에 ```spring.flyway.baseline-on-migrate = true``` 추가