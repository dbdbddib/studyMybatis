## 좋은 프래그래머는 입력 데이터형과 출력 데이터형이 맞는 개발자!
public String {
  return String
}

# 경로
+ studyMybatis
  + mybatisapp  ( Thymeleaf )
  + mustacheapp ( mustache )

+ added dependencies
  + Spring Boot DevTools
  + Lombok
  + Spring Configuration Processor
  + Spring Web
  + Mustache
  + MyBatis Framework
  + MySQL Driver

    
# CRUD
CRUD는 “Create, Read, Update, Delete” 의 약어로 데이터베이스와 같은 시스템에서 기본적인 데이터 관리 기능을 의미
CRUD는 거의 모든 시스템에 적용되는 가장 기본
```
 이름     SQL문

CREATE    INSERT
READ      SELECT
UPDATE    UPDATE
DELETE    DELETE
```

### REST API 에서의 CRUD

* REST API (Representational State Transfer)
    + 웹 서비스를 개발하고 서로 다른 시스템 간에 데이터를 주고받을 수 있도록 하는 아키텍처 스타일 (아키텍쳐 스타일: 시스템을 설계하는 다양한 방법)
    + 다른 프로그램들이 서로 통신하고 데이터를 교환할 수 있도록 해주는 일종의 규약

* 클라이언트 <-> 서버간 HTTP 프로토콜을 이용해 REST API를 설계하고 작성할 때에도 CRUD 개념이 사용

```
 이름     HTTP METHOD

CREATE    POST
READ      GET
UPDATE    PUT, PATCH
DELETE    DELETE
```

* Create (생성)
  - HTTP 메서드: POST
  + 예시: 새로운 사용자를 생성하는 경우
  + 요청: POST /users
  + 요청 본문(body): 새로운 사용자의 정보를 포함하는 JSON 또는 XML 데이터
  + 응답: 새로운 사용자의 ID와 함께 성공 메시지 또는 적절한 상태 코드 (예: 201 Created)


* Read (읽기):
  + HTTP 메서드: GET
  + 예시: 모든 사용자 목록을 가져오는 경우
  + 요청: GET /users
  + 응답: 모든 사용자의 목록을 포함하는 JSON 또는 XML 데이터 또는 적절한 상태 코드와 함께 성공 메시지 (예: 200 OK)


* Update (갱신):
  + HTTP 메서드: PUT 또는 PATCH
  + 예시: 특정 사용자의 정보를 업데이트하는 경우
  + 요청: PUT /users/{id} 또는 PATCH /users/{id}  {put: 전체 업데이트 (모든 데이터 전송), patch: 부분 업데이트 (변경할 데이터만 전송)}
  + 요청 본문: 업데이트할 사용자의 정보를 포함하는 JSON 또는 XML 데이터
  + 응답: 적절한 상태 코드와 함께 성공 메시지 또는 업데이트된 사용자의 정보


* Delete (삭제):
  + HTTP 메서드: DELETE
  + 예시: 특정 사용자를 삭제하는 경우
  + 요청: DELETE /users/{id}
  + 응답: 적절한 상태 코드와 함께 성공 메시지 또는 삭제된 사용자의 정보


## MVC 패턴의 CRUD 프로젝트 만드는 순서
+ [화면(Mustache/Thymeleaf/React) -> Controller -> ServierImpl -> IService -> Mybatis/JPA -> RDBMS]
+ [RDBMS -> Mybatis/JPA -> IService -> ServierImpl -> Controller -> 화면(Mustache/Thymeleaf/React)]
0. 화면에 기능을 서술한다.
1. 화면에서 사용하는 데이터들을 필요한것들 열거하고 논리모델링, 정규화한다 (id 를 키로 다른 테이블에서는 id 를 외래키)
2. 물리모델링을 한다.
3. create table 한다.
4. SpringBoot, create table 한 테이블의 interface 를 java 로 생성한다. getId, setId, getName, setName
5. 4번에서 만든 interface 를 상속하여 Dto 를 java 로 생성한다.
6. resources/mapper/**/쿼리.xml 파일에 쿼리 insert, update, delete, select 만든다. (interface) MybatisMapper Java 에 메소드를 생성한다.
7. (interface) MybatisMapper Java 에 있는 메소드를 (interface) IService java 에 생성한다.
8. IService 를 상속받아서 ServiceImpl java 를 생성하고 메소드를 구현한다. MybatisMapper 를 실행한다.
   - ServiceImpl 의 insert 메소드는 MybatisMapper 의 insert 메소드를 실행한다.
   - ServiceImpl 의 update 메소드는 MybatisMapper 의 update 메소드를 실행한다.
   - ServiceImpl 의 deleteById 메소드는 MybatisMapper 의 deleteById 메소드를 실행한다.
   - ServiceImpl 의 updateDeleteFlag 메소드는 MybatisMapper 의 updateDeleteFlag 메소드를 실행한다.
   - ServiceImpl 의 findById 메소드는 MybatisMapper 의 findById 메소드를 실행한다.
   - ServiceImpl 의 findAllByNameContains 메소드는 MybatisMapper 의 findAllByNameContains 메소드를 실행한다.
9. Controller 화면템플릿용도 (WEB 화면용 템플릿으로 화면을 그린다. return 이 String "디렉토리명/html 파일명" )
10. 화면템플릿용도 Controller 는 GET @RequestParam, POST @ModelAttribute 로 받아서 데이터 처리하고 화면에 Model 출력한다.
11. RestController ajax 또는 axios 또는 api 용도 (JSON 으로 처리된다.)
12. JSON 용도 RestController 는 GET @PathVariable, POST @RequestBody, DELETE @PathVariable, PATCH @PathVariable 로 받아서 데이터 처리하고 ResponseEntity 로 리턴한다.




# CODE

+ DAO (Data Access Object)
    + 데이터베이스와의 상호작용을 캡슐화한 객체
    + DAO는 데이터베이스의 CRUD (Create, Read, Update, Delete) 작업 수행
    + 데이터베이스와의 연결 및 쿼리 실행 등의 작업을 처리

+ DTO (Data Transfer Object)
    + 데이터 전송 객체
    + DTO는 데이터베이스나 다른 시스템과 데이터를 주고받기 위한 객체
    + 주로 데이터의 상태를 캡슐화하고 전달하는 역할
    + DTO는 데이터베이스와의 상호작용을 직접적으로 하지 않음
    + DAO가 DTO 객체를 사용하여 데이터를 전송

+ ICategory..
    + 화면출력 경우 ICategory 를 사용
    + db값, 값을 가져올 때 CategoryDto 사용
      
+ 인터페이스 사용경우
    + 하나의 메소드의 리턴형이 인터페이스면 서로다른 클래스에서의 필드 추가가 가능하기 때문
    + 추상형.. 어떤 리턴값이 올지 모른다?? 정해진 값이 아니다
    + 코드의 재사용..
    + if.. 서로다른 로그인 화면 두개가 있는데 인터페이스로 선언된 하나의 메소드로 구현 가능하다..
      

+ CONTROLLER
 
    + 리턴형은 String 만  ->  화면 템플릿만 리턴한다는 뜻

    + controller -> ServiceImpl -> mapper -> xml -> 자바 인터페이스를 통해 db에서 SQL 쿼리를 실행 -> 결과 객체로 반환

    + 요청 request ( 클라이언트 )과 응답 response ( 서버 ) 에서의 응답
    + 서버 역할
    + 호출에 대한 응답
    + 소스 구현은 서비스에서..
    + 매개변수  : 클라이언트 -> 서버
    + 리턴형    : 서버 -> 클라이언트

   ``` 
   @GetMapping("/html/category_list")
      public String categoryOld(Model model, @RequestParam String name, @RequestParam int page) {}

   // 
  (http://localhost:8089/catweb/html/category_list?page=1&name=유병훈)
  이런식으로 매개변수 값까지 입력해야 메소드에 접근한다

+ MVC 패턴 (Model-View-Controller)
    + 사용자가 애플리케이션에서 작업을 수행하면, 뷰(View)는 사용자의 입력을 감지하고 컨트롤러(Controller)에 전달
    + 컨트롤러는 사용자 입력을 처리하고 적절한 모델(Model) 기능을 호출하여 데이터를 검색, 수정 또는 저장
    + 모델은 데이터와 관련된 비즈니스 로직을 수행하고, 필요한 경우 데이터베이스와 상호 작용합니다. 모델은 작업이 완료되면 결과를 컨트롤러에 반환
    + 컨트롤러는 모델의 결과를 받아 뷰에 전달
    + 뷰는 이 데이터를 사용하여 사용자에게 보여지는 화면을 업데이트
      
+ MVC패턴의 설계원칙
    + 모델, 뷰, 컨트롤러는 독립적으로 작동하고, 각각의 역할에 집중해야 합니다.
        + 모델(Model): 데이터와 비즈니스 로직을 처리합니다. 데이터의 저장, 검색, 수정 등에 집중하며, 뷰와 컨트롤러에 대한 정보는 가지고 있지 않습니다.
        + 뷰(View): 사용자 인터페이스를 담당하며, 모델에서 전달받은 데이터를 화면에 표시합니다. 뷰는 사용자의 입력을 받아 컨트롤러에 전달하고, 데이터 표시에만 집중합니다.
        + 컨트롤러(Controller): 사용자 입력을 받아 모델과 뷰 사이에서 통신을 관리합니다. 사용자의 요청을 해석하고, 적절한 모델 함수를 호출하여 데이터를 처리한 후 결과를 뷰에 전달합니다.

## MemberServiceImpl.java -> cudInfoDto.setCreateInfo() 알고리즘
```
MemberServiceImpl.java

@Override
    public IMember insert(CUDInfoDto cudInfoDto, IMember member) {
        if ( !this.isValidInsert(member) ) {
            return null;
        }
        MemberDto dto = MemberDto.builder().build();
        dto.copyFields(member);
        dto.setPassword(encoder.encode(dto.getPassword()));
        cudInfoDto.setCreateInfo(dto);
        this.memberMybatisMapper.insert(dto);
        return dto;
    }
```

  ```
  CUDInfoDto.java
  
  public void setCreateInfo(IBase iBase) {    // 자식타입은 부모타입의 기능을 사용할 수 있다
        if (iBase == null) {
            return;
        }
        iBase.setCreateDt(this.getSystemDt());
        iBase.setCreateId(loginUser.getPosition());
    }
  ```

    cudInfoDto.setCreateInfo(dto); 메소드 호출
    IBase 형으로 매개변수 받을 수 있는 이유는 MemberDto 가 IBase 를 implements 받았기 때문 ( 자식타입은 부모타입의 기능을 사용할 수 있다 )
    실직적 받은 값은 객체 dto 이다. 그러므로 iBase.setCreateDt(this.getSystemDt()); 는 현객체의 필드값을 설정 해주는 메소드이다.


+ HttpSession 은 자바 서블릿 API 에서 제공하는 인터페이스로, 클라이언트와 서버 간의 세션을 관리하는 데 사용
+ HttpServletRequest 는 자바 서블릿 API 에서 제공되는 인터페이스로, HTTP 요청과(POST, GET, session 등..) 관련된 정보를 서블릿에 전달하는 데 사용




## 서버에서 클라이언트 값 전송
+ 서버에 객체 필드 생성
+ sql 쿼리 select 작성
+ 컨트룰러 작성
+ html 에 {{}} 로 값을 받는다








# 필요 애노테이션

* @Slf4j
  + Lombok 라이브러리가 제공하는 애노테이션
  + log 를 쉽게 사용


* @Controller
  + 해당 클래스가 컨트롤러임을 나타냄
  + 컨트롤러는 주로 웹 요청을 처리
  + 비즈니스 로직을 수행
  + 결과를 뷰(View)에 전달하는 역할


* @RequestMapping
  + 만약 @RequestMapping("/ctweb") 는 /ctweb 경로로 들어오는 요청을 처리

* @Autowired
  + 필요한 객체 스프링부트가 생성

* @Mapper
  + MyBatis 프레임워크에서 사용하는 애노테이션
  + Java 인터페이스(DB연결)를 통해 SQL 쿼리를 실행하고 결과를 매핑하는 역할
  + SQL 쿼리는 애노테이션이나 XML 파일을 통해 정의

  + 주요 기능
    + SQL 쿼리 실행: 인터페이스 메서드에 매핑된 SQL 쿼리를 실행합니다.
    + 결과 매핑: SQL 쿼리의 결과를 Java 객체로 변환하여 반환합니다.
    + 매개변수 바인딩: 메서드의 매개변수를 SQL 쿼리에 바인딩합니다.

* @NoArgsConstructor
  + 인자가 없는 기본 생성자를 자동으로 생성합니다.

* @AllArgsConstructor
  + 클래스의 모든 필드를 인자로 받는 생성자를 자동으로 생성합니다.

  ```
   public class MyClass {
    private int id;
    private String name;
  }

  // new MyClass(1, "example") 와 같이 사용할 수 있음

* @SuperBuilder
  + 상속 관계가 있는 클래스에서 빌더 패턴을 사용할 수 있게 합니다.
  + 상속이 되는 순간 @SuperBuilder 사용 해야됨
    
```
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String name;
    private String position;
    private String department;
    private String email;
    private String phoneNumber;
}

public class Main {
    public static void main(String[] args) {
        // 5개의 필드 중 4개만 설정
        Employee employee = Employee.builder()
            .name("John")
            .position("Manager")
            .department("HR")
            .email("john@example.com")
            .build(); // phoneNumber는 설정하지 않음
    }
}
```
```
@AllArgsConstructor는 모든 필드를 받는 생성자를 생성하지만,
특정 필드만 선택적으로 설정하고 싶다면
@Builder 패턴을 활용하는 것이 가장 유연하고 편리한 방법입니다.
```

* @Null
    + 없어도 되는 필드값
     
+ @Transectional
    + service 에 사용
    + 메소드가 끝날 때까지 실행 보류
    + 메소드가 끝나야 결과 반영
    + 애노테이션 사용안 할시 문장 끝날 떄마다 결과 반영





# 메소드

* build():
  + Lombok의 @Builder나 @SuperBuilder 애노테이션을 사용하면 자동으로 빌더 클래스를 생성
  + 빌더 객체에 설정된 값들을 사용하여 최종 객체를 생성합니다.

* model.addAttribute("변수명", 객체)
  + model 객체를 사용하는 이유는 html 화면을 만들기 위해 필요한 데이터를 전달하기 위함이다.
  + 객체를 model 에 추가하여, html 에서 사용할 수 있도록 한다.

+ stream().map()
  ```
  private List<ICategory> getICategoryList(List<CategoryDto> list) {
        List<ICategory> result = list.stream()
                .map(a -> (ICategory)a)
                .toList();
        return result;
    }
  ```
  + map(a ) a에 List<CategoryDto> list 하나씩 요소가 ICategory 로 형변환 후 .toList(); 새로운 리스트 생성 -> result
  
  


# 클래스 구성
* ServiceImpl
주로 서비스 계층의 인터페이스를 구현한 클래스
소프트웨어 개발에서 서비스 계층은 비즈니스 로직을 포함하는 계층  
(비즈니스 로직(business logic): 애플리케이션이 수행해야 할 주요 기능과 작업을 정의)

애플리케이션의 주요 기능을 구현

Impl은 "Implementation"의 약어로, 인터페이스를 실제로 구현한 클래스라는 것을 나타낸다

데이터 접근 계층(Repository, DAO)과 프레젠테이션 계층(Controller) 사이에서 중재 역할

인터페이스와 구현
서비스 계층은 인터페이스와 그 구현 클래스로 구성되는 경우가 많습니다. 이는 코드의 유연성과 테스트 용이성을 높이기 위해서입니다.

장점
유연성: 인터페이스를 사용함으로써 구현체를 쉽게 교체할 수 있습니다.
테스트 용이성: 모킹(mocking)을 사용하여 서비스 계층을 독립적으로 테스트할 수 있습니다.
명확한 구조: 코드의 역할이 명확히 분리되어 가독성이 높아집니다.

+ resources
    + static
        + 정적 파일 : url 로 실행
    + templates
        + 동적 파일 : Controller 로 실행
        + Controller 안 return 으로 실행

+ Controller 동작 과정
    + get OR post
    + url
    + 매개변수

# 환경설정
+ resource -> application.properties
    + db 연결 설정
      ```
      spring.datasource.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
      DriverSpy는 log4jdbc 라이브러리의 드라이버 클래스
      log4jdbc는 JDBC 쿼리를 로깅해 주는 라이브러리로, SQL 실행 쿼리와 SQL 실행 시간을 로그에 기록할 수 있습니다. 디버깅이나 성능 모니터링에 유용
      
      spring.datasource.url=jdbc:log4jdbc:mysql://192.168.0.2:3306/phonebook_db?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC&allowPublicKeyRetrieval=true
      데이터베이스 연결 URL
      
      spring.datasource.username=phonebook_user
      phonebook_user는 데이터베이스에 접근
      
      spring.datasource.password=sangbong3!
      데이터베이스에 연결할 때 사용할 비밀번호
      ``` 
    + MyBatis 설정
      ```
      1. mybatis.mapper-locations=classpath:mapper/**/*.xml
      MyBatis 매퍼 XML 파일의 위치를 지정합니다.
      
      2. mybatis.configuration.map-underscore-to-camel-case=true
      데이터베이스 컬럼명이 언더스코어 '_' 로 작성된 경우, 이를 자동으로 자바 객체의 카멜 케이스(camelCase) 프로퍼티로 매핑합니다.
      ex) 'user_name' -> 'userName'
      
      3. mybatis.type-aliases-package=com.sb3.sun.sunprj
      MyBatis에서 사용할 타입 별칭(type alias)이 정의된 패키지를 지정합니다.
      com.sb3.sun.sunprj 패키지 아래의 모든 클래스가 별칭으로 사용할 수 있게 됩니다.
      이 설정을 통해 XML에서 전체 클래스 이름을 입력하지 않고 간단한 별칭을 사용할 수 있습니다.
      ```
    + Mustache 설정
      ```
      1. spring.mustache.check-template-location=true
      Mustache 템플릿 파일이 설정된 위치에 존재하는지 확인합니다.
      
      2. spring.mustache.enabled=true
      Mustache 템플릿 엔진을 활성화합니다.
      
      3.spring.mustache.charset=UTF-8
      Mustache 템플릿 파일을 읽을 때 사용할 문자 인코딩을 설정
      
      4. spring.mustache.suffix=.html
      Mustache 템플릿 파일의 확장자를 설정
      예를 들어, example.html 템플릿 파일을 example로 호출
      ```
  

# 그 외
+ 템플릿 엔진
  * 템플린 엔진이란?
    - HTML, XML, 텍스트 파일 등을 동적으로 생성할 수 있게 해주는 도구
      
  * 템플릿 엔진의 주요 기능
    -  변수를 사용하여 동적인 데이터를 삽입할 수 있습니다.
    -  리스트나 배열 등의 데이터 구조를 반복하여 HTML 요소를 생성

  * 대표적인 템플릿 엔진
    - Thymeleaf (자바 기반)
      + 서버 사이드 렌더링 (Spring과 통합)
      + 내추럴 템플릿 (브라우저에서 직접 확인 가능)
      + 다양한 표현식과 로직 지원
      + 오류 발생 시 예외 발생, 전체 페이지 깨짐
      + 동적 웹 페이지 생성, Spring MVC와 통합
        
    - Mustache (언어 중립적)
      + 클라이언트 및 서버 사이드 렌더링
      + 순수 HTML (단순하고 가벼움)
      + 로직 없음 (단순 변수 치환 및 반복)
      + 오류 발생 시 템플릿 그대로 화면 출력
      + 단순 템플릿 렌더링, 다양한 언어와 환경에서 사용

+ jQuery 란
  
  - 복잡한 JavaScript 코드를 간단하고 직관적으로 작성할 수 있게 합

    ```
    // JavaScript로 요소 선택 및 이벤트 처리
    document.getElementById("myButton").addEventListener("click", function() {
        alert("Button clicked!");
    });
    
    // jQuery로 요소 선택 및 이벤트 처리
    $("#myButton").click(function() {
        alert("Button clicked!");
    });

  - $.searchCategoryList = function() {}
    * 독립적인 함수 정의를 통해 특정 기능을 캡슐화하고 재사용 가능
    * funtion(){...} 밖에 선언 ( <script> 안 )
  
  - $("#prevBtn").click(function() {}
      - 버튼 클릭 이벤트
      - jQuery를 사용하여 특정 요소에 클릭 이벤트를 등록하는 코드
      - id가 prevBtn 인 요소를 선택
      - funtion(){...} 안에 선언
            
        
+ ajax 란
  - Asynchronous JavaScript and XML
  - JavaScript와 XML을 이용한 비동기적 정보 교환 기법
  - 요즘은 XML보다는 JSON을 주로 사용
  - 브라우저의 XMLHttpRequest를 이용해 전체 페이지를 새로 가져오지 않고도 페이지 일부만을 변경할 수 있도록 javascript를 실행해 서버에 데이터만     을 별도로 요청하는 기법
  - javascript를 통해 HTML,CSS를 이용해 골격을 먼저 형성하고 ajax 실행 부가 담긴 javascript 영역을 실행하여 데이터를 별도로 가져와 적절한 방법으로 데이터를 끼워 넣은 후 페이지를 로딩한다.
    
  - 자주 쓰이는 설정값
    * url :	ajax 요청할 매핑된 url 입력
    * type :	HTTP 통신의 종류를 설정
    * dataType :	ajax를 통해 리턴받을 데이터의 타입을 설정
    * contentType : application/x-www-form-urlencoded; charset=UTF-8
    * data :	URL 파라미터를 통해 보낼 데이터. 종류 : Object or String or Array
        - Object는 key:value set 객체여야 하며 value 영역이 array일 경우 jQuery가 serialize를 해줌. value 영역이 String이 아닌 경우 String으로 변환한 뒤 전송됨

```
$.ajax({
                    url: "/api/v1/organization/findAll",
                    type: "POST",
                    datatype: "json",
                    data: JSON.stringify({                              // 이런 형태로 서버에 데이터 전송  서버는 @RequestBody 로 값을 받음
                        "organization": organization,                   // 보라색은 전역변수, 흰색은 지역변수 ~
                    }),
                    contentType: "application/json; charset=UTF-8"
                }).done(function (data, status, xhr) {
                    if (status === "success") {
                        $.showItemList(data.responseData.dataList);
                    }
                }).fail(function (jqXHR, status, errorThrown) {
                    alert("검색을 실패했습니다. " + jqXHR.responseJSON.message);
                });
            };
```

        


+ Spring framework 또는 전자정부 프레임워크
    - WEB 화면, Mobile (HTML, CSS, JavaScript, Vue.js, React.js, Angular.js, TypeScript, BootStrap, Flutter, ...)
    - Controller : HTTP POST, GET, PATCH, DELETE 이러한 사용자가 행동하는 기능을 구현합니다.
    - Service : Service Implement 의 interface 를 구현합니다. (개발회사 마다 약간씩 다름)
    - Service Implement : 데이터 객체를 DAO 에서 저장하거나 읽을수 있도록 하는 기능을 구현합니다.
    - Data Access Object (DAO) : Database 에 접속하여서 데이터를 저장하거나 읽습니다. MyBatis 안에 SQL 언어
    - Model (데이터 객체, 자바빈)
    - ORACLE, MSSQL, DB2, MySQL, ... : DBMS 


+ Spring Boot 프레임워크 에서는 (Python Django 프레임워크)
    - WEB 화면, Mobile (HTML, CSS, JavaScript, Vue.js, React.js, Angular.js, TypeScript, BootStrap, Flutter, ...)
    - Controller : HTTP POST, GET, PATCH, DELETE 이러한 사용자가 행동하는 기능을 구현합니다.
    - Service : Service Implement 의 interface 를 구현합니다. (개발회사 마다 약간씩 다름)
    - Service Implement : 데이터 객체를 Repository 에서 저장하거나 읽을수 있도록 하는 기능을 구현합니다.
    - Repository : 실제 저장공간(Database, 파일, 액셀, NOSQL 등) 에 접속하여서 데이터를 저장하거나 읽습니다.
    - Model (데이터 객체, 자바빈)
    - ORACLE, MSSQL, DB2, MySQL, NoSQL, JSON, Excel, File, ...
    
+ Local 현 컴퓨터
    + commit -> local 저장소에 저장
    + 로컬 저장소는 사용자의 컴퓨터에 있는 .git 디렉터리 내에 있음
      
+ Remote 원격 ( 깃 서버 )
    + push 원격 저장
      
+ Recent (최근)

+ Git branches
    + git Sync : push, pull....
    + show log -> Create Branch ( 새로운 브랜치에 해당 히스토리로 롤백 )

+ GitHub Desktop
    + 로컬에서의 변경 사항을 "시각적" 으로 관리하고, 원격 저장소로 푸시하는 작업을 돕는 도구
    
+ Merge
    + 목적지 브랜치로 바꾼 뒤 바꿀 브랜치 머지

+ Debug
    + f7 : 메소드 안으로 들어가기
    + f8 : 다음 라인으로 이동
    + Shift + F8 : 현재 메소드에서 빠져나와 호출한 코드로 돌아가는 기능

+ db
    cascade : 외래키 참조키 자동 변환

# 단축키

+ Replace
    - Ctrl + r
+ Replace in Files
    - Ctrl + Shift + r
+ Find in Files
    - Ctrl + Shift + f
+ 코드 포맷팅
    + ctrl + alt + L
+ 오류 발생 시:
    + IntelliJ 에서 Gradle 탭을 열고, Tasks > build > classes 를 선택하여 실행
    + Rebuild 는 프로젝트의 모든 파일을 다시 컴파일, classes는 변경된 파일만 빌드
