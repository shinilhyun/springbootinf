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
    * [스프링 웹 MVC](스프링-웹-mvc) 
    * 스프링 데이터 
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

- 시작은 일단 spring-boot-starter-test를 추가하는 것 부터
    * test 스콥으로 추가.
    ~~~html
        <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-test</artifactId>
              <scope>test</scope>
          </dependency>
    ~~~

---


- @SpringBootTest
    * @RunWith(SpringRunner.class)랑 같이 써야 함.
    * 빈 설정 파일은 설정을 안해주나? 알아서 찾음. (@SpringBootApplication)
    * webEnvironment
        * MOCK: mock servlet environment. 내장 톰캣 구동 안 함.
            * 서블릿 구동한 것처럼 할 수 있는데 MOCK MVC 를 이용해야함
        * RANDON_PORT, DEFINED_PORT: 내장 톰캣 사용 함.
        * NONE: 서블릿 환경 제공 안 함.
        
        
- @MockBean
    * ApplicationContext에 들어있는 빈을 Mock으로 만든 객체로 교체 함.
    * 모든 @Test 마다 자동으로 리셋.


---


###### MockMvc를 사용하여 테스트
~~~java
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SamplecontrollerTest {

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
~~~


###### RANDOM_PORT와 @MockBean을 사용하여 controller test
~~~java
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
~~~

---

#### WebTestClient 사용
 - 장점 
    - async 이므로 기다리지 않아도 됨 (RestTamplate 는 sync)
    - api가 RestTamplate에 비해 쓰기 편함 (추천)
    
    
- *WebTestClient 의존성 추가*
    ~~~html
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
    ~~~


- *WebTestClient 사용 예*
    ~~~java
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
    ~~~
 
---

- 슬라이스 테스트
    - 레이어 별로 잘라서 테스트하고 싶을 때 *(난 테스트 하고 싶은 것만 등록하고 싶다.)*
    - @JsonTest
    - @WebMvcTest
    - @WebFluxTest
    - @DataJpaTest
    - ...

---


- 기타 테스트 유틸..
    - OutputCapture
    - TestPropertyValues
    - TestRestTemplate
    - ConfigFileApplicationContextInitializer

#### OutputCapture

- 로그를 비롯해서 콘솔에 찍이는 모든 것 (기록)검사 가능


####OutputCapture 사용 예
~~~java
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
~~~


---

Spring-Boot-Devtools
---

**스프링 부트가 제공하는 optional 한 tool**

주로 개발 시 캐쉬 기능을 꺼놓거나 파일 변경 시 자동 재구동 등의 기능을 제공한다

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
    - 릴로딩 보다는 느리다. (JRebel 같은건 아님)  
    - 리스타트 하고 싶지 않은 리소스는? spring.devtools.restart.exclude  
    - 리스타트 기능 끄려면? spring.devtools.restart.enabled = false  


- 라이브 릴로드? 리스타트 했을 때 브라우저 자동 리프레시 하는 기능
    - 브라우저 플러그인 설치해야 함.  
    - 라이브 릴로드 서버 끄려면? spring.devtools.liveload.enabled = false  


- 글로벌 설정  
    - Dev-Tools 가 의존성 추가되어 있으면 아래의 설정이 1순위
    - ~/.spring-boot-devtools.properties

      
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

#### ViewResolve

스프링 MVC의 ```Contents NgotiationgViewResolver```가   
요청이 들어오면 요청의 응답을 만들 수 있는 모든 뷰를 찾아냄  
최종적으로 헤더가 원하는 ```Accept view```를 찾아서 응답  
```Accept header``` 가 있으면 이걸 토대로 응답타입을 찾음  
_ex) 만약 없으면 ```/path?format=pdf``` 와 같은 정보로 파악_


- ViewResolve 설정 제공
- HttpMessageConvertersAutoConfiguration

#### XML 메시지 컨버터 추가하기
미디어타입이 ```xml```인 경우는 컨퍼터를 추가해줘야 자동 변환을 해준다  
```html
<dependency>
   <groupId>com.fasterxml.jackson.dataformat</groupId>
   <artifactId>jackson-dataformat-xml</artifactId>
   <version>2.9.6</version>
</dependency>
```

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
```

---
