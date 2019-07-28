스프링 부트 Practice
===================
* 스프링 부트가 제공하는 주요 기능 사용 연습
* 스프링 부트를 사용하여 웹 애플리케이션을 개발
* 스프링 부트를 사용하여 여러 데이터 기술과 연동
* 스프링 부트 애플리케이션의 운영 정보를 관리하고 모니터링 
---
 
* 스프링 부트 핵심 기능 
    * SpringApplication 
    * 외부 설정 
    * 로깅 
    * 테스트 
    * Spring-Boot-Devtools 
* 각종 기술 연동 
    * 스프링 웹 MVC 
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

자동 설정 만들기 @ConfigurationProperties
-------------------
* 덮어쓰기 방지하기
    * @ConditioanlOnMissingBean
* 빈 재정의 수고 덜기
    * @ConfigurationProperties("shin ")
    * @EnableConfigurationProperties("ShinProperties)
    * 프로퍼티 키값 자동 완성

######자동설정을 위한 Dependency 
~~~
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
---
Reference : <https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-c onfig> 
 
 사용할 수 있는 외부 설정 

    * properties 
    * YAML 
    * 환경 변수 
    * 커맨드 라인 아규먼트 
    
<br> 
 프로퍼티 우선 순위
  
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
 
 <br>
application.properties 우선 순위 (높은게 낮은걸 덮어 쓴다.)
 
    1. file:./config/ 
    2. file:./ 
    3. classpath:/config/ 
    4. classpath:/ 
 
랜덤값 설정하기 
 * ${random.*} 
 
플레이스 홀더 
 * name = ilhyun 
 * fullName = ${name} shin
 
<br>
타입-세이프 프로퍼티 @ConfigurationProperties
  
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
  
---
프로파일 
---

* @Profile 애노테이션은 어디에? 
    * @Configuration 
    * @Component 

* 어떤 프로파일을 활성화 할 것인가? 
    * spring.profiles.active 어떤 프로파일을 추가할 것인가? 
    * spring.profiles.include 프로파일용 프로퍼티 
    * application-{profile}.properties 