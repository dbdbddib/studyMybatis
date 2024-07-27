# studyMybatis

CRUD는 “Create, Read, Update, Delete”의 약어로 데이터베이스와 같은 시스템에서 기본적인 데이터 관리 기능을 의미
CRUD는 거의 모든 시스템에 적용되는 가장 기본

 이름     SQL문
CREATE    INSERT
READ      SELECT
UPDATE    UPDATE
DELETE    DELETE


REST API 에서의 CRUD

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
