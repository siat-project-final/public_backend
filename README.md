# SIATHUB - Back

본 프로젝트는 교육생과 멘토 간의 학습 교류를 돕고, 교육생들의 학습을 도와주기 위해 설계된 웹 기반 커뮤니티 플랫폼의 백엔드 레포입니다.

(* 현 레포는 기존 배포 자동화가 적용된 레포가 아닌 public 읽기 전용입니다.)

- 접속 도메인 : https://siathub.com (게스트 계정 guest / 1234)

---
# 개발 환경
- JAVA 17/spring boot 3.5.0
- gradle 8.14
- redis 7, postgreSQL 14.2(rds ver)
- util dependency : p6spy, slf4j, swagger(openapi), junit5
---

## 🧩 주요 기능

### 1. 학습일지

* 학습 내용 등록/수정/삭제
* 공개 여부 설정 및 공개 일지 목록 조회
* 댓글 기능 (등록/조회)

### 2. Todo & 캘린더 통합

* Todo를 localStorage 기반으로 저장 및 캘린더와 연동
* 일정 클릭 시 모달 오픈 및 수정 가능

### 3. AI 챌린지

* 매일 5문제 자동 생성 및 풀이
* 제출 결과 저장 및 히스토리/랭킹 확인
* 재참여 제한 및 점수 환산

### 4. 멘토링

* 멘토/멘티 역할 분리된 예약 시스템
* 수락/거절/완료 상태 관리
* 알림 기능 연동

### 5. 알림 시스템

* 멘토/멘티별 알림 조회 및 삭제 가능
* 예약, 수락, 완료, 댓글 등 주요 이벤트 기반 알림 발송

---


# 협업을 위한 규칙
- 테스트
1. 유닛테스트는 필수인 서비스(ExtendWith)와 컨트롤러(WebMvcTest)만 진행
> 매퍼의 경우 백엔드 팀 전체가 이해하기 어려울 수 있기에 생략
2. 프론트 팀과의 협의 이후 통합테스트를 작성

- 예외 처리
1. 모든 예외는 서비스 단에서 처리하며 커스텀 예외를 발생시키도록 함
2. 모든 예외는 GlobalExceptionHandler를 통해 핸들링
3. 예외 코드는 정확히 구분해 줄 것

- 로그
1. 기본적으로 로그의 경우 디버깅을 위해 dev profile에선 on
2. SQL 디버깅을 위해 p6spy를 사용하여 포매팅

- DB
1. 배포 DB는 PostgreSQL이나 개발을 위해 h2 mode 변경으로 작업 권장
> postgreSQL의 경우 h2와 완벽히 호환은 안되므로 주의
2. RDS 환경엔 PostgreSQL을 사용하며, refresh 토큰 저장을 위해 redis를 사용
3. redis의 경우 프리티어 제외인 elasticache 대신 docker로 ec2 내부에 띄워 사용

# 트러블슈팅 로그
백엔드
- 250614 @MockBean deprecated > @MockitoBean
- 250614 컨트롤러 단위테스트 ExtendWith > WebMvcTest(엄밀하게 단위테스트는 아닐 수 있지만 컨트롤러의 경우 단위테스트시 @WebMvcTest를 주로 사용)
- 250616 anthropic api 발송시 인증서 문제 해결 > trouble-shooting.txt #1 참고
- 250618 myBatis 자동타입변환시 dto에 존재하지 않는 칼럼 select시 생기는 변환 오류 해결 > trouble-shooting.txt #2 참고
- 250618 myBatis dto 매핑시 칼럼 순서와 멤버 순서 일치해야함
- 250626 챌린지 복습 리스트 조회시 통신이 30초 이상 걸리는 문제 발생 > 원인 파악 결과 슬로우 쿼리 발생 > 쿼리 수정으로 해결 > trouble-shooting.txt #2 참고

# 패치 로그
- 250629 최초 기본 CI/CD 파이프라인 구축 완료(git action 기반 S3와 EC2에 workflow trigger 배포)
- 250701 router 53에서 구매한 siathub.com 도메인에 A, NS 레코드 추가하여 배포된 cloudfront 연결
- 250703 letsencrypt를 통해 인증서 발급 후 서버 통신 https 변경
- 250704 프리티어 한도 내 서버 유지를 위해 elasticache 대신 EC2 내 redis 추가
- 250719 포트폴리오 확인용 임시 게스트 계정 추가

- 250924 public repo 클론

