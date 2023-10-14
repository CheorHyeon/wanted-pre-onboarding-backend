# wanted-pre-onboarding-backend
원티드 프리온보딩 백엔드 인턴십 사전과제 - 박철현

### 과제 정보
- 구현 언어 및 프레임워크 : Java 17 & Spring 3.1
- DB : Maria DB
- 로그인 및 인가가 필요한 공고 수정, 공고 삭제, 공고 생성, 공고 지원 등의 기능을 로그인/인증 기능 없이 구현하였습니다.

### 동작 시켜보기
1. pull을 받아줍니다.
2. application.yml의 DB 정보를 맞게 수정해 주시고, DB 이름을 `wanted`로 설정해 주세요
3. 인텔리제이에서 구동해 주시고, Swagger UI에 접속하셔서 API를 체험하실 수 있습니다.
  - 링크 : [Swagger](http://localhost:8080/swagger-ui/index.html?continue#/)
![swagger](https://github.com/CheorHyeon/wanted-pre-onboarding-backend/assets/126079049/2aee3730-3cdc-4ca7-aadf-7a4f18f48e13)

### RsData 란?
- 응답 형식의 객체로 사용되는 RsData class 입니다.
- 단순히 Boolean으로 조건이 `참 / 거짓`을 나타내기보다는, 조건의 일치 여부와 함께 메세지도 같이 넘겨주기 위해 해당 class를 사용합니다.
- 성공도 그렇고 실패한 경우가 여러 가지 있을 수 있기 때문에 메세지와 성공 코드로 구분합니다.
  - 예를 들어 로그인에 실패했다면 ID가 안 맞는건지? PW가 안 맞는건지? 계정 정지 상태인 건지? 등의 상태 정보를 알 수 있습니다.

### 구현 기능
- [x] 채용 공고 등록
    - 사용자로부터 회사명을 입력받아 회사 정보를 추출합니다(사용자가 회사 id를 알고 있을 것 같진 않을 것 같아 이름으로 받았습니다.)
    - 입력받은 정보로 공고를 등록합니다.
    - 상세 파일별 구현 내용은 [PR](https://github.com/CheorHyeon/wanted-pre-onboarding-backend/pull/1)에서 확인할 수 있습니다.
- [x] 채용 공고 수정
    - URL을 통해 채용 공고의 Id를 받아 조회하고 수정할 속성을 수정합니다.
    - 회사명이나 회사 Id는 DTO 객체에 JSON 매칭을 하지 않기에 무시되어 수정되지 않습니다.
    - PETCH 메서드를 구현하였습니다.
    - 상세 파일별 구현 내용은 [PR](https://github.com/CheorHyeon/wanted-pre-onboarding-backend/pull/2)에서 확인할 수 있습니다.
- [x] 채용 공고 삭제
    - DELETE 메서드 요청 URL을 통해 삭제할 채용공고의 ID를 받아와서 삭제합니다.
    - 상세 파일별 구현 내용은 [PR](https://github.com/CheorHyeon/wanted-pre-onboarding-backend/pull/3)에서 확인할 수 있습니다.
- [x] 채용 공고 목록 조회 & 채용 상세 페이지
    - 채용 공고 전체 조회하거나 공고별 상세 페이지에 접속하여 상세 공고 내용을 확인할 수 있습니다.
    - 상세 파일별 구현 내용은 [PR](https://github.com/CheorHyeon/wanted-pre-onboarding-backend/pull/4)에서 확인할 수 있습니다.

### 가산점 기능(추가 기능)
- [x] 채용 공고 지원(사용자 중복 지원 불가)
    - 채용 공고 id와 사용자 id를 이용해 유효성 검사를 진행한 뒤 지원내역에 저장합니다
      - 채용 공고 id & 사용자 id를 동시에 갖는 DB 데이터가 있을 경우 데이터를 생성하지 않습니다.
    - 상세 파일별 구현 내용은 [PR](https://github.com/CheorHyeon/wanted-pre-onboarding-backend/pull/5)에서 확인할 수 있습니다.
- [x] 채용 공고 상세 페이지 중 해당 회사 다른 채용공고 id 출력
    - 가져온 공고에서 회사 정보를 가져온 뒤에 회사 정보로 등록된 다른 채용 공고가 있는지 검사합니다. (해당 회사채용 공고가 2건 이상인 경우)
    - 2건 이상이라면 찾은 공고를 제외하고 나머지 공고의 id를 리스트화 하여 DTO객체에 넣어 반환합니다.
    - 공고 엔티티에 다른 채용공고를 저장할 리스트를 둘까 했지만, 채용 공고 엔티티 자체는 그냥 회사채용 공고지, 다른 공고를 갖는건 괜히 테이블만 커질 것 같아 DTO객체에만 넣었습니다.
    - 상세 파일별 구현 내용은 [PR](https://github.com/CheorHyeon/wanted-pre-onboarding-backend/pull/6)에서 확인할 수 있습니다.
- [x] 채용 공고 검색 기능
    - 채용 공고 조회할 때 검색어를 파라미터로 넘기면 검색이 가능합니다.
    - JPQL을 이용하여 기능을 구현하였습니다.
    - 상세 파일별 구현 내용은 [PR](https://github.com/CheorHyeon/wanted-pre-onboarding-backend/pull/7)에서 확인할 수 있습니다.
     
### 메세지 컨벤션
- feat : 새로운 기능 추가
- refactor : 코드 리팩토링
- test : 테스트 코드, 리팩토링 테스트 코드 추가(프로덕션 코드를 변경하지 않는 경우)
- 출처 : [[Git] 좋은 커밋 메시지 작성법](https://cocoon1787.tistory.com/708)
    
