```mermaid
sequenceDiagram
    participant 사용자
    participant 컨트롤러
    participant 서비스
    participant JPA_리포지토리
    participant Supabase_DB

    사용자->>컨트롤러: 글 작성 요청 (POST /posts)
    컨트롤러->>서비스: 게시글 저장 요청
    서비스->>JPA_리포지토리: save(postEntity)
    JPA_리포지토리->>Supabase_DB: INSERT INTO posts ...
    Supabase_DB-->>JPA_리포지토리: 저장 완료 (id 반환)
    JPA_리포지토리-->>서비스: 저장된 게시글 Entity
    서비스-->>컨트롤러: 응답 데이터 DTO로 변환
    컨트롤러-->>사용자: 200 OK + JSON 응답

    사용자->>컨트롤러: 게시글 목록 조회 (GET /posts)
    컨트롤러->>서비스: 전체 게시글 요청
    서비스->>JPA_리포지토리: findAll()
    JPA_리포지토리->>Supabase_DB: SELECT * FROM posts
    Supabase_DB-->>JPA_리포지토리: 결과 반환
    JPA_리포지토리-->>서비스: List<PostEntity>
    서비스-->>컨트롤러: DTO 리스트로 변환
    컨트롤러-->>사용자: 200 OK + 게시글 리스트 JSON

    사용자->>컨트롤러: 게시글 삭제 요청 (DELETE /posts/{id})
    컨트롤러->>서비스: 삭제 요청
    서비스->>JPA_리포지토리: deleteById(id)
    JPA_리포지토리->>Supabase_DB: DELETE FROM posts WHERE id = ?
    Supabase_DB-->>JPA_리포지토리: 삭제 완료
    JPA_리포지토리-->>서비스: void
    서비스-->>컨트롤러: 완료
    컨트롤러-->>사용자: 204 No Content
```

### 🟩 1. Entity

📍위치: JPA_리포지토리 ↔ 서비스 ↔ Supabase_DB 사이에 쓰이는 내부 구조체

구분 | 설명
내부 위치 | JPA Repository에서 사용하는 데이터 단위 (예: PostEntity)
작동 시점 | save(postEntity) 또는 findAll()로 가져온 결과 타입이 Entity
예시 | PostEntity ← @Entity, @Id, @Column으로 구성됨
핵심 역할 | DB 테이블과 직접 매핑되는 객체 (ORM Object)

➡️ 즉, Entity는 DB와 통신하는 유일한 자바 객체로

Repository에서 쿼리를 실행할 때도

DB에서 SELECT한 결과를 받을 때도

Entity 객체로 전달되고 반환돼요.

### 🟦 2. DTO (Data Transfer Object)

📍위치: 서비스 ↔ 컨트롤러 사이에서 사용


구분 | 설명
내부 위치 | Controller로 보낼 때 혹은 받을 때 직접 사용되는 구조체
작동 시점 | Service 계층에서 Entity → DTO 변환 (PostEntity → PostResponseDto)
예시 | PostRequestDto, PostResponseDto
핵심 역할 | 사용자에게 보여줄 정보만 골라서 전달 / 받는 구조체 (보안 + 명확성)

➡️ 즉, DTO는 클라이언트와 주고받는 데이터의 형태로,

Controller에서는 Entity를 직접 다루지 않고 DTO만 사용

Service에서 Entity ↔ DTO로 변환

### 🟨 3. Supabase PostgreSQL

📍위치: Supabase_DB 라고 표기된 부분 그 자체


구분 | 설명
내부 위치 | 다이어그램 제일 오른쪽 Supabase_DB
작동 시점 | Repository가 save, findAll, deleteById 등을 실행할 때 실제 SQL이 실행되는 대상
예시 | Supabase 콘솔에서 posts 테이블 직접 확인 가능
핵심 역할 | JPA가 실행하는 모든 쿼리의 최종 목적지인 실제 물리 DB 서버

➡️ Supabase는 Spring 앱 외부에 있는 PostgreSQL 호스팅 서비스예요.
