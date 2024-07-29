# studyMybatis

# CRUD
CRUD는 “Create, Read, Update, Delete”의 약어로 데이터베이스와 같은 시스템에서 기본적인 데이터 관리 기능을 의미
CRUD는 거의 모든 시스템에 적용되는 가장 기본

 이름     SQL문
CREATE    INSERT
READ      SELECT
UPDATE    UPDATE
DELETE    DELETE


### REST API 에서의 CRUD

클라이언트 <-> 서버간 HTTP 프로토콜을 이용해 REST API를 설계하고 작성할 때에도 CRUD 개념이 사용

 이름     HTTP METHOD
CREATE    POST
READ      GET
UPDATE    PUT, PATCH
DELETE    DELETE

###Create (생성)
HTTP 메서드: POST
예시: 새로운 사용자를 생성하는 경우
요청: POST /users
요청 본문(body): 새로운 사용자의 정보를 포함하는 JSON 또는 XML 데이터
응답: 새로운 사용자의 ID와 함께 성공 메시지 또는 적절한 상태 코드 (예: 201 Created)


###Read (읽기):
HTTP 메서드: GET
예시: 모든 사용자 목록을 가져오는 경우
요청: GET /users
응답: 모든 사용자의 목록을 포함하는 JSON 또는 XML 데이터 또는 적절한 상태 코드와 함께 성공 메시지 (예: 200 OK)


###Update (갱신):
HTTP 메서드: PUT 또는 PATCH
예시: 특정 사용자의 정보를 업데이트하는 경우
요청: PUT /users/{id} 또는 PATCH /users/{id}
요청 본문: 업데이트할 사용자의 정보를 포함하는 JSON 또는 XML 데이터
응답: 적절한 상태 코드와 함께 성공 메시지 또는 업데이트된 사용자의 정보


###Delete (삭제):
HTTP 메서드: DELETE
예시: 특정 사용자를 삭제하는 경우
요청: DELETE /users/{id}
응답: 적절한 상태 코드와 함께 성공 메시지 또는 삭제된 사용자의 정보



# CONTROLLER 

controller -> ServiceImpl -> mapper -> xml -> 자바 인터페이스를 통해 db에서 SQL 쿼리를 실행 -> 결과 객체로 반환


# 필요 애노테이션

## @Slf4j
Lombok 라이브러리가 제공하는 애노테이션
log 를 쉽게 사용


## @Controller
해당 클래스가 컨트롤러임을 나타냄
컨트롤러는 주로 웹 요청을 처리
비즈니스 로직을 수행
결과를 뷰(View)에 전달하는 역할


## @RequestMapping
만약 @RequestMapping("/ctweb") 는 /ctweb 경로로 들어오는 요청을 처리

## @Autowired
필요한 객체를 직접 생성

## @Mapper
MyBatis 프레임워크에서 사용하는 애노테이션
Java 인터페이스(DB연결)를 통해 SQL 쿼리를 실행하고 결과를 매핑하는 역할
SQL 쿼리는 애노테이션이나 XML 파일을 통해 정의

주요 기능
SQL 쿼리 실행: 인터페이스 메서드에 매핑된 SQL 쿼리를 실행합니다.
결과 매핑: SQL 쿼리의 결과를 Java 객체로 변환하여 반환합니다.
매개변수 바인딩: 메서드의 매개변수를 SQL 쿼리에 바인딩합니다.

## @NoArgsConstructor
인자가 없는 기본 생성자를 자동으로 생성합니다.

## @AllArgsConstructor
클래스의 모든 필드를 인자로 받는 생성자를 자동으로 생성합니다.

public class MyClass {
    private int id;
    private String name;
}

// new MyClass(1, "example") 와 같이 사용할 수 있음

## @SuperBuilder
상속 관계가 있는 클래스에서 빌더 패턴을 사용할 수 있게 합니다.
SearchCategoryDto searchCategoryDto = SearchCategoryDto.builder()
                    .name(name).page(page).build();



# 메소드

## build():
Lombok의 @Builder나 @SuperBuilder 애노테이션을 사용하면 자동으로 빌더 클래스를 생성
빌더 객체에 설정된 값들을 사용하여 최종 객체를 생성합니다.


# 클래스 구성
## ServiceImpl
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




