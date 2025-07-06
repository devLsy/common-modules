
# 공통 모듈 (common-modules)

API 서비스에서 공통 기능을 분리하여 **재사용**할 수 있도록 구성된 모듈입니다.  
별도 `.jar` 파일로 패키징하여 **Nexus 저장소**에 업로드하고,  
다른 프로젝트에서 **의존성 주입**을 통해 가져다 씁니다.

---

## ✅ 사용 목적
- 공통 유틸리티, 예외 처리, AOP, 설정 등을 모듈화
- 다수의 API 서비스에서 중복 없이 재사용 가능
- 관리와 유지보수 효율성 향상

---

## 📦 배포 방식 (Nexus)

해당 모듈은 `maven-publish` 플러그인을 사용하여 Nexus 저장소에 업로드합니다.

### 1. `gradle.properties` 파일 설정 (로컬에 생성 필요)

루트 디렉토리에 아래와 같이 생성하고 nexus 계정정보를 적어줍니다.

`gradle.properties`는 `.gitignore`에 포함되어 있습니다.  
**보안상 반드시 포함시켜야 합니다.**

```properties
nexusUsername=your-nexus-username
nexusPassword=your-nexus-password
```

---

### 2. 퍼블리시 명령어 실행

```bash
./gradlew publish
```

또는  
**IntelliJ → Gradle 탭 → publishing → publish 실행**

업로드 후, 다른 프로젝트의 `build.gradle`에서 아래처럼 참조합니다:

```groovy
repositories {
    // 넥서스 저장소 추가
    maven {
        // 넥서스 공통모듈 저장소 경로
        url "http://localhost:8081/repository/common-modules/"
    }
}

dependencies {
    // 공통 모듈 의존성 추가
    implementation 'com.example:common-modules:${버전}-SNAPSHOT'
}
```

---

## ⚙️ 포함 항목 예시

- `FileUtils`, `QueryDslUtils`, `StringUtils`
- 공통 예외 처리 및 Validation 어노테이션
- Swagger / JPA / Security 기본 설정
- API 로깅 AOP
- Enum / DTO / Config 정리

---

## 📌 버전 관리 가이드

공통 모듈은 여러 프로젝트에 영향을 미치는 중요한 구성 요소이므로,  
**기능 단위로 명확히 나눠 단위 테스트와 함께 버전을 올리는 방식**을 권장합니다.

> 버전 업 시 팀 내 리뷰 및 사전 공유 절차가 필요합니다.  
> 예: `0.0.16 → 0.0.17`

---

> ❗ **주의:** 공통 모듈은 실행 가능한 애플리케이션이 아니므로 `bootJar`는 비활성화되어 있습니다.
