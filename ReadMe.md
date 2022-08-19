# Project MAIC-Server(프로젝트 물아일체)

## 기술 스택
- Java 17
- Spring Boot
- Spring Data JPA
- QueryDSL
- Docker

## 규칙
- 추후 서비스가 커짐에 따라 MSA 환경으로 구성 될 가능성을 생각하여 규칙 설정
  - 서로 다른 도메인의 Entity끼리 연관 관계 금지
  - 서로 다른 도메인끼리 필요한 데이터는 Service Layer에서만 주고 받을 수 있음
  - 데이터를 주고 받을 때 Response 클래스를 통해 가능
