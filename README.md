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

Create (생성)
HTTP 메서드: POST
예시: 새로운 사용자를 생성하는 경우
요청: POST /users
요청 본문(body): 새로운 사용자의 정보를 포함하는 JSON 또는 XML 데이터
응답: 새로운 사용자의 ID와 함께 성공 메시지 또는 적절한 상태 코드 (예: 201 Created)


Read (읽기):
HTTP 메서드: GET
예시: 모든 사용자 목록을 가져오는 경우
요청: GET /users
응답: 모든 사용자의 목록을 포함하는 JSON 또는 XML 데이터 또는 적절한 상태 코드와 함께 성공 메시지 (예: 200 OK)


Update (갱신):
HTTP 메서드: PUT 또는 PATCH
예시: 특정 사용자의 정보를 업데이트하는 경우
요청: PUT /users/{id} 또는 PATCH /users/{id}
요청 본문: 업데이트할 사용자의 정보를 포함하는 JSON 또는 XML 데이터
응답: 적절한 상태 코드와 함께 성공 메시지 또는 업데이트된 사용자의 정보


Delete (삭제):
HTTP 메서드: DELETE
예시: 특정 사용자를 삭제하는 경우
요청: DELETE /users/{id}
응답: 적절한 상태 코드와 함께 성공 메시지 또는 삭제된 사용자의 정보



# CONTROLLER 

controller -> ServiceImpl -> mapper -> xml -> 자바 인터페이스를 통해 db에서 SQL 쿼리를 실행 -> 결과 객체로 반환


# 필요 애노테이션

@Slf4j
Lombok 라이브러리가 제공하는 애노테이션
log 를 쉽게 사용


@Controller
해당 클래스가 컨트롤러임을 나타냄
컨트롤러는 주로 웹 요청을 처리
비즈니스 로직을 수행
결과를 뷰(View)에 전달하는 역할


@RequestMapping
만약 @RequestMapping("/ctweb") 는 /ctweb 경로로 들어오는 요청을 처리

@Autowired
필요한 객체를 직접 생성

@Mapper
MyBatis 프레임워크에서 사용하는 애노테이션
Java 인터페이스(DB연결)를 통해 SQL 쿼리를 실행하고 결과를 매핑하는 역할
SQL 쿼리는 애노테이션이나 XML 파일을 통해 정의

주요 기능
SQL 쿼리 실행: 인터페이스 메서드에 매핑된 SQL 쿼리를 실행합니다.
결과 매핑: SQL 쿼리의 결과를 Java 객체로 변환하여 반환합니다.
매개변수 바인딩: 메서드의 매개변수를 SQL 쿼리에 바인딩합니다.

@NoArgsConstructor
인자가 없는 기본 생성자를 자동으로 생성합니다.

@AllArgsConstructor
클래스의 모든 필드를 인자로 받는 생성자를 자동으로 생성합니다.

public class MyClass {
    private int id;
    private String name;
}

// new MyClass(1, "example") 와 같이 사용할 수 있음

@SuperBuilder
상속 관계가 있는 클래스에서 빌더 패턴을 사용할 수 있게 합니다.
SearchCategoryDto searchCategoryDto = SearchCategoryDto.builder()
                    .name(name).page(page).build();















